@file:OptIn(ExperimentalJsExport::class)

package experimental.recognition

import Letter
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

// --- Input types ---

@JsExport
data class DrawingPoint(val x: Double, val y: Double)

// --- Reference stroke types ---

@JsExport
enum class StrokeType { LINE, CURVE, CIRCLE }

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

// --- Analysis types ---

enum class StrokeDirection { HORIZONTAL, VERTICAL, CIRCLE }

data class AnalyzedStroke(
    val direction: StrokeDirection,
    val startPoint: DrawingPoint,
    val endPoint: DrawingPoint,
    val centerPoint: DrawingPoint,
    val minX: Double,
    val maxX: Double,
    val minY: Double,
    val maxY: Double,
    val angle: Double,
    val length: Double,
    val isCurved: Boolean,
    val flexDirection: Boolean = false,
)

enum class HorizontalPosition { LEFT_OF, RIGHT_OF, OVERLAPPING }
enum class VerticalPosition { ABOVE, BELOW, OVERLAPPING }
enum class ConnectionPoint { START, MIDDLE, END, NONE }

data class StrokeRelation(
    val connected: Boolean,
    val connectionType: String,
    val connectionPointOnStroke2: ConnectionPoint,
    val connectionQuality: Double,
    val horizontalPosition: HorizontalPosition,
    val verticalPosition: VerticalPosition,
)

data class StructuralSignature(
    val strokeCount: Int,
    val strokes: List<AnalyzedStroke>,
    val relations: List<List<StrokeRelation>>,
)

// --- Geometry types ---

data class BoundingBox(
    val minX: Double,
    val minY: Double,
    val maxX: Double,
    val maxY: Double,
) {
    val width: Double get() = maxX - minX
    val height: Double get() = maxY - minY
}

// --- Result types ---

@JsExport
data class RecognitionResult(
    val letter: Letter,
    val coverage: Int,
    val debug: String = "",
)
