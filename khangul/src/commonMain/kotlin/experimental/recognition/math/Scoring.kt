package experimental.recognition.math

import kotlin.math.pow

/**
 * Linear falloff: max(floor, 1.0 - value * rate).
 * Score decreases linearly as value increases, clamped to [floor, 1.0].
 */
fun linearFalloff(value: Double, rate: Double, floor: Double = 0.0): Double =
    maxOf(floor, 1.0 - value * rate)

/**
 * Quadratic falloff: max(0.0, 1.0 - (value * rate)^2).
 * Score decreases quadratically as value increases, clamped to [0.0, 1.0].
 */
fun quadraticFalloff(value: Double, rate: Double): Double =
    maxOf(0.0, 1.0 - (value * rate).pow(2))
