@file:OptIn(ExperimentalJsExport::class)

package experimental.recognition

import Letter
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

    /** Match threshold for free draw mode (coverage percentage). */
    var matchThreshold: Int = 40

    /**
     * Recognize which letter the user drew from free-draw paths.
     *
     * @param paths List of strokes, where each stroke is a list of points (in any coordinate space)
     * @return All candidates sorted by score descending, or empty if unrecognizable
     */
    @JsName("recognizeFromArrays")
    fun recognize(paths: Array<Array<DrawingPoint>>): Array<RecognitionResult> =
        recognize(paths.map { it.toList() }).toTypedArray()

    /** Kotlin-friendly overload accepting Lists. */
    fun recognize(paths: List<List<DrawingPoint>>): List<RecognitionResult> =
        recognizeShape(paths, flexible = true)

    /**
     * Check if the top recognition result matches the expected letter above the threshold.
     */
    @JsName("isMatchFromArrays")
    fun isMatch(paths: Array<Array<DrawingPoint>>, expected: Letter): Boolean =
        isMatch(paths.map { it.toList() }, expected)

    /** Kotlin-friendly overload accepting Lists. */
    fun isMatch(paths: List<List<DrawingPoint>>, expected: Letter): Boolean {
        val results = recognize(paths)
        val top = results.firstOrNull() ?: return false
        return top.letter == expected && top.coverage >= matchThreshold
    }

    /**
     * Validate a single stroke in guided mode.
     *
     * @param userPoints Points drawn by the user (canvas pixel coordinates)
     * @param letter The letter being practiced
     * @param strokeIndex Which stroke (0-based) to validate
     * @param canvasSize Canvas dimension in pixels
     * @return true if the stroke is valid
     */
    @JsName("validateGuidedStrokeFromArray")
    fun validateGuidedStroke(
        userPoints: Array<DrawingPoint>,
        letter: Letter,
        strokeIndex: Int,
        canvasSize: Double,
    ): Boolean = validateGuidedStroke(userPoints.toList(), letter, strokeIndex, canvasSize)

    /** Kotlin-friendly overload accepting List. */
    fun validateGuidedStroke(
        userPoints: List<DrawingPoint>,
        letter: Letter,
        strokeIndex: Int,
        canvasSize: Double,
    ): Boolean {
        val strokes = ReferenceData.strokes[letter] ?: return false
        if (strokeIndex !in strokes.indices) return false
        return validateStroke(userPoints, strokes[strokeIndex], canvasSize)
    }

    /**
     * Get reference stroke definitions for a letter.
     */
    fun getReferenceStrokes(letter: Letter): Array<ReferenceStroke>? =
        ReferenceData.strokes[letter]?.toTypedArray()

    /**
     * Get all letters that have stroke definitions.
     */
    fun getAllLetters(): Array<Letter> =
        ReferenceData.allLetters.toTypedArray()

    /**
     * Sample points along a reference stroke (for rendering guides).
     */
    fun sampleReferenceStroke(stroke: ReferenceStroke, numSamples: Int = 40): Array<DrawingPoint> =
        sampleStrokePath(stroke, numSamples).toTypedArray()

}
