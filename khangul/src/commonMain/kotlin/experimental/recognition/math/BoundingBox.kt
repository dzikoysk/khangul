package experimental.recognition.math

data class BoundingBox(
    val minX: Double,
    val minY: Double,
    val maxX: Double,
    val maxY: Double,
) {
    val width: Double get() = maxX - minX
    val height: Double get() = maxY - minY

    /** Max dimension, floored at 1.0 to avoid division by zero. */
    val scale: Double get() = maxOf(width, height).let { if (it == 0.0) 1.0 else it }

    val center: Vec2 get() = Vec2(minX + width / 2.0, minY + height / 2.0)

    /** Normalize a point into 0..1 × 0..1 unit square (each axis scaled independently). */
    fun normalize(point: Vec2): Vec2 = Vec2(
        if (width > 0) (point.x - minX) / width else 0.5,
        if (height > 0) (point.y - minY) / height else 0.5,
    )
}
