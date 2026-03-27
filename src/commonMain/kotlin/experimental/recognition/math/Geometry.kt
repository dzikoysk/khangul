package experimental.recognition.math

import experimental.recognition.DrawingPoint
import kotlin.math.PI
import kotlin.math.abs

fun DrawingPoint.toVec2(): Vec2 = Vec2(x, y)

fun Vec2.toPoint(): DrawingPoint = DrawingPoint(x, y)

/** Shortest distance from [point] to any point on the line segment from [lineStart] to [lineEnd]. */
fun pointToLineDistance(point: Vec2, lineStart: Vec2, lineEnd: Vec2): Double {
    val segment = lineEnd - lineStart
    val lengthSquared = segment.dot(segment)
    if (lengthSquared < 0.001) {
        return (point - lineStart).length
    }
    val projection = ((point - lineStart).dot(segment) / lengthSquared).coerceIn(0.0, 1.0)
    val closestPoint = lineStart + segment * projection
    return (point - closestPoint).length
}

/**
 * Check if two angles (in radians) point in roughly the same or opposite direction,
 * within the given threshold (in radians).
 */
fun areParallelOrAntiParallel(angleA: Double, angleB: Double, threshold: Double): Boolean {
    val rawDifference = abs(angleA - angleB)
    val parallelDifference = if (rawDifference > PI) 2.0 * PI - rawDifference else rawDifference
    if (parallelDifference < threshold) return true
    val antiParallelDifference = abs(parallelDifference - PI)
    return antiParallelDifference < threshold
}
