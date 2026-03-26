package experimental.recognition

import kotlin.math.*

// --- Shared math primitives used across pipeline stages ---

fun distance(a: DrawingPoint, b: DrawingPoint): Double =
    sqrt((a.x - b.x).pow(2) + (a.y - b.y).pow(2))

fun getBoundingBox(points: List<DrawingPoint>): BoundingBox? {
    if (points.isEmpty()) return null
    var minX = Double.MAX_VALUE
    var minY = Double.MAX_VALUE
    var maxX = -Double.MAX_VALUE
    var maxY = -Double.MAX_VALUE
    for (p in points) {
        if (p.x < minX) minX = p.x
        if (p.y < minY) minY = p.y
        if (p.x > maxX) maxX = p.x
        if (p.y > maxY) maxY = p.y
    }
    return BoundingBox(minX, minY, maxX, maxY)
}

/**
 * Classify direction as H or V based on angle from horizontal.
 * ≤45° = HORIZONTAL, >45° = VERTICAL.
 * Very short strokes (length < 0.05) default to HORIZONTAL.
 */
fun classifyDirection(angle: Double, length: Double): StrokeDirection {
    if (length < 0.05) return StrokeDirection.HORIZONTAL
    val normalized = ((angle % 360.0) + 360.0) % 360.0
    var absAngle = normalized % 180.0
    if (absAngle > 90.0) absAngle = 180.0 - absAngle
    return if (absAngle <= 45.0) StrokeDirection.HORIZONTAL else StrokeDirection.VERTICAL
}

/**
 * Perpendicular distance from point (px, py) to line segment (x1,y1)→(x2,y2).
 */
fun pointToLineDistance(px: Double, py: Double, x1: Double, y1: Double, x2: Double, y2: Double): Double {
    val dx = x2 - x1
    val dy = y2 - y1
    val lenSq = dx * dx + dy * dy
    if (lenSq < 0.001) {
        return sqrt((px - x1).pow(2) + (py - y1).pow(2))
    }
    val t = ((px - x1) * dx + (py - y1) * dy) / lenSq
    val clamped = t.coerceIn(0.0, 1.0)
    val projX = x1 + clamped * dx
    val projY = y1 + clamped * dy
    return sqrt((px - projX).pow(2) + (py - projY).pow(2))
}

/**
 * Sample points along a reference stroke definition.
 * Lines: linear interpolation.
 * Curves: quadratic Bézier.
 * Circles: parametric ellipse (startX=centerX, startY=top, endY=bottom).
 */
fun sampleStrokePath(stroke: ReferenceStroke, numSamples: Int = 20): List<DrawingPoint> {
    val points = mutableListOf<DrawingPoint>()
    for (i in 0..numSamples) {
        val t = i.toDouble() / numSamples
        when (stroke.type) {
            StrokeType.LINE -> {
                points.add(DrawingPoint(
                    x = stroke.startX + (stroke.endX - stroke.startX) * t,
                    y = stroke.startY + (stroke.endY - stroke.startY) * t,
                ))
            }
            StrokeType.CURVE -> {
                val cx = stroke.controlX ?: continue
                val cy = stroke.controlY ?: continue
                val mt = 1.0 - t
                points.add(DrawingPoint(
                    x = mt * mt * stroke.startX + 2.0 * mt * t * cx + t * t * stroke.endX,
                    y = mt * mt * stroke.startY + 2.0 * mt * t * cy + t * t * stroke.endY,
                ))
            }
            StrokeType.CIRCLE -> {
                val centerX = stroke.startX
                val centerY = (stroke.startY + stroke.endY) / 2.0
                val radiusY = (stroke.endY - stroke.startY) / 2.0
                val radiusX = stroke.controlX ?: radiusY
                val angle = -PI / 2.0 + t * PI * 2.0
                points.add(DrawingPoint(
                    x = centerX + radiusX * cos(angle),
                    y = centerY + radiusY * sin(angle),
                ))
            }
        }
    }
    return points
}
