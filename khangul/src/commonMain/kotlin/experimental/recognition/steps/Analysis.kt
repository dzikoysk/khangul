package experimental.recognition.steps

import experimental.recognition.*
import experimental.recognition.math.*
import kotlin.math.PI

// Pipeline: analyzeStroke (per segment) → analyzeRelation (pairwise) → signature

private const val MIN_SEGMENT_LENGTH = 0.10
private const val CURVE_DEVIATION_THRESHOLD = 0.35

private const val CONNECTION_THRESHOLD = 0.15
private const val H_POSITION_MIN_THRESHOLD = 0.05
private const val H_POSITION_SCALE = 0.30
private const val V_POSITION_MIN_THRESHOLD = 0.05
private const val V_POSITION_SCALE = 0.30

/**
 * Analyze a segment of raw points into an [AnalyzedStroke].
 * Normalizes coordinates to 0-1 space, classifies H/V direction, and measures curvature
 * (how far the path deviates from a straight line between start and end).
 */
fun analyzeStroke(points: List<DrawingPoint>, bbox: BoundingBox): AnalyzedStroke? {
    if (points.size < 2) return null

    val normalizedPoints = points.map { bbox.normalize(it.toVec2()) }
    val start = normalizedPoints.first()
    val end = normalizedPoints.last()
    val bounds = normalizedPoints.boundingBox() ?: return null
    val center = normalizedPoints.centroid()

    val angle = start.angleTo(end)
    val length = start.distanceTo(end)
    val maxDeviation = normalizedPoints.maxOf { pointToLineDistance(it, start, end) }

    return AnalyzedStroke(
        direction = classifyDirection(angle, length),
        startPoint = start.toPoint(),
        endPoint = end.toPoint(),
        centerPoint = center.toPoint(),
        minX = bounds.minX, maxX = bounds.maxX,
        minY = bounds.minY, maxY = bounds.maxY,
        angle = angle,
        length = length,
        isCurved = maxDeviation > CURVE_DEVIATION_THRESHOLD,
    )
}

/**
 * Analyze the spatial relationship between two strokes.
 *
 * Tests stroke1's endpoints against 5 evenly spaced points along stroke2 to find
 * the closest connection. Also determines relative horizontal/vertical positioning
 * using center-based comparison with adaptive thresholds.
 */
fun analyzeRelation(stroke1: AnalyzedStroke, stroke2: AnalyzedStroke): StrokeRelation {
    val stroke1Start = stroke1.startPoint.toVec2()
    val stroke1End = stroke1.endPoint.toVec2()
    val stroke2Start = stroke2.startPoint.toVec2()
    val stroke2End = stroke2.endPoint.toVec2()

    val samplePoints = listOf(0.0, 0.25, 0.5, 0.75, 1.0).map { stroke2Start.lerp(stroke2End, it) }
    val sampleLabels = listOf(ConnectionPoint.START, ConnectionPoint.START, ConnectionPoint.MIDDLE, ConnectionPoint.END, ConnectionPoint.END)

    data class Candidate(val type: String, val distance: Double, val point: ConnectionPoint)

    val sampleNames = listOf("start", "quarter", "mid", "3quarter", "end")
    val candidates = mutableListOf<Candidate>()
    for ((index, samplePoint) in samplePoints.withIndex()) {
        candidates.add(Candidate("start-${sampleNames[index]}", stroke1Start.distanceTo(samplePoint), sampleLabels[index]))
        candidates.add(Candidate("end-${sampleNames[index]}", stroke1End.distanceTo(samplePoint), sampleLabels[index]))
    }

    val closest = candidates.minBy { it.distance }
    val connected = closest.distance < CONNECTION_THRESHOLD
    val connectionQuality = if (connected) linearFalloff(closest.distance / CONNECTION_THRESHOLD, rate = 1.0) else 0.0

    val stroke2Width = stroke2.maxX - stroke2.minX
    val horizontalThreshold = maxOf(H_POSITION_MIN_THRESHOLD, stroke2Width * H_POSITION_SCALE)
    val horizontalPosition = when {
        stroke1.centerPoint.x < stroke2.centerPoint.x - horizontalThreshold -> HorizontalPosition.LEFT_OF
        stroke1.centerPoint.x > stroke2.centerPoint.x + horizontalThreshold -> HorizontalPosition.RIGHT_OF
        else -> HorizontalPosition.OVERLAPPING
    }

    val stroke2Height = stroke2.maxY - stroke2.minY
    val verticalThreshold = maxOf(V_POSITION_MIN_THRESHOLD, stroke2Height * V_POSITION_SCALE)
    val verticalPosition = when {
        stroke1.centerPoint.y < stroke2.centerPoint.y - verticalThreshold -> VerticalPosition.ABOVE
        stroke1.centerPoint.y > stroke2.centerPoint.y + verticalThreshold -> VerticalPosition.BELOW
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
    val bbox = allPoints.map { it.toVec2() }.boundingBox() ?: return null

    var segments = mutableListOf<List<DrawingPoint>>()
    val circleStrokes = mutableListOf<AnalyzedStroke>()

    for (path in paths) {
        val pathBbox = path.map { it.toVec2() }.boundingBox()

        if (pathBbox != null && detectCircularPattern(listOf(path), pathBbox)) {
            val scale = bbox.scale
            val pathCenter = pathBbox.center
            circleStrokes.add(AnalyzedStroke(
                direction = StrokeDirection.CIRCLE,
                startPoint = DrawingPoint(
                    (pathCenter.x - bbox.minX) / scale,
                    (pathBbox.minY - bbox.minY) / scale,
                ),
                endPoint = DrawingPoint(
                    (pathCenter.x - bbox.minX) / scale,
                    (pathBbox.minY - bbox.minY) / scale,
                ),
                centerPoint = DrawingPoint(
                    (pathCenter.x - bbox.minX) / scale,
                    (pathCenter.y - bbox.minY) / scale,
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

    // Skip merging in flex mode — direction flips are intentional split points
    if (!flexible) {
        segments = mergeConnectedSegments(segments, bbox).toMutableList()
    }
    segments = deduplicateOverlappingSegments(segments, bbox).toMutableList()

    if (circleStrokes.isNotEmpty() && segments.isEmpty()) {
        val relations = buildRelationMatrix(circleStrokes)
        return StructuralSignature(circleStrokes.size, circleStrokes, relations)
    }

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
 * Create signature from reference stroke definitions (flat list of all segments).
 * Unlike user signatures, reference strokes are already clean segments — no splitting needed.
 */
fun createRefSignature(strokeDefs: List<ReferenceStroke>): StructuralSignature? {
    val allPoints = mutableListOf<DrawingPoint>()
    val paths = strokeDefs.map { strokeDef ->
        val points = sampleStrokePath(strokeDef, 20)
        allPoints.addAll(points)
        points
    }

    val bbox = allPoints.map { it.toVec2() }.boundingBox() ?: return null

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

/**
 * Create signature from reference steps (nested array structure).
 * Flattens to individual segments for structural analysis.
 */
fun createRefSignatureFromSteps(steps: Array<Array<ReferenceStroke>>): StructuralSignature? =
    createRefSignature(steps.flatMap { it.toList() })
