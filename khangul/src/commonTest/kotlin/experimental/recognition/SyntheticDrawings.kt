package experimental.recognition

import Letter
import Letters
import experimental.recognition.sampleStrokePath
import kotlin.math.*

/**
 * Generate synthetic drawings from reference stroke data for testing.
 * These are "perfect" drawings — exact paths following the reference definitions,
 * with slight noise on circles to simulate real touch input.
 *
 * Each step (pen down/up action) produces one path. Steps with multiple connected
 * segments concatenate their sampled points into a single continuous path.
 */
object SyntheticDrawings {

    /**
     * Circle detection's shape check samples every N/60 points and measures
     * angle between triplets. A mathematically perfect circle has near-zero
     * angle changes between uniformly sampled triplets, causing it to be
     * rejected as "too many straight sections." Real hand-drawn circles have
     * natural jitter that produces measurable curvature variation.
     *
     * We add slight deterministic noise (seeded RNG) to synthetic circles
     * to simulate realistic input while keeping results reproducible.
     */
    private const val CIRCLE_POINTS = 200

    // Minimal sinusoidal wobble so the circle detection's shape check sees curvature
    // variation (angles > 10° between sampled triplets) but no corners (< 35°).
    // amplitude=0.8 in 0-100 space is ~2.7% of a radius-30 circle — barely visible.
    // frequency=11 distributes the wobble evenly so no long straight runs remain.
    private const val CIRCLE_WOBBLE_AMPLITUDE = 0.4
    private const val CIRCLE_WOBBLE_FREQUENCY = 13.0

    private fun applyCircleWobble(base: List<DrawingPoint>, stroke: ReferenceStroke): List<DrawingPoint> {
        val cx = stroke.startX
        val cy = (stroke.startY + stroke.endY) / 2.0
        return base.mapIndexed { i, p ->
            val t = i.toDouble() / CIRCLE_POINTS
            val wobble = sin(t * 2.0 * PI * CIRCLE_WOBBLE_FREQUENCY) * CIRCLE_WOBBLE_AMPLITUDE
            val dx = p.x - cx
            val dy = p.y - cy
            val dist = sqrt(dx * dx + dy * dy)
            if (dist < 0.01) p else {
                val s = (dist + wobble) / dist
                DrawingPoint(cx + dx * s, cy + dy * s)
            }
        }
    }

    /**
     * Sample a single step (one or more connected segments) into a single path of points.
     * For circle segments, applies wobble when [wobble] is true.
     */
    private fun sampleStep(segments: Array<ReferenceStroke>, pointsPerStroke: Int, wobble: Boolean): List<DrawingPoint> {
        val allPoints = mutableListOf<DrawingPoint>()
        for (segment in segments) {
            val points = if (segment.type == StrokeType.CIRCLE) {
                val sampled = sampleStrokePath(segment, CIRCLE_POINTS)
                if (wobble) applyCircleWobble(sampled, segment) else sampled
            } else {
                sampleStrokePath(segment, pointsPerStroke)
            }
            // Skip first point of subsequent segments to avoid duplicate at the junction
            if (allPoints.isNotEmpty() && points.isNotEmpty()) {
                allPoints.addAll(points.drop(1))
            } else {
                allPoints.addAll(points)
            }
        }
        return allPoints
    }

    /**
     * Generate a drawing with slight wobble on circles (simulates hand-drawn input).
     * Returns one path per step (not per segment).
     */
    fun wobblyDrawing(letter: Letter, pointsPerStroke: Int = 50): List<List<DrawingPoint>> {
        val steps = letter.referenceStrokes.toList()
        if (steps.isEmpty()) return emptyList()
        return steps.map { sampleStep(it, pointsPerStroke, wobble = true) }
    }

    /**
     * Generate a mathematically clean drawing — no wobble, exact paths.
     * Returns one path per step (not per segment).
     */
    fun cleanDrawing(letter: Letter, pointsPerStroke: Int = 50): List<List<DrawingPoint>> {
        val steps = letter.referenceStrokes.toList()
        if (steps.isEmpty()) return emptyList()
        return steps.map { sampleStep(it, pointsPerStroke, wobble = false) }
    }

    fun allWobblyDrawings(pointsPerStroke: Int = 50): Map<Letter, List<List<DrawingPoint>>> =
        Letters.getAll().associateWith { wobblyDrawing(it, pointsPerStroke) }

    fun allCleanDrawings(pointsPerStroke: Int = 50): Map<Letter, List<List<DrawingPoint>>> =
        Letters.getAll().associateWith { cleanDrawing(it, pointsPerStroke) }

    /** ㅢ with horizontal touching the vertical — must not be confused with ㅓ */
    fun uiConnected(pointsPerStroke: Int = 50): List<List<DrawingPoint>> = listOf(
        sampleStrokePath(ReferenceStroke(10.0, 58.0, 66.0, 58.0, StrokeType.LINE), pointsPerStroke),
        sampleStrokePath(ReferenceStroke(66.0, 10.0, 66.0, 90.0, StrokeType.LINE), pointsPerStroke),
    )

}
