package experimental.recognition.steps

import experimental.recognition.DrawingPoint
import experimental.recognition.math.*
import kotlin.math.*

// Pipeline: circle check → split → merge (strict only) → deduplicate

private const val CIRCLE_CLOSURE_THRESHOLD = 0.20
private const val CIRCLE_ASPECT_MIN = 0.5
private const val CIRCLE_ASPECT_MAX = 2.0
private const val CIRCLE_RADIUS_VARIANCE_MAX = 0.40
private const val CIRCLE_SAMPLE_COUNT = 60
private const val CIRCLE_CORNER_ANGLE = 50.0
private const val CIRCLE_STRAIGHT_RATIO_MAX = 0.20
private const val CIRCLE_MAX_CORNERS = 3
private const val CIRCLE_MAX_GAPS = 3
private const val CIRCLE_GAP_MAX_SINGLE = 0.15
private const val CIRCLE_GAP_MAX_TOTAL_RATIO = 0.25
private const val CIRCLE_GAP_MIN_THRESHOLD = 0.02
private const val CIRCLE_STRAIGHT_ANGLE = 10.0
private const val CIRCLE_ARC_SECTORS = 16
private const val CIRCLE_MIN_ARC_COVERAGE = 0.75

private const val CORNER_ANGLE_THRESHOLD = 50.0
private const val CORNER_CUMULATIVE_THRESHOLD = 60.0
private const val CORNER_SMOOTHING_WINDOW = 3
private const val CORNER_MIN_SEGMENT_LENGTH = 3.0

private const val FLIP_WINDOW_SIZE = 4
private const val FLIP_MIN_MOVEMENT = 0.5
private const val FLIP_DOMINANCE_DEAD_ZONE = 0.85
private const val FLIP_STABILITY_THRESHOLD = 8
private const val FLIP_MIN_SEGMENT_LENGTH = 2.0

private const val MERGE_DISTANCE_RATIO = 0.10
private const val MERGE_ANGLE_THRESHOLD = PI / 3.0

private const val DEDUP_PARALLEL_THRESHOLD = 0.4
private const val DEDUP_COLLINEAR_THRESHOLD = 0.08
private const val DEDUP_OVERLAP_RATIO = 0.50

/**
 * Detect if the given path segments form a closed circular shape.
 * Rejects polygons (too many corners) and shapes with long straight sections.
 */
fun detectCircularPattern(segments: List<List<DrawingPoint>>, bbox: BoundingBox): Boolean {
    if (segments.isEmpty()) return false
    val allPoints = segments.flatten()
    if (allPoints.size < 10) return false

    val scale = bbox.scale

    if (segments.size > 1) {
        var gapCount = 0
        var totalGapDistance = 0.0
        val maxGapThreshold = scale * CIRCLE_GAP_MAX_SINGLE

        for (i in 0 until segments.size - 1) {
            val currentEnd = segments[i].last().toVec2()
            val nextStart = segments[i + 1].first().toVec2()
            val gapDist = currentEnd.distanceTo(nextStart)

            if (gapDist > scale * CIRCLE_GAP_MIN_THRESHOLD) {
                gapCount++
                totalGapDistance += gapDist
                if (gapDist > maxGapThreshold) return false
            }
        }

        if (gapCount > CIRCLE_MAX_GAPS) return false
        if (totalGapDistance > PI * scale * CIRCLE_GAP_MAX_TOTAL_RATIO) return false
    }

    val firstPoint = segments.first().first().toVec2()
    val lastPoint = segments.last().last().toVec2()
    if (firstPoint.distanceTo(lastPoint) > scale * CIRCLE_CLOSURE_THRESHOLD) {
        // Not tightly closed – accept if the arc covers most of the circle (handles overshoot and open arcs)
        val center = bbox.center
        val covered = BooleanArray(CIRCLE_ARC_SECTORS)
        for (p in allPoints) {
            val v = p.toVec2()
            val angle = atan2(v.y - center.y, v.x - center.x)
            val sector = ((angle + PI) / (2 * PI) * CIRCLE_ARC_SECTORS).toInt().coerceIn(0, CIRCLE_ARC_SECTORS - 1)
            covered[sector] = true
        }
        if (covered.count { it }.toDouble() / CIRCLE_ARC_SECTORS < CIRCLE_MIN_ARC_COVERAGE) return false
    }

    val aspectRatio = bbox.width / (bbox.height.let { if (it == 0.0) 1.0 else it })
    if (aspectRatio < CIRCLE_ASPECT_MIN || aspectRatio > CIRCLE_ASPECT_MAX) return false

    val center = bbox.center
    val avgRadius = (bbox.width + bbox.height) / 4.0
    var radiusVariance = 0.0
    for (point in allPoints) {
        radiusVariance += abs(point.toVec2().distanceTo(center) - avgRadius)
    }
    radiusVariance /= allPoints.size

    // Sample evenly spaced triplets and measure the angle change between them.
    // Circles have smooth curvature; polygons have sharp corners and straight runs.
    val step = maxOf(2, allPoints.size / CIRCLE_SAMPLE_COUNT)
    var cornerCount = 0
    var straightSectionLength = 0
    var maxStraightSection = 0

    var i = step * 2
    while (i < allPoints.size) {
        val previous = allPoints[i - step * 2].toVec2()
        val current = allPoints[i - step].toVec2()
        val next = allPoints[i].toVec2()

        val angle = angleBetween(current - previous, next - current, minLength = 0.5)
        if (angle != null) {
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

    if (cornerCount >= CIRCLE_MAX_CORNERS) return false
    if (cornerCount > 0 && straightRatio > CIRCLE_STRAIGHT_RATIO_MAX) return false

    return radiusVariance < avgRadius * CIRCLE_RADIUS_VARIANCE_MAX
}

/**
 * Split path at sharp corners. Uses smoothed direction vectors over a window
 * to detect both sudden angle changes and gradual cumulative turns.
 */
fun splitPathAtCorners(points: List<DrawingPoint>, angleThreshold: Double = 45.0): List<List<DrawingPoint>> {
    if (points.size < 3) return listOf(points)

    val segments = mutableListOf<List<DrawingPoint>>()
    val currentSegment = mutableListOf(points[0])
    var cumulativeAngle = 0.0

    for (i in 1 until points.size - 1) {
        currentSegment.add(points[i])

        var incomingDirection = Vec2(0.0, 0.0)
        val inStart = maxOf(0, i - CORNER_SMOOTHING_WINDOW)
        for (j in inStart until i) {
            incomingDirection = incomingDirection + (points[j + 1].toVec2() - points[j].toVec2())
        }

        var outgoingDirection = Vec2(0.0, 0.0)
        val outEnd = minOf(points.size - 1, i + CORNER_SMOOTHING_WINDOW)
        for (j in i until outEnd) {
            outgoingDirection = outgoingDirection + (points[j + 1].toVec2() - points[j].toVec2())
        }

        val angle = angleBetween(incomingDirection, outgoingDirection)
        if (angle != null) {
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

    return segments.filter { segment ->
        if (segment.size < 2) return@filter false
        segment.first().toVec2().distanceTo(segment.last().toVec2()) > CORNER_MIN_SEGMENT_LENGTH
    }
}

/**
 * Split path where the dominant drawing direction flips between horizontal and vertical.
 *
 * Unlike corner splitting, this handles smooth curves where no single point has a sharp
 * angle change. It tracks the dominant direction (H or V) over a sliding window and
 * splits only after the new direction has been stable for several consecutive points,
 * avoiding false splits on diagonal movement.
 */
fun splitPathAtDirectionFlips(points: List<DrawingPoint>, windowSize: Int = FLIP_WINDOW_SIZE): List<List<DrawingPoint>> {
    if (points.size < 6) return listOf(points)

    val segments = mutableListOf<List<DrawingPoint>>()
    val currentSegment = mutableListOf(points[0])
    var previousDirection: Char? = null
    var directionStableCount = 0

    for (i in 1 until points.size) {
        currentSegment.add(points[i])

        val windowStart = maxOf(0, i - windowSize)
        var smoothedDirection = Vec2(0.0, 0.0)
        for (j in windowStart until minOf(i, points.size - 1)) {
            smoothedDirection = smoothedDirection + (points[j + 1].toVec2() - points[j].toVec2())
        }

        val absDx = abs(smoothedDirection.x)
        val absDy = abs(smoothedDirection.y)
        if (absDx < FLIP_MIN_MOVEMENT && absDy < FLIP_MIN_MOVEMENT) continue

        // Ignore near-diagonal movement where neither H nor V clearly dominates
        val dominance = maxOf(absDx, absDy) / (absDx + absDy)
        if (dominance < FLIP_DOMINANCE_DEAD_ZONE) continue

        val direction = if (absDx >= absDy) 'H' else 'V'

        if (direction == previousDirection) {
            directionStableCount++
        } else if (previousDirection != null) {
            if (directionStableCount >= FLIP_STABILITY_THRESHOLD) {
                val newSegment = currentSegment.dropLast(1)
                if (newSegment.isNotEmpty()) segments.add(newSegment)
                currentSegment.clear()
                currentSegment.add(points[i])
            }
            directionStableCount = 0
        }
        previousDirection = direction
    }

    if (currentSegment.size >= 2) segments.add(currentSegment.toList())

    val filtered = segments.filter { segment ->
        if (segment.size < 2) return@filter false
        segment.first().toVec2().distanceTo(segment.last().toVec2()) > FLIP_MIN_SEGMENT_LENGTH
    }.toMutableList()

    // Closed path: if the user started mid-segment, the first and last segments
    // are really one continuous bar split by the start point. Merge them by prepending
    // the trailing segment to the leading one.
    if (filtered.size >= 3) {
        val pathClosed = points.first().toVec2().distanceTo(points.last().toVec2()) <
            FLIP_MIN_SEGMENT_LENGTH * 2
        if (pathClosed) {
            val firstDir = dominantDirection(filtered.first())
            val lastDir = dominantDirection(filtered.last())
            if (firstDir != null && firstDir == lastDir) {
                val tail = filtered.removeAt(filtered.lastIndex)
                filtered[0] = tail + filtered[0]
            }
        }
    }

    return filtered
}

private fun dominantDirection(segment: List<DrawingPoint>): Char? {
    if (segment.size < 2) return null
    val dx = abs(segment.last().x - segment.first().x)
    val dy = abs(segment.last().y - segment.first().y)
    if (dx < FLIP_MIN_MOVEMENT && dy < FLIP_MIN_MOVEMENT) return null
    return if (dx >= dy) 'H' else 'V'
}

/**
 * Merge adjacent segments that are close together and heading in a similar direction.
 * Only used in strict (guided) mode.
 */
fun mergeConnectedSegments(segments: List<List<DrawingPoint>>, bbox: BoundingBox): List<List<DrawingPoint>> {
    if (segments.size <= 1) return segments

    val connectionThreshold = bbox.scale * MERGE_DISTANCE_RATIO

    val merged = mutableListOf<MutableList<DrawingPoint>>()
    var current = segments[0].toMutableList()

    for (i in 1 until segments.size) {
        val next = segments[i]
        if (next.isEmpty()) continue

        val currentEnd = current.last().toVec2()
        val nextStart = next.first().toVec2()
        val distance = currentEnd.distanceTo(nextStart)

        val currentTail = current[maxOf(0, current.size - 3)].toVec2()
        val nextHead = next[minOf(2, next.size - 1)].toVec2()
        val currentDirection = currentTail.angleTo(currentEnd).toRadians()
        val nextDirection = nextStart.angleTo(nextHead).toRadians()
        val similarDirection = areParallelOrAntiParallel(currentDirection, nextDirection, MERGE_ANGLE_THRESHOLD)

        if (distance < connectionThreshold && similarDirection) {
            current.addAll(next)
        } else {
            merged.add(current)
            current = next.toMutableList()
        }
    }
    merged.add(current)

    return merged
}

/**
 * Remove overlapping segments caused by the user redrawing the same line.
 * Two segments are considered duplicates when they are parallel, collinear (midpoints
 * close to each other's line), and overlap by more than 50% of the shorter segment's length.
 * The shorter duplicate is removed.
 */
fun deduplicateOverlappingSegments(segments: List<List<DrawingPoint>>, bbox: BoundingBox): List<List<DrawingPoint>> {
    if (segments.size <= 1) return segments

    val collinearThreshold = bbox.scale * DEDUP_COLLINEAR_THRESHOLD

    data class SegmentProps(
        val start: Vec2, val end: Vec2,
        val length: Double, val angle: Double,
        val mid: Vec2,
    )

    val props = segments.map { segment ->
        val start = segment.first().toVec2()
        val end = segment.last().toVec2()
        SegmentProps(
            start = start, end = end,
            length = start.distanceTo(end),
            angle = start.angleTo(end).toRadians(),
            mid = start.lerp(end, 0.5),
        )
    }

    val toRemove = mutableSetOf<Int>()

    for (i in props.indices) {
        if (i in toRemove) continue
        for (j in i + 1 until props.size) {
            if (j in toRemove) continue

            val segmentA = props[i]
            val segmentB = props[j]

            if (!areParallelOrAntiParallel(segmentA.angle, segmentB.angle, DEDUP_PARALLEL_THRESHOLD)) continue

            val perpDistA = pointToLineDistance(segmentA.mid, segmentB.start, segmentB.end)
            val perpDistB = pointToLineDistance(segmentB.mid, segmentA.start, segmentA.end)
            if (perpDistA >= collinearThreshold || perpDistB >= collinearThreshold) continue

            val projectionAngle = if (segmentA.length > segmentB.length) segmentA.angle else segmentB.angle
            val projA1 = segmentA.start.projectOnto(projectionAngle)
            val projA2 = segmentA.end.projectOnto(projectionAngle)
            val projB1 = segmentB.start.projectOnto(projectionAngle)
            val projB2 = segmentB.end.projectOnto(projectionAngle)

            val rangeAMax = maxOf(projA1, projA2)
            val rangeAMin = minOf(projA1, projA2)
            val rangeBMax = maxOf(projB1, projB2)
            val rangeBMin = minOf(projB1, projB2)
            val overlapLength = maxOf(0.0, minOf(rangeAMax, rangeBMax) - maxOf(rangeAMin, rangeBMin))
            val overlapRatio = if (minOf(segmentA.length, segmentB.length) > 0) overlapLength / minOf(segmentA.length, segmentB.length) else 0.0

            if (overlapRatio > DEDUP_OVERLAP_RATIO) {
                if (segmentA.length >= segmentB.length) {
                    toRemove.add(j)
                } else {
                    toRemove.add(i)
                    break
                }
            }
        }
    }

    return segments.filterIndexed { index, _ -> index !in toRemove }
}
