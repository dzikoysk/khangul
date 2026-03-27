package experimental.recognition.steps

import experimental.recognition.DrawingPoint

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
