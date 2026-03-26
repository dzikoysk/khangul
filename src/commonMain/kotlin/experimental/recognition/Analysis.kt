package experimental.recognition

import kotlin.math.*

// =============================================================================
// Analysis: clean segments → structural signature
//
// Pipeline: analyzeStroke (per segment) → analyzeRelation (pairwise) → signature
// =============================================================================

// --- Stroke analysis constants ---
private const val MIN_SEGMENT_LENGTH = 0.10          // normalized length — filter spurious tail segments
private const val CURVE_DEVIATION_THRESHOLD = 0.35   // max deviation from line to classify as curved
private const val SHORT_STROKE_LENGTH = 0.05         // below this, default to HORIZONTAL

// --- Relation analysis constants ---
private const val CONNECTION_THRESHOLD = 0.15        // normalized distance for "connected"
private const val H_POSITION_MIN_THRESHOLD = 0.05    // minimum threshold for h-position detection
private const val H_POSITION_SCALE = 0.30            // scale factor for width-based threshold
private const val V_POSITION_MIN_THRESHOLD = 0.05    // minimum threshold for v-position detection
private const val V_POSITION_SCALE = 0.30            // scale factor for height-based threshold


// =============================================================================
// Single stroke analysis
// =============================================================================

/**
 * Analyze a segment of points into an [AnalyzedStroke].
 * Normalizes to 0-1 space, classifies direction, measures curvature.
 * Returns null if fewer than 2 points.
 */
fun analyzeStroke(points: List<DrawingPoint>, bbox: BoundingBox): AnalyzedStroke? {
    if (points.size < 2) return null

    val scale = maxOf(bbox.width, bbox.height).let { if (it == 0.0) 1.0 else it }

    fun normalize(p: DrawingPoint) = DrawingPoint(
        x = (p.x - bbox.minX) / scale,
        y = (p.y - bbox.minY) / scale,
    )

    val normalizedPoints = points.map(::normalize)
    val start = normalizedPoints.first()
    val end = normalizedPoints.last()

    // Center = average of all points
    var sumX = 0.0; var sumY = 0.0
    var minX = Double.MAX_VALUE; var maxX = -Double.MAX_VALUE
    var minY = Double.MAX_VALUE; var maxY = -Double.MAX_VALUE
    for (p in normalizedPoints) {
        sumX += p.x; sumY += p.y
        if (p.x < minX) minX = p.x
        if (p.x > maxX) maxX = p.x
        if (p.y < minY) minY = p.y
        if (p.y > maxY) maxY = p.y
    }
    val center = DrawingPoint(sumX / normalizedPoints.size, sumY / normalizedPoints.size)

    // Angle from start to end
    val dx = end.x - start.x
    val dy = end.y - start.y
    val angle = atan2(dy, dx) * (180.0 / PI)

    // Length
    val length = sqrt(dx * dx + dy * dy)

    // Curvature: max deviation from straight line
    var maxDeviation = 0.0
    val lenSq = length * length
    for (np in normalizedPoints) {
        val t = if (lenSq > 0.0) (((np.x - start.x) * dx + (np.y - start.y) * dy) / lenSq).coerceIn(0.0, 1.0) else 0.0
        val projX = start.x + t * dx
        val projY = start.y + t * dy
        val deviation = sqrt((np.x - projX).pow(2) + (np.y - projY).pow(2))
        maxDeviation = maxOf(maxDeviation, deviation)
    }
    val isCurved = maxDeviation > CURVE_DEVIATION_THRESHOLD

    val direction = classifyDirection(angle, length)

    return AnalyzedStroke(
        direction = direction,
        startPoint = start,
        endPoint = end,
        centerPoint = center,
        minX = minX, maxX = maxX,
        minY = minY, maxY = maxY,
        angle = angle,
        length = length,
        isCurved = isCurved,
    )
}


// =============================================================================
// Relation analysis (pairwise)
// =============================================================================

/**
 * Analyze the spatial relationship between two strokes.
 * Detects connections (with 5 sample points along stroke2), relative positions.
 */
fun analyzeRelation(stroke1: AnalyzedStroke, stroke2: AnalyzedStroke): StrokeRelation {
    val stroke2Mid = DrawingPoint(
        (stroke2.startPoint.x + stroke2.endPoint.x) / 2.0,
        (stroke2.startPoint.y + stroke2.endPoint.y) / 2.0,
    )
    val stroke2Quarter = DrawingPoint(
        stroke2.startPoint.x * 0.75 + stroke2.endPoint.x * 0.25,
        stroke2.startPoint.y * 0.75 + stroke2.endPoint.y * 0.25,
    )
    val stroke2ThreeQuarter = DrawingPoint(
        stroke2.startPoint.x * 0.25 + stroke2.endPoint.x * 0.75,
        stroke2.startPoint.y * 0.25 + stroke2.endPoint.y * 0.75,
    )

    // Find closest connection between stroke1 endpoints and 5 points along stroke2
    data class Candidate(val type: String, val dist: Double, val point: ConnectionPoint)
    val candidates = listOf(
        Candidate("start-start", distance(stroke1.startPoint, stroke2.startPoint), ConnectionPoint.START),
        Candidate("start-quarter", distance(stroke1.startPoint, stroke2Quarter), ConnectionPoint.START),
        Candidate("start-mid", distance(stroke1.startPoint, stroke2Mid), ConnectionPoint.MIDDLE),
        Candidate("start-3quarter", distance(stroke1.startPoint, stroke2ThreeQuarter), ConnectionPoint.END),
        Candidate("start-end", distance(stroke1.startPoint, stroke2.endPoint), ConnectionPoint.END),
        Candidate("end-start", distance(stroke1.endPoint, stroke2.startPoint), ConnectionPoint.START),
        Candidate("end-quarter", distance(stroke1.endPoint, stroke2Quarter), ConnectionPoint.START),
        Candidate("end-mid", distance(stroke1.endPoint, stroke2Mid), ConnectionPoint.MIDDLE),
        Candidate("end-3quarter", distance(stroke1.endPoint, stroke2ThreeQuarter), ConnectionPoint.END),
        Candidate("end-end", distance(stroke1.endPoint, stroke2.endPoint), ConnectionPoint.END),
    )

    val closest = candidates.minBy { it.dist }
    val connected = closest.dist < CONNECTION_THRESHOLD
    val connectionQuality = if (connected) maxOf(0.0, 1.0 - closest.dist / CONNECTION_THRESHOLD) else 0.0

    // Horizontal position: center-based
    val stroke1CenterX = stroke1.centerPoint.x
    val stroke2CenterX = stroke2.centerPoint.x
    val stroke2Width = stroke2.maxX - stroke2.minX
    val hThreshold = maxOf(H_POSITION_MIN_THRESHOLD, stroke2Width * H_POSITION_SCALE)

    val horizontalPosition = when {
        stroke1CenterX < stroke2CenterX - hThreshold -> HorizontalPosition.LEFT_OF
        stroke1CenterX > stroke2CenterX + hThreshold -> HorizontalPosition.RIGHT_OF
        else -> HorizontalPosition.OVERLAPPING
    }

    // Vertical position: center-based
    val stroke1CenterY = stroke1.centerPoint.y
    val stroke2CenterY = stroke2.centerPoint.y
    val stroke2Height = stroke2.maxY - stroke2.minY
    val vThreshold = maxOf(V_POSITION_MIN_THRESHOLD, stroke2Height * V_POSITION_SCALE)

    val verticalPosition = when {
        stroke1CenterY < stroke2CenterY - vThreshold -> VerticalPosition.ABOVE
        stroke1CenterY > stroke2CenterY + vThreshold -> VerticalPosition.BELOW
        else -> VerticalPosition.OVERLAPPING
    }

    return StrokeRelation(
        connected = connected,
        connectionType = if (connected) closest.type else "none",
        connectionPointOnStroke2 = if (connected) closest.point else ConnectionPoint.NONE,
        connectionQuality = connectionQuality,
        horizontalPosition = horizontalPosition,
        verticalPosition = verticalPosition,
    )
}


// =============================================================================
// Signature building
// =============================================================================

private val SELF_RELATION = StrokeRelation(
    connected = false,
    connectionType = "self",
    connectionPointOnStroke2 = ConnectionPoint.NONE,
    connectionQuality = 0.0,
    horizontalPosition = HorizontalPosition.OVERLAPPING,
    verticalPosition = VerticalPosition.OVERLAPPING,
)

private fun buildRelationMatrix(strokes: List<AnalyzedStroke>): List<List<StrokeRelation>> =
    strokes.indices.map { i ->
        strokes.indices.map { j ->
            if (i == j) SELF_RELATION else analyzeRelation(strokes[i], strokes[j])
        }
    }

/**
 * Create structural signature from user-drawn paths.
 * In flexible mode (free draw), splits at H↔V direction changes.
 * In strict mode (guided), splits at sharp corners.
 */
fun createSignature(paths: List<List<DrawingPoint>>, flexible: Boolean = false): StructuralSignature? {
    if (paths.isEmpty()) return null

    val allPoints = paths.flatten()
    val bbox = getBoundingBox(allPoints) ?: return null

    var segments = mutableListOf<List<DrawingPoint>>()
    val circleStrokes = mutableListOf<AnalyzedStroke>()

    for (path in paths) {
        val pathBbox = getBoundingBox(path)

        // Check if this individual path is a circle
        if (pathBbox != null && detectCircularPattern(listOf(path), pathBbox)) {
            val scale = maxOf(bbox.width, bbox.height).let { if (it == 0.0) 1.0 else it }
            circleStrokes.add(AnalyzedStroke(
                direction = StrokeDirection.CIRCLE,
                startPoint = DrawingPoint(
                    (pathBbox.minX + pathBbox.width / 2.0 - bbox.minX) / scale,
                    (pathBbox.minY - bbox.minY) / scale,
                ),
                endPoint = DrawingPoint(
                    (pathBbox.minX + pathBbox.width / 2.0 - bbox.minX) / scale,
                    (pathBbox.minY - bbox.minY) / scale,
                ),
                centerPoint = DrawingPoint(
                    (pathBbox.minX + pathBbox.width / 2.0 - bbox.minX) / scale,
                    (pathBbox.minY + pathBbox.height / 2.0 - bbox.minY) / scale,
                ),
                minX = (pathBbox.minX - bbox.minX) / scale,
                maxX = (pathBbox.maxX - bbox.minX) / scale,
                minY = (pathBbox.minY - bbox.minY) / scale,
                maxY = (pathBbox.maxY - bbox.minY) / scale,
                angle = 0.0,
                length = PI * maxOf(pathBbox.width, pathBbox.height) / scale,
                isCurved = true,
            ))
        } else if (flexible) {
            segments.addAll(splitPathAtDirectionFlips(path))
        } else {
            segments.addAll(splitPathAtCorners(path, 50.0))
        }
    }

    // Merge connected segments (skip in flex mode — direction flips are intentional)
    if (!flexible) {
        segments = mergeConnectedSegments(segments, bbox).toMutableList()
    }

    // Deduplicate overlapping segments
    segments = deduplicateOverlappingSegments(segments, bbox).toMutableList()

    // If only circles, return early
    if (circleStrokes.isNotEmpty() && segments.isEmpty()) {
        val relations = buildRelationMatrix(circleStrokes)
        return StructuralSignature(circleStrokes.size, circleStrokes, relations)
    }

    // Analyze each segment, filter short ones
    val strokes = circleStrokes.toMutableList()
    for (segment in segments) {
        val analyzed = analyzeStroke(segment, bbox)
        if (analyzed != null && analyzed.length > MIN_SEGMENT_LENGTH) {
            strokes.add(analyzed)
        }
    }

    if (strokes.isEmpty()) return null

    val relations = buildRelationMatrix(strokes)
    return StructuralSignature(strokes.size, strokes, relations)
}

/**
 * Create signature from reference stroke definitions.
 * Reference strokes are already well-defined segments — no splitting needed.
 * Marks curve strokes as flexDirection, circle strokes as CIRCLE direction.
 */
fun createRefSignature(strokeDefs: List<ReferenceStroke>): StructuralSignature? {
    val allPoints = mutableListOf<DrawingPoint>()
    val paths = strokeDefs.map { s ->
        val points = sampleStrokePath(s, 20)
        allPoints.addAll(points)
        points
    }

    val bbox = getBoundingBox(allPoints) ?: return null

    val strokes = mutableListOf<AnalyzedStroke>()
    for (i in paths.indices) {
        val analyzed = analyzeStroke(paths[i], bbox) ?: continue
        val modified = when (strokeDefs[i].type) {
            StrokeType.CURVE -> analyzed.copy(flexDirection = true, isCurved = true)
            StrokeType.CIRCLE -> analyzed.copy(direction = StrokeDirection.CIRCLE, isCurved = true)
            else -> analyzed
        }
        strokes.add(modified)
    }

    if (strokes.isEmpty()) return null

    val relations = buildRelationMatrix(strokes)
    return StructuralSignature(strokes.size, strokes, relations)
}
