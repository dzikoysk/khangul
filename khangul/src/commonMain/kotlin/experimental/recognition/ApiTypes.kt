@file:OptIn(ExperimentalJsExport::class)

package experimental.recognition

import Letter
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
data class DrawingPoint(
    val x: Double,
    val y: Double,
)

@JsExport
enum class StrokeType {
    LINE,
    CURVE,
    CIRCLE
}

@JsExport
data class ReferenceStroke(
    val startX: Double,
    val startY: Double,
    val endX: Double,
    val endY: Double,
    val type: StrokeType,
    val controlX: Double? = null,
    val controlY: Double? = null,
)

@JsExport
data class RecognitionResult(
    val letter: Letter,
    val coverage: Int,
    val debug: String = "",
)
