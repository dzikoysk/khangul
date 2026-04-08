package experimental.recognition

import experimental.recognition.math.*

private const val GUIDED_MIN_POINTS = 4
private const val GUIDED_START_DISTANCE = 25.0
private const val GUIDED_SAMPLE_COUNT = 30
private const val GUIDED_COVERAGE_THRESHOLD = 0.70
private const val GUIDED_THRESHOLD_BASE = 15.0
private const val GUIDED_THRESHOLD_OFFSET = 18.0

/**
 * Validate a user-drawn path against a step (one or more connected reference segments).
 *
 * Checks two things:
 * 1. The user started drawing near the start of the first segment (or end of the last)
 * 2. At least 70% of the combined reference path is covered by the user's drawing
 *
 * Coordinates: [userPoints] are in canvas pixels, segments are in 0-100 space.
 */
fun validateStep(
    userPoints: List<DrawingPoint>,
    segments: List<ReferenceStroke>,
    canvasSize: Double,
): Boolean {
    if (userPoints.size < GUIDED_MIN_POINTS || segments.isEmpty()) return false

    val scale = canvasSize / 100.0

    // Sample all segments and concatenate into one reference path
    val expected = segments.flatMap { sampleStrokePath(it, GUIDED_SAMPLE_COUNT) }

    val userStart = userPoints.first().toVec2() / scale
    val userEnd = userPoints.last().toVec2() / scale
    val firstSegment = segments.first()
    val lastSegment = segments.last()
    val stepStart = Vec2(firstSegment.startX, firstSegment.startY)
    val stepEnd = Vec2(lastSegment.endX, lastSegment.endY)

    if (userStart.distanceTo(stepStart) > GUIDED_START_DISTANCE &&
        userEnd.distanceTo(stepEnd) > GUIDED_START_DISTANCE) return false

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
