package experimental.recognition

import kotlin.math.*

// =============================================================================
// Preprocessing: raw user paths → clean segments
//
// Pipeline: circle check → split → merge (strict only) → deduplicate
//
// Constants are colocated with the pipeline step that uses them.
// =============================================================================

// --- Circle detection constants ---
private const val CIRCLE_CLOSURE_THRESHOLD = 0.20         // start↔end distance as fraction of scale
private const val CIRCLE_ASPECT_MIN = 0.5
private const val CIRCLE_ASPECT_MAX = 2.0
private const val CIRCLE_RADIUS_VARIANCE_MAX = 0.40       // fraction of avgRadius
private const val CIRCLE_SAMPLE_COUNT = 60                // number of sampled points for shape check
private const val CIRCLE_CORNER_ANGLE = 35.0              // degrees — sharper = corner
private const val CIRCLE_STRAIGHT_RATIO_MAX = 0.15        // max fraction of samples that are straight
private const val CIRCLE_MAX_CORNERS = 3                  // ≥ this many corners → not a circle
private const val CIRCLE_MAX_GAPS = 3
private const val CIRCLE_GAP_MAX_SINGLE = 0.15            // single gap as fraction of scale
private const val CIRCLE_GAP_MAX_TOTAL_RATIO = 0.25       // total gap as fraction of circumference
private const val CIRCLE_GAP_MIN_THRESHOLD = 0.02         // gap counted if > 2% of scale
private const val CIRCLE_STRAIGHT_ANGLE = 10.0            // degrees — less than this = straight section

// --- Corner splitting constants (strict/guided mode) ---
private const val CORNER_ANGLE_THRESHOLD = 50.0           // instant split angle (degrees) — note: passed as param, default 45 in TS but called with 50
private const val CORNER_CUMULATIVE_THRESHOLD = 60.0      // cumulative angle over smoothing window
private const val CORNER_SMOOTHING_WINDOW = 3
private const val CORNER_MIN_SEGMENT_LENGTH = 3.0         // minimum displacement (pixels) to keep segment

// --- Direction-flip splitting constants (flexible/free draw mode) ---
private const val FLIP_WINDOW_SIZE = 4
private const val FLIP_MIN_MOVEMENT = 0.5                 // minimum dx/dy to determine direction
private const val FLIP_DOMINANCE_DEAD_ZONE = 0.85         // skip when H/V dominance < this
private const val FLIP_STABILITY_THRESHOLD = 8            // consecutive stable-direction points before split
private const val FLIP_MIN_SEGMENT_LENGTH = 2.0           // minimum displacement to keep segment

// --- Merge constants (strict mode only) ---
private const val MERGE_DISTANCE_RATIO = 0.10             // connection distance as fraction of scale
private const val MERGE_ANGLE_THRESHOLD = PI / 3.0        // 60 degrees

// --- Deduplication constants ---
private const val DEDUP_PARALLEL_THRESHOLD = 0.4          // radians (~23 degrees)
private const val DEDUP_COLLINEAR_THRESHOLD = 0.08        // perpendicular distance as fraction of scale
private const val DEDUP_OVERLAP_RATIO = 0.50              // overlap along length to consider duplicate


// =============================================================================
// Circle detection — runs FIRST, before any splitting
// =============================================================================

/**
 * Detect if the given segments form a closed circular shape.
 * Checks closure, aspect ratio, radius consistency, and shape (no corners, no straight sections).
 */
fun detectCircularPattern(segments: List<List<DrawingPoint>>, bbox: BoundingBox): Boolean {
    if (segments.isEmpty()) return false
    val allPoints = segments.flatten()
    if (allPoints.size < 10) return false

    val scale = maxOf(bbox.width, bbox.height).let { if (it == 0.0) 1.0 else it }

    // Check gaps between segments
    if (segments.size > 1) {
        var gapCount = 0
        var totalGapDistance = 0.0
        val maxGapThreshold = scale * CIRCLE_GAP_MAX_SINGLE

        for (i in 0 until segments.size - 1) {
            val currentEnd = segments[i].last()
            val nextStart = segments[i + 1].first()
            val gapDist = distance(currentEnd, nextStart)

            if (gapDist > scale * CIRCLE_GAP_MIN_THRESHOLD) {
                gapCount++
                totalGapDistance += gapDist
                if (gapDist > maxGapThreshold) return false
            }
        }

        if (gapCount > CIRCLE_MAX_GAPS) return false
        val circumference = PI * scale
        if (totalGapDistance > circumference * CIRCLE_GAP_MAX_TOTAL_RATIO) return false
    }

    // Check if path is roughly closed
    val firstPoint = segments.first().first()
    val lastPoint = segments.last().last()
    val closureThreshold = scale * CIRCLE_CLOSURE_THRESHOLD
    val closureDist = distance(firstPoint, lastPoint)
    if (closureDist > closureThreshold) return false

    // Check aspect ratio
    val aspectRatio = bbox.width / (bbox.height.let { if (it == 0.0) 1.0 else it })
    if (aspectRatio < CIRCLE_ASPECT_MIN || aspectRatio > CIRCLE_ASPECT_MAX) return false

    // Check consistent radius
    val centerX = bbox.minX + bbox.width / 2.0
    val centerY = bbox.minY + bbox.height / 2.0
    val avgRadius = (bbox.width + bbox.height) / 4.0

    var radiusVariance = 0.0
    for (p in allPoints) {
        val dist = sqrt((p.x - centerX).pow(2) + (p.y - centerY).pow(2))
        radiusVariance += abs(dist - avgRadius)
    }
    radiusVariance /= allPoints.size

    // Sampled shape check: detect corners and straight sections
    val step = maxOf(1, allPoints.size / CIRCLE_SAMPLE_COUNT)
    var cornerCount = 0
    var straightSectionLength = 0
    var maxStraightSection = 0

    var i = step * 2
    while (i < allPoints.size) {
        val p1 = allPoints[i - step * 2]
        val p2 = allPoints[i - step]
        val p3 = allPoints[i]

        val dx1 = p2.x - p1.x
        val dy1 = p2.y - p1.y
        val dx2 = p3.x - p2.x
        val dy2 = p3.y - p2.y

        val len1 = sqrt(dx1 * dx1 + dy1 * dy1)
        val len2 = sqrt(dx2 * dx2 + dy2 * dy2)

        if (len1 > 0.5 && len2 > 0.5) {
            val dot = (dx1 * dx2 + dy1 * dy2) / (len1 * len2)
            val angle = acos(dot.coerceIn(-1.0, 1.0)) * (180.0 / PI)

            if (angle < CIRCLE_STRAIGHT_ANGLE) {
                straightSectionLength++
                maxStraightSection = maxOf(maxStraightSection, straightSectionLength)
            } else {
                straightSectionLength = 0
                if (angle > CIRCLE_CORNER_ANGLE) {
                    cornerCount++
                }
            }
        }
        i += step
    }

    val sampledCount = allPoints.size / step
    val straightRatio = if (sampledCount > 0) maxStraightSection.toDouble() / sampledCount else 0.0

    // Reject polygons: ≥3 corners means a square/triangle, not a circle.
    // Straight ratio only matters when there ARE corners — a uniformly smooth path
    // (cornerCount=0, high straightRatio) is a perfect circle, not a square.
    // Rounded squares have both corners AND long straight sections.
    if (cornerCount >= CIRCLE_MAX_CORNERS) return false
    if (cornerCount > 0 && straightRatio > CIRCLE_STRAIGHT_RATIO_MAX) return false

    return radiusVariance < avgRadius * CIRCLE_RADIUS_VARIANCE_MAX
}


// =============================================================================
// Corner splitting (strict/guided mode)
// =============================================================================

/**
 * Split path at sharp corners using instant angle change and cumulative angle over a window.
 * Used in guided mode where strokes have clear corners.
 */
fun splitPathAtCorners(points: List<DrawingPoint>, angleThreshold: Double = 45.0): List<List<DrawingPoint>> {
    if (points.size < 3) return listOf(points)

    val segments = mutableListOf<List<DrawingPoint>>()
    val currentSegment = mutableListOf(points[0])

    var cumulativeAngle = 0.0

    for (i in 1 until points.size - 1) {
        currentSegment.add(points[i])

        // Smoothed incoming direction
        var inDirX = 0.0; var inDirY = 0.0
        val inStart = maxOf(0, i - CORNER_SMOOTHING_WINDOW)
        for (j in inStart until i) {
            inDirX += points[j + 1].x - points[j].x
            inDirY += points[j + 1].y - points[j].y
        }
        val inLen = sqrt(inDirX * inDirX + inDirY * inDirY)

        // Smoothed outgoing direction
        var outDirX = 0.0; var outDirY = 0.0
        val outEnd = minOf(points.size - 1, i + CORNER_SMOOTHING_WINDOW)
        for (j in i until outEnd) {
            outDirX += points[j + 1].x - points[j].x
            outDirY += points[j + 1].y - points[j].y
        }
        val outLen = sqrt(outDirX * outDirX + outDirY * outDirY)

        if (inLen > 0.001 && outLen > 0.001) {
            val dot = (inDirX * outDirX + inDirY * outDirY) / (inLen * outLen)
            val angle = acos(dot.coerceIn(-1.0, 1.0)) * (180.0 / PI)

            cumulativeAngle += angle

            if (angle > angleThreshold || cumulativeAngle > CORNER_CUMULATIVE_THRESHOLD) {
                segments.add(currentSegment.toList())
                currentSegment.clear()
                currentSegment.add(points[i])
                cumulativeAngle = 0.0
            }
        }
    }

    currentSegment.add(points.last())
    segments.add(currentSegment.toList())

    return segments.filter { seg ->
        if (seg.size < 2) return@filter false
        val dx = seg.last().x - seg.first().x
        val dy = seg.last().y - seg.first().y
        sqrt(dx * dx + dy * dy) > CORNER_MIN_SEGMENT_LENGTH
    }
}


// =============================================================================
// Direction-flip splitting (flexible/free draw mode)
// =============================================================================

/**
 * Split path at clear H↔V direction transitions.
 * Handles smooth curves where no single point has a sharp angle change.
 * Uses dominance dead zone and stability threshold to prevent false splits.
 */
fun splitPathAtDirectionFlips(points: List<DrawingPoint>, windowSize: Int = FLIP_WINDOW_SIZE): List<List<DrawingPoint>> {
    if (points.size < 6) return listOf(points)

    val segments = mutableListOf<List<DrawingPoint>>()
    val currentSegment = mutableListOf(points[0])
    var prevDir: Char? = null // 'H' or 'V'
    var dirStableCount = 0

    for (i in 1 until points.size) {
        currentSegment.add(points[i])

        // Smoothed direction over window
        val wStart = maxOf(0, i - windowSize)
        var dx = 0.0; var dy = 0.0
        for (j in wStart until minOf(i, points.size - 1)) {
            dx += points[j + 1].x - points[j].x
            dy += points[j + 1].y - points[j].y
        }

        val absDx = abs(dx)
        val absDy = abs(dy)
        if (absDx < FLIP_MIN_MOVEMENT && absDy < FLIP_MIN_MOVEMENT) continue

        // Skip near-diagonal directions — dominance dead zone
        val dominance = maxOf(absDx, absDy) / (absDx + absDy)
        if (dominance < FLIP_DOMINANCE_DEAD_ZONE) continue

        val dir = if (absDx >= absDy) 'H' else 'V'

        if (dir == prevDir) {
            dirStableCount++
        } else if (prevDir != null) {
            if (dirStableCount >= FLIP_STABILITY_THRESHOLD) {
                val newSegment = currentSegment.dropLast(1)
                if (newSegment.isNotEmpty()) segments.add(newSegment)
                currentSegment.clear()
                currentSegment.add(points[i])
            }
            dirStableCount = 0
        }
        prevDir = dir
    }

    if (currentSegment.size >= 2) segments.add(currentSegment.toList())

    return segments.filter { seg ->
        if (seg.size < 2) return@filter false
        val dx = seg.last().x - seg.first().x
        val dy = seg.last().y - seg.first().y
        sqrt(dx * dx + dy * dy) > FLIP_MIN_SEGMENT_LENGTH
    }
}


// =============================================================================
// Segment merging (strict mode only — skipped in flex mode)
// =============================================================================

/**
 * Merge adjacent segments if close and similar direction.
 */
fun mergeConnectedSegments(segments: List<List<DrawingPoint>>, bbox: BoundingBox): List<List<DrawingPoint>> {
    if (segments.size <= 1) return segments

    val scale = maxOf(bbox.width, bbox.height).let { if (it == 0.0) 1.0 else it }
    val connectionThreshold = scale * MERGE_DISTANCE_RATIO

    val merged = mutableListOf<MutableList<DrawingPoint>>()
    var current = segments[0].toMutableList()

    for (i in 1 until segments.size) {
        val next = segments[i]
        if (next.isEmpty()) continue

        val currentEnd = current.last()
        val nextStart = next.first()
        val dist = distance(currentEnd, nextStart)

        // Direction similarity
        val currentDir = atan2(
            currentEnd.y - current[maxOf(0, current.size - 3)].y,
            currentEnd.x - current[maxOf(0, current.size - 3)].x,
        )
        val nextDir = atan2(
            next[minOf(2, next.size - 1)].y - nextStart.y,
            next[minOf(2, next.size - 1)].x - nextStart.x,
        )
        val angleDiff = abs(currentDir - nextDir)
        val normalizedAngleDiff = minOf(angleDiff, PI * 2.0 - angleDiff)

        if (dist < connectionThreshold && normalizedAngleDiff < MERGE_ANGLE_THRESHOLD) {
            current.addAll(next)
        } else {
            merged.add(current)
            current = next.toMutableList()
        }
    }
    merged.add(current)

    return merged
}


// =============================================================================
// Segment deduplication
// =============================================================================

/**
 * Remove overlapping segments (when user redraws the same line).
 * Checks: parallel, collinear, >50% overlap along length → remove shorter.
 */
fun deduplicateOverlappingSegments(segments: List<List<DrawingPoint>>, bbox: BoundingBox): List<List<DrawingPoint>> {
    if (segments.size <= 1) return segments

    val scale = maxOf(bbox.width, bbox.height).let { if (it == 0.0) 1.0 else it }
    val overlapThreshold = scale * DEDUP_COLLINEAR_THRESHOLD

    data class SegmentProps(
        val start: DrawingPoint, val end: DrawingPoint,
        val length: Double, val angle: Double,
        val midX: Double, val midY: Double,
    )

    val props = segments.map { seg ->
        val start = seg.first()
        val end = seg.last()
        val dx = end.x - start.x
        val dy = end.y - start.y
        SegmentProps(
            start = start, end = end,
            length = sqrt(dx * dx + dy * dy),
            angle = atan2(dy, dx),
            midX = (start.x + end.x) / 2.0,
            midY = (start.y + end.y) / 2.0,
        )
    }

    val toRemove = mutableSetOf<Int>()

    for (i in props.indices) {
        if (i in toRemove) continue
        for (j in i + 1 until props.size) {
            if (j in toRemove) continue

            val a = props[i]
            val b = props[j]

            // Parallel check (including anti-parallel)
            var angleDiff = abs(a.angle - b.angle)
            if (angleDiff > PI) angleDiff = 2.0 * PI - angleDiff
            var antiAngleDiff = abs(a.angle - b.angle + PI)
            if (antiAngleDiff > PI) antiAngleDiff = 2.0 * PI - antiAngleDiff

            if (angleDiff >= DEDUP_PARALLEL_THRESHOLD && antiAngleDiff >= DEDUP_PARALLEL_THRESHOLD) continue

            // Collinear check
            val perpDistA = pointToLineDistance(a.midX, a.midY, b.start.x, b.start.y, b.end.x, b.end.y)
            val perpDistB = pointToLineDistance(b.midX, b.midY, a.start.x, a.start.y, a.end.x, a.end.y)
            if (perpDistA >= overlapThreshold || perpDistB >= overlapThreshold) continue

            // Overlap along length
            val lineDir = if (a.length > b.length) a else b
            val cosAngle = cos(lineDir.angle)
            val sinAngle = sin(lineDir.angle)

            val projA1 = a.start.x * cosAngle + a.start.y * sinAngle
            val projA2 = a.end.x * cosAngle + a.end.y * sinAngle
            val projB1 = b.start.x * cosAngle + b.start.y * sinAngle
            val projB2 = b.end.x * cosAngle + b.end.y * sinAngle

            val aMin = minOf(projA1, projA2)
            val aMax = maxOf(projA1, projA2)
            val bMin = minOf(projB1, projB2)
            val bMax = maxOf(projB1, projB2)

            val overlapLength = maxOf(0.0, minOf(aMax, bMax) - maxOf(aMin, bMin))
            val minLength = minOf(a.length, b.length)
            val overlapRatio = if (minLength > 0) overlapLength / minLength else 0.0

            if (overlapRatio > DEDUP_OVERLAP_RATIO) {
                if (a.length >= b.length) {
                    toRemove.add(j)
                } else {
                    toRemove.add(i)
                    break
                }
            }
        }
    }

    return segments.filterIndexed { idx, _ -> idx !in toRemove }
}
