package experimental.recognition

import experimental.recognition.math.*
import experimental.recognition.steps.StrokeDirection
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
 * Sample points along a reference stroke definition.
 * Lines: linear interpolation.
 * Curves: quadratic Bézier.
 * Circles: parametric ellipse.
 */
fun sampleStrokePath(stroke: ReferenceStroke, numSamples: Int = 20): List<DrawingPoint> {
    val points = mutableListOf<DrawingPoint>()
    for (sampleIndex in 0..numSamples) {
        val interpolation = sampleIndex.toDouble() / numSamples
        when (stroke.type) {
            StrokeType.LINE -> {
                val start = Vec2(stroke.startX, stroke.startY)
                val end = Vec2(stroke.endX, stroke.endY)
                points.add(start.lerp(end, interpolation).toPoint())
            }
            StrokeType.CURVE -> {
                val controlX = stroke.controlX ?: continue
                val controlY = stroke.controlY ?: continue
                val inverseInterpolation = 1.0 - interpolation
                points.add(DrawingPoint(
                    x = inverseInterpolation * inverseInterpolation * stroke.startX + 2.0 * inverseInterpolation * interpolation * controlX + interpolation * interpolation * stroke.endX,
                    y = inverseInterpolation * inverseInterpolation * stroke.startY + 2.0 * inverseInterpolation * interpolation * controlY + interpolation * interpolation * stroke.endY,
                ))
            }
            StrokeType.CIRCLE -> {
                val centerX = stroke.startX
                val centerY = (stroke.startY + stroke.endY) / 2.0
                val radiusY = (stroke.endY - stroke.startY) / 2.0
                val radiusX = stroke.controlX ?: radiusY
                val angle = -PI / 2.0 + interpolation * PI * 2.0

                points.add(
                    DrawingPoint(
                        x = centerX + radiusX * cos(angle),
                        y = centerY + radiusY * sin(angle),
                    ),
                )
            }
        }
    }
    return points
}
