package experimental.recognition.math

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vec2(
    val x: Double,
    val y: Double,
) {

    val length: Double get() = sqrt(x * x + y * y)

    operator fun plus(other: Vec2): Vec2 = Vec2(x + other.x, y + other.y)
    operator fun minus(other: Vec2): Vec2 = Vec2(x - other.x, y - other.y)
    operator fun times(scalar: Double): Vec2 = Vec2(x * scalar, y * scalar)
    operator fun div(scalar: Double): Vec2 = Vec2(x / scalar, y / scalar)

    fun distanceTo(other: Vec2): Double = (this - other).length

    /**
     * Dot product — measures how much two vectors point in the same direction.
     * Returns 0 when perpendicular, positive when similar direction, negative when opposite.
     */
    fun dot(other: Vec2): Double = x * other.x + y * other.y

    /**
     * Linear interpolation between this point and [other].
     * At t=0.0 returns this, at t=0.5 returns the midpoint, at t=1.0 returns [other].
     */
    fun lerp(other: Vec2, t: Double): Vec2 = this + (other - this) * t

    /** Angle from this point to [other] in degrees, measured from the positive X axis. */
    fun angleTo(other: Vec2): Double = atan2(other.y - y, other.x - x).toDegrees()

    /**
     * Scalar projection onto an axis defined by [axisAngleRadians].
     * Projects this point onto a line at the given angle, returning the signed distance along it.
     * Used for measuring overlap between segments along a shared direction.
     */
    fun projectOnto(axisAngleRadians: Double): Double = x * cos(axisAngleRadians) + y * sin(axisAngleRadians)

}

fun List<Vec2>.centroid(): Vec2 {
    var sumX = 0.0
    var sumY = 0.0
    for (point in this) {
        sumX += point.x
        sumY += point.y
    }
    return Vec2(sumX / size, sumY / size)
}

fun List<Vec2>.boundingBox(): BoundingBox? {
    if (isEmpty()) return null
    var minX = Double.MAX_VALUE
    var minY = Double.MAX_VALUE
    var maxX = -Double.MAX_VALUE
    var maxY = -Double.MAX_VALUE
    for (point in this) {
        if (point.x < minX) minX = point.x
        if (point.y < minY) minY = point.y
        if (point.x > maxX) maxX = point.x
        if (point.y > maxY) maxY = point.y
    }
    return BoundingBox(minX, minY, maxX, maxY)
}
