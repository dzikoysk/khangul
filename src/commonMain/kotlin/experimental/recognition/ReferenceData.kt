package experimental.recognition

import Letter
import Letters
import experimental.recognition.StrokeType.*

/**
 * Reference stroke definitions for all Hangul letters.
 * Coordinates are in 0-100 space.
 */
object ReferenceData {

    val strokes: Map<Letter, List<ReferenceStroke>> = mapOf(

        // ===== Consonants =====

        Letters.ㄱ to listOf(
            ReferenceStroke(20.0, 25.0, 80.0, 25.0, LINE),
            ReferenceStroke(80.0, 25.0, 80.0, 80.0, LINE),
        ),
        Letters.ㄴ to listOf(
            ReferenceStroke(20.0, 20.0, 20.0, 80.0, LINE),
            ReferenceStroke(20.0, 80.0, 80.0, 80.0, LINE),
        ),
        Letters.ㄷ to listOf(
            ReferenceStroke(20.0, 20.0, 80.0, 20.0, LINE),
            ReferenceStroke(20.0, 20.0, 20.0, 80.0, LINE),
            ReferenceStroke(20.0, 80.0, 80.0, 80.0, LINE),
        ),
        Letters.ㄹ to listOf(
            ReferenceStroke(15.0, 25.0, 85.0, 25.0, LINE),
            ReferenceStroke(85.0, 25.0, 85.0, 48.0, LINE),
            ReferenceStroke(85.0, 48.0, 15.0, 48.0, LINE),
            ReferenceStroke(15.0, 48.0, 15.0, 72.0, LINE),
            ReferenceStroke(15.0, 72.0, 85.0, 72.0, LINE),
        ),
        Letters.ㅁ to listOf(
            ReferenceStroke(20.0, 28.0, 20.0, 72.0, LINE),
            ReferenceStroke(20.0, 72.0, 80.0, 72.0, LINE),
            ReferenceStroke(80.0, 72.0, 80.0, 28.0, LINE),
            ReferenceStroke(80.0, 28.0, 20.0, 28.0, LINE),
        ),
        Letters.ㅂ to listOf(
            ReferenceStroke(25.0, 25.0, 25.0, 75.0, LINE),
            ReferenceStroke(75.0, 25.0, 75.0, 75.0, LINE),
            ReferenceStroke(25.0, 50.0, 75.0, 50.0, LINE),
            ReferenceStroke(25.0, 75.0, 75.0, 75.0, LINE),
        ),
        Letters.ㅅ to listOf(
            ReferenceStroke(50.0, 20.0, 15.0, 80.0, CURVE, controlX = 45.0, controlY = 55.0),
            ReferenceStroke(50.0, 20.0, 85.0, 80.0, CURVE, controlX = 55.0, controlY = 55.0),
        ),
        Letters.ㅇ to listOf(
            ReferenceStroke(50.0, 20.0, 50.0, 80.0, CIRCLE),
        ),
        Letters.ㅈ to listOf(
            ReferenceStroke(20.0, 20.0, 80.0, 20.0, LINE),
            ReferenceStroke(50.0, 20.0, 18.0, 80.0, CURVE, controlX = 45.0, controlY = 52.0),
            ReferenceStroke(50.0, 20.0, 82.0, 80.0, CURVE, controlX = 55.0, controlY = 52.0),
        ),
        Letters.ㅊ to listOf(
            ReferenceStroke(50.0, 12.0, 50.0, 30.0, LINE),
            ReferenceStroke(20.0, 30.0, 80.0, 30.0, LINE),
            ReferenceStroke(50.0, 30.0, 18.0, 85.0, CURVE, controlX = 45.0, controlY = 58.0),
            ReferenceStroke(50.0, 30.0, 82.0, 85.0, CURVE, controlX = 55.0, controlY = 58.0),
        ),
        Letters.ㅋ to listOf(
            ReferenceStroke(25.0, 25.0, 75.0, 25.0, LINE),
            ReferenceStroke(75.0, 25.0, 75.0, 75.0, LINE),
            ReferenceStroke(25.0, 50.0, 75.0, 50.0, LINE),
        ),
        Letters.ㅌ to listOf(
            ReferenceStroke(20.0, 25.0, 80.0, 25.0, LINE),
            ReferenceStroke(20.0, 25.0, 20.0, 75.0, LINE),
            ReferenceStroke(20.0, 50.0, 80.0, 50.0, LINE),
            ReferenceStroke(20.0, 75.0, 80.0, 75.0, LINE),
        ),
        Letters.ㅍ to listOf(
            ReferenceStroke(25.0, 28.0, 75.0, 28.0, LINE),
            ReferenceStroke(35.0, 28.0, 35.0, 72.0, LINE),
            ReferenceStroke(65.0, 28.0, 65.0, 72.0, LINE),
            ReferenceStroke(25.0, 72.0, 75.0, 72.0, LINE),
        ),
        Letters.ㅎ to listOf(
            ReferenceStroke(50.0, 15.0, 50.0, 30.0, LINE),
            ReferenceStroke(25.0, 30.0, 75.0, 30.0, LINE),
            ReferenceStroke(50.0, 38.0, 50.0, 78.0, CIRCLE, controlX = 25.0),
        ),

        // ===== Tense consonants =====

        Letters.ㄲ to listOf(
            ReferenceStroke(10.0, 25.0, 45.0, 25.0, LINE),
            ReferenceStroke(45.0, 25.0, 45.0, 80.0, LINE),
            ReferenceStroke(55.0, 25.0, 90.0, 25.0, LINE),
            ReferenceStroke(90.0, 25.0, 90.0, 80.0, LINE),
        ),
        Letters.ㄸ to listOf(
            ReferenceStroke(8.0, 20.0, 45.0, 20.0, LINE),
            ReferenceStroke(8.0, 20.0, 8.0, 80.0, LINE),
            ReferenceStroke(8.0, 80.0, 45.0, 80.0, LINE),
            ReferenceStroke(55.0, 20.0, 92.0, 20.0, LINE),
            ReferenceStroke(55.0, 20.0, 55.0, 80.0, LINE),
            ReferenceStroke(55.0, 80.0, 92.0, 80.0, LINE),
        ),
        Letters.ㅃ to listOf(
            ReferenceStroke(10.0, 20.0, 10.0, 80.0, LINE),
            ReferenceStroke(42.0, 20.0, 42.0, 80.0, LINE),
            ReferenceStroke(10.0, 50.0, 42.0, 50.0, LINE),
            ReferenceStroke(10.0, 80.0, 42.0, 80.0, LINE),
            ReferenceStroke(58.0, 20.0, 58.0, 80.0, LINE),
            ReferenceStroke(90.0, 20.0, 90.0, 80.0, LINE),
            ReferenceStroke(58.0, 50.0, 90.0, 50.0, LINE),
            ReferenceStroke(58.0, 80.0, 90.0, 80.0, LINE),
        ),
        Letters.ㅆ to listOf(
            ReferenceStroke(30.0, 20.0, 8.0, 80.0, CURVE, controlX = 26.0, controlY = 55.0),
            ReferenceStroke(30.0, 20.0, 50.0, 80.0, CURVE, controlX = 34.0, controlY = 55.0),
            ReferenceStroke(70.0, 20.0, 50.0, 80.0, CURVE, controlX = 66.0, controlY = 55.0),
            ReferenceStroke(70.0, 20.0, 92.0, 80.0, CURVE, controlX = 74.0, controlY = 55.0),
        ),
        Letters.ㅉ to listOf(
            ReferenceStroke(8.0, 20.0, 45.0, 20.0, LINE),
            ReferenceStroke(27.0, 20.0, 10.0, 80.0, CURVE, controlX = 24.0, controlY = 52.0),
            ReferenceStroke(27.0, 20.0, 43.0, 80.0, CURVE, controlX = 30.0, controlY = 52.0),
            ReferenceStroke(55.0, 20.0, 92.0, 20.0, LINE),
            ReferenceStroke(74.0, 20.0, 57.0, 80.0, CURVE, controlX = 71.0, controlY = 52.0),
            ReferenceStroke(74.0, 20.0, 90.0, 80.0, CURVE, controlX = 77.0, controlY = 52.0),
        ),

        // ===== Vowels =====

        Letters.ㅏ to listOf(
            ReferenceStroke(35.0, 10.0, 35.0, 90.0, LINE),
            ReferenceStroke(35.0, 45.0, 55.0, 45.0, LINE),
        ),
        Letters.ㅑ to listOf(
            ReferenceStroke(30.0, 10.0, 30.0, 90.0, LINE),
            ReferenceStroke(30.0, 35.0, 50.0, 35.0, LINE),
            ReferenceStroke(30.0, 60.0, 50.0, 60.0, LINE),
        ),
        Letters.ㅓ to listOf(
            ReferenceStroke(65.0, 10.0, 65.0, 90.0, LINE),
            ReferenceStroke(45.0, 45.0, 65.0, 45.0, LINE),
        ),
        Letters.ㅕ to listOf(
            ReferenceStroke(70.0, 10.0, 70.0, 90.0, LINE),
            ReferenceStroke(50.0, 35.0, 70.0, 35.0, LINE),
            ReferenceStroke(50.0, 60.0, 70.0, 60.0, LINE),
        ),
        Letters.ㅗ to listOf(
            ReferenceStroke(50.0, 37.0, 50.0, 55.0, LINE),
            ReferenceStroke(10.0, 55.0, 90.0, 55.0, LINE),
        ),
        Letters.ㅛ to listOf(
            ReferenceStroke(35.0, 37.0, 35.0, 55.0, LINE),
            ReferenceStroke(65.0, 37.0, 65.0, 55.0, LINE),
            ReferenceStroke(10.0, 55.0, 90.0, 55.0, LINE),
        ),
        Letters.ㅜ to listOf(
            ReferenceStroke(10.0, 45.0, 90.0, 45.0, LINE),
            ReferenceStroke(50.0, 45.0, 50.0, 63.0, LINE),
        ),
        Letters.ㅠ to listOf(
            ReferenceStroke(15.0, 45.0, 85.0, 45.0, LINE),
            ReferenceStroke(35.0, 45.0, 35.0, 66.0, LINE),
            ReferenceStroke(65.0, 45.0, 65.0, 66.0, LINE),
        ),
        Letters.ㅡ to listOf(
            ReferenceStroke(10.0, 50.0, 90.0, 50.0, LINE),
        ),
        Letters.ㅣ to listOf(
            ReferenceStroke(50.0, 10.0, 50.0, 90.0, LINE),
        ),

        // ===== Complex vowels =====

        Letters.ㅐ to listOf(
            ReferenceStroke(25.0, 10.0, 25.0, 90.0, LINE),
            ReferenceStroke(25.0, 45.0, 60.0, 45.0, LINE),
            ReferenceStroke(60.0, 10.0, 60.0, 90.0, LINE),
        ),
        Letters.ㅔ to listOf(
            ReferenceStroke(40.0, 10.0, 40.0, 90.0, LINE),
            ReferenceStroke(25.0, 45.0, 40.0, 45.0, LINE),
            ReferenceStroke(60.0, 10.0, 60.0, 90.0, LINE),
        ),
        Letters.ㅒ to listOf(
            ReferenceStroke(22.0, 10.0, 22.0, 90.0, LINE),
            ReferenceStroke(22.0, 35.0, 58.0, 35.0, LINE),
            ReferenceStroke(22.0, 60.0, 58.0, 60.0, LINE),
            ReferenceStroke(58.0, 10.0, 58.0, 90.0, LINE),
        ),
        Letters.ㅖ to listOf(
            ReferenceStroke(42.0, 10.0, 42.0, 90.0, LINE),
            ReferenceStroke(22.0, 35.0, 42.0, 35.0, LINE),
            ReferenceStroke(22.0, 60.0, 42.0, 60.0, LINE),
            ReferenceStroke(62.0, 10.0, 62.0, 90.0, LINE),
        ),
        Letters.ㅘ to listOf(
            ReferenceStroke(36.0, 51.0, 36.0, 64.0, LINE),
            ReferenceStroke(16.0, 64.0, 56.0, 64.0, LINE),
            ReferenceStroke(65.0, 10.0, 65.0, 90.0, LINE),
            ReferenceStroke(65.0, 50.0, 85.0, 50.0, LINE),
        ),
        Letters.ㅝ to listOf(
            ReferenceStroke(16.0, 50.0, 56.0, 50.0, LINE),
            ReferenceStroke(36.0, 50.0, 36.0, 64.0, LINE),
            ReferenceStroke(65.0, 10.0, 65.0, 90.0, LINE),
            ReferenceStroke(50.0, 70.0, 65.0, 70.0, LINE),
        ),
        Letters.ㅙ to listOf(
            ReferenceStroke(26.0, 51.0, 26.0, 64.0, LINE),
            ReferenceStroke(8.0, 64.0, 44.0, 64.0, LINE),
            ReferenceStroke(58.0, 10.0, 58.0, 90.0, LINE),
            ReferenceStroke(58.0, 50.0, 85.0, 50.0, LINE),
            ReferenceStroke(85.0, 10.0, 85.0, 90.0, LINE),
        ),
        Letters.ㅞ to listOf(
            ReferenceStroke(12.0, 50.0, 50.0, 50.0, LINE),
            ReferenceStroke(31.0, 50.0, 31.0, 64.0, LINE),
            ReferenceStroke(45.0, 70.0, 60.0, 70.0, LINE),
            ReferenceStroke(60.0, 10.0, 60.0, 90.0, LINE),
            ReferenceStroke(78.0, 10.0, 78.0, 90.0, LINE),
        ),
        Letters.ㅚ to listOf(
            ReferenceStroke(32.0, 51.0, 32.0, 64.0, LINE),
            ReferenceStroke(12.0, 64.0, 52.0, 64.0, LINE),
            ReferenceStroke(68.0, 10.0, 68.0, 90.0, LINE),
        ),
        Letters.ㅟ to listOf(
            ReferenceStroke(16.0, 50.0, 56.0, 50.0, LINE),
            ReferenceStroke(36.0, 50.0, 36.0, 64.0, LINE),
            ReferenceStroke(68.0, 10.0, 68.0, 90.0, LINE),
        ),
        Letters.ㅢ to listOf(
            ReferenceStroke(10.0, 58.0, 62.0, 58.0, LINE),
            ReferenceStroke(66.0, 10.0, 66.0, 90.0, LINE),
        ),
    )

    /** All letters that have stroke definitions, in canonical order. */
    val allLetters: List<Letter> = strokes.keys.toList()

}
