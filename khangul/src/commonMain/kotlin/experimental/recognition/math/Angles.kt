package experimental.recognition.math

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos

fun Double.toDegrees(): Double = this * (180.0 / PI)

fun Double.toRadians(): Double = this * (PI / 180.0)

/**
 * Angle between two direction vectors in degrees (via dot product + acos).
 * Returns null when either vector is near-zero length.
 */
fun angleBetween(v1: Vec2, v2: Vec2, minLength: Double = 0.001): Double? {
    val len1 = v1.length
    val len2 = v2.length
    if (len1 < minLength || len2 < minLength) return null
    val dot = v1.dot(v2) / (len1 * len2)
    return acos(dot.coerceIn(-1.0, 1.0)).toDegrees()
}

/**
 * Normalize angle to [0, 180) range — for direction-agnostic comparison.
 */
fun normalizeAngleTo180(degrees: Double): Double {
    val n = ((degrees % 180.0) + 180.0) % 180.0
    return n
}

/**
 * Smallest angular difference in [0, 90] range between two angles.
 * Both angles are first normalized to [0, 180), then the shortest distance is returned.
 */
fun angleDifference180(a: Double, b: Double): Double {
    val na = normalizeAngleTo180(a)
    val nb = normalizeAngleTo180(b)
    var diff = abs(na - nb)
    if (diff > 90.0) diff = 180.0 - diff
    return diff
}
