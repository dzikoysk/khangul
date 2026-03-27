@file:OptIn(ExperimentalJsExport::class)

package experimental.recognition

import Letter
import Letters
import experimental.recognition.steps.recognizeShape
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Facade API for Hangul drawing recognition.
 *
 * Two modes:
 * - Free draw: [recognize] — user draws freely, system identifies the letter
 * - Guided: [validateGuidedStroke] — user follows stroke-by-stroke, system validates each
 */
@JsExport
class HangulRecognizer {

    var matchThreshold: Int = 40

    fun recognize(paths: List<List<DrawingPoint>>): List<RecognitionResult> =
        recognizeShape(paths, flexible = true)

    @JsName("recognizeFromArrays")
    fun recognize(paths: Array<Array<DrawingPoint>>): Array<RecognitionResult> =
        recognize(paths.map { it.toList() }).toTypedArray()

    fun isMatch(paths: List<List<DrawingPoint>>, expected: Letter): Boolean {
        val top = recognize(paths).firstOrNull() ?: return false
        return top.letter == expected && top.coverage >= matchThreshold
    }

    @JsName("isMatchFromArrays")
    fun isMatch(paths: Array<Array<DrawingPoint>>, expected: Letter): Boolean =
        isMatch(paths.map { it.toList() }, expected)

    @JsName("validateGuidedStrokeFromArray")
    fun validateGuidedStroke(
        userPoints: Array<DrawingPoint>,
        letter: Letter,
        strokeIndex: Int,
        canvasSize: Double,
    ): Boolean {
        if (strokeIndex !in letter.referenceStrokes.indices) return false
        return validateStroke(userPoints.toList(), letter.referenceStrokes[strokeIndex], canvasSize)
    }

    fun getReferenceStrokes(letter: Letter): Array<ReferenceStroke> =
        letter.referenceStrokes

    fun getAllLetters(): Array<Letter> =
        Letters.getAll().toTypedArray()

    fun sampleReferenceStroke(stroke: ReferenceStroke, numSamples: Int = 40): Array<DrawingPoint> =
        sampleStrokePath(stroke, numSamples).toTypedArray()

}
