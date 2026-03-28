package experimental.recognition

import experimental.recognition.math.*

private const val GUIDED_MIN_POINTS = 4
private const val GUIDED_START_DISTANCE = 25.0
private const val GUIDED_SAMPLE_COUNT = 30
private const val GUIDED_COVERAGE_THRESHOLD = 0.70
private const val GUIDED_THRESHOLD_BASE = 15.0
private const val GUIDED_THRESHOLD_OFFSET = 18.0

/**
 * Validate a single user-drawn stroke against a reference stroke definition.
 *
 * Checks two things:
 * 1. The user started drawing near either end of the reference stroke
 * 2. At least 70% of the reference path is covered by the user's drawing
 *
 * Coordinates: [userPoints] are in canvas pixels, [stroke] is in 0-100 space.
 */
fun validateStroke(
    userPoints: List<DrawingPoint>,
    stroke: ReferenceStroke,
    canvasSize: Double,
): Boolean {
    if (userPoints.size < GUIDED_MIN_POINTS) return false

    val scale = canvasSize / 100.0
    val expected = sampleStrokePath(stroke, GUIDED_SAMPLE_COUNT)

    val userStart = userPoints.first().toVec2() / scale
    val userEnd = userPoints.last().toVec2() / scale
    val strokeStart = Vec2(stroke.startX, stroke.startY)
    val strokeEnd = Vec2(stroke.endX, stroke.endY)

    if (userStart.distanceTo(strokeStart) > GUIDED_START_DISTANCE &&
        userEnd.distanceTo(strokeEnd) > GUIDED_START_DISTANCE) return false

    val coverageThreshold = GUIDED_THRESHOLD_BASE * scale * 0.01 * canvasSize * 0.01 + GUIDED_THRESHOLD_OFFSET
    var coveredCount = 0

    for (expectedPoint in expected) {
        val scaledExpectedPoint = expectedPoint.toVec2() * scale
        var minDistance = Double.MAX_VALUE
        for (userPoint in userPoints) {
            val distance = scaledExpectedPoint.distanceTo(userPoint.toVec2())
            if (distance < minDistance) minDistance = distance
        }
        if (minDistance < coverageThreshold) {
            coveredCount++
        }
    }

    return coveredCount.toDouble() / expected.size >= GUIDED_COVERAGE_THRESHOLD
}
