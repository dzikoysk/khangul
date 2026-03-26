package experimental.recognition

import kotlin.math.sqrt

// =============================================================================
// Guided mode: strict stroke-by-stroke validation
//
// Simpler than free draw — user follows a specific stroke in order.
// Validates start position and coverage of expected path.
// =============================================================================

private const val GUIDED_MIN_POINTS = 4
private const val GUIDED_START_DISTANCE = 25.0      // max distance from stroke start (in 0-100 space)
private const val GUIDED_SAMPLE_COUNT = 30           // reference points to check coverage against
private const val GUIDED_COVERAGE_THRESHOLD = 0.70   // fraction of reference points that must be covered
private const val GUIDED_THRESHOLD_BASE = 15.0       // base threshold for point-to-point distance
private const val GUIDED_THRESHOLD_OFFSET = 18.0     // additive offset in threshold calculation

/**
 * Validate a single user-drawn stroke against a reference stroke definition.
 *
 * @param userPoints Points drawn by the user (in canvas pixel coordinates)
 * @param stroke Reference stroke definition (in 0-100 coordinate space)
 * @param canvasSize Canvas dimension in pixels
 * @return true if the stroke is valid (covers ≥70% of the reference path)
 */
fun validateStroke(
    userPoints: List<DrawingPoint>,
    stroke: ReferenceStroke,
    canvasSize: Double,
): Boolean {
    if (userPoints.size < GUIDED_MIN_POINTS) return false

    val scale = canvasSize / 100.0
    val expected = sampleStrokePath(stroke, GUIDED_SAMPLE_COUNT)

    // Direction check: user start should be near stroke start
    val userStart = userPoints.first()
    val userEnd = userPoints.last()
    val startDist = distance(
        DrawingPoint(userStart.x / scale, userStart.y / scale),
        DrawingPoint(stroke.startX, stroke.startY),
    )
    val endDist = distance(
        DrawingPoint(userEnd.x / scale, userEnd.y / scale),
        DrawingPoint(stroke.endX, stroke.endY),
    )

    if (startDist > GUIDED_START_DISTANCE && endDist > GUIDED_START_DISTANCE) return false

    // Coverage check: fraction of expected points covered by user drawing
    var coveredCount = 0
    for (ep in expected) {
        val epScaled = DrawingPoint(ep.x * scale, ep.y * scale)
        var minDist = Double.MAX_VALUE
        for (up in userPoints) {
            val d = distance(epScaled, up)
            if (d < minDist) minDist = d
        }
        if (minDist < GUIDED_THRESHOLD_BASE * scale * 0.01 * canvasSize * 0.01 + GUIDED_THRESHOLD_OFFSET) {
            coveredCount++
        }
    }

    val coverage = coveredCount.toDouble() / expected.size
    return coverage >= GUIDED_COVERAGE_THRESHOLD
}
