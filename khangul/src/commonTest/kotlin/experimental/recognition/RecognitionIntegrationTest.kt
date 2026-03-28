package experimental.recognition

import Letter
import Letters
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Integration tests: synthetic perfect drawings → recognition.
 * Every letter drawn perfectly from reference data should recognize as itself.
 */
class RecognitionIntegrationTest {

    private val recognizer = HangulRecognizer()

    // --- Consonants ---

    @Test @JsName("recognize_giyeok")
    fun `ㄱ perfect drawing recognized as giyeok`() = assertRecognizes(Letters.ㄱ)

    @Test @JsName("recognize_nieun")
    fun `ㄴ perfect drawing recognized as nieun`() = assertRecognizes(Letters.ㄴ)

    @Test @JsName("recognize_digeut")
    fun `ㄷ perfect drawing recognized as digeut`() = assertRecognizes(Letters.ㄷ)

    @Test @JsName("recognize_rieul")
    fun `ㄹ perfect drawing recognized as rieul`() = assertRecognizes(Letters.ㄹ)

    @Test @JsName("recognize_mieum")
    fun `ㅁ perfect drawing recognized as mieum`() = assertRecognizes(Letters.ㅁ)

    @Test @JsName("recognize_bieup")
    fun `ㅂ perfect drawing recognized as bieup`() = assertRecognizes(Letters.ㅂ)

    @Test @JsName("recognize_siot")
    fun `ㅅ perfect drawing recognized as siot`() = assertRecognizes(Letters.ㅅ)

    @Test @JsName("recognize_ieung")
    fun `ㅇ perfect drawing recognized as ieung`() = assertRecognizes(Letters.ㅇ)

    @Test @JsName("recognize_jieut")
    fun `ㅈ perfect drawing recognized as jieut`() = assertRecognizes(Letters.ㅈ)

    @Test @JsName("recognize_chieut")
    fun `ㅊ perfect drawing recognized as chieut`() = assertRecognizes(Letters.ㅊ)

    @Test @JsName("recognize_kieuk")
    fun `ㅋ perfect drawing recognized as kieuk`() = assertRecognizes(Letters.ㅋ)

    @Test @JsName("recognize_tieut")
    fun `ㅌ perfect drawing recognized as tieut`() = assertRecognizes(Letters.ㅌ)

    @Test @JsName("recognize_pieup")
    fun `ㅍ perfect drawing recognized as pieup`() = assertRecognizes(Letters.ㅍ)

    @Test @JsName("recognize_hieut")
    fun `ㅎ perfect drawing recognized as hieut`() = assertRecognizes(Letters.ㅎ)

    // --- Tense consonants ---

    @Test @JsName("recognize_kk")
    fun `ㄲ perfect drawing recognized as ssang-giyeok`() = assertRecognizes(Letters.ㄲ)

    @Test @JsName("recognize_tt")
    fun `ㄸ perfect drawing recognized as ssang-digeut`() = assertRecognizes(Letters.ㄸ)

    @Test @JsName("recognize_pp")
    fun `ㅃ perfect drawing recognized as ssang-bieup`() = assertRecognizes(Letters.ㅃ)

    @Test @JsName("recognize_ss")
    fun `ㅆ perfect drawing recognized as ssang-siot`() = assertRecognizes(Letters.ㅆ)

    @Test @JsName("recognize_jj")
    fun `ㅉ perfect drawing recognized as ssang-jieut`() = assertRecognizes(Letters.ㅉ)

    // --- Vowels ---

    @Test @JsName("recognize_a")
    fun `ㅏ perfect drawing recognized as a`() = assertRecognizes(Letters.ㅏ)

    @Test @JsName("recognize_ya")
    fun `ㅑ perfect drawing recognized as ya`() = assertRecognizes(Letters.ㅑ)

    @Test @JsName("recognize_eo")
    fun `ㅓ perfect drawing recognized as eo`() = assertRecognizes(Letters.ㅓ)

    @Test @JsName("recognize_yeo")
    fun `ㅕ perfect drawing recognized as yeo`() = assertRecognizes(Letters.ㅕ)

    @Test @JsName("recognize_o")
    fun `ㅗ perfect drawing recognized as o`() = assertRecognizes(Letters.ㅗ)

    @Test @JsName("recognize_yo")
    fun `ㅛ perfect drawing recognized as yo`() = assertRecognizes(Letters.ㅛ)

    @Test @JsName("recognize_u")
    fun `ㅜ perfect drawing recognized as u`() = assertRecognizes(Letters.ㅜ)

    @Test @JsName("recognize_yu")
    fun `ㅠ perfect drawing recognized as yu`() = assertRecognizes(Letters.ㅠ)

    @Test @JsName("recognize_eu")
    fun `ㅡ perfect drawing recognized as eu`() = assertRecognizes(Letters.ㅡ)

    @Test @JsName("recognize_i")
    fun `ㅣ perfect drawing recognized as i`() = assertRecognizes(Letters.ㅣ)

    // --- Complex vowels ---

    @Test @JsName("recognize_ae")
    fun `ㅐ perfect drawing recognized as ae`() = assertRecognizes(Letters.ㅐ)

    @Test @JsName("recognize_e")
    fun `ㅔ perfect drawing recognized as e`() = assertRecognizes(Letters.ㅔ)

    @Test @JsName("recognize_yae")
    fun `ㅒ perfect drawing recognized as yae`() = assertRecognizes(Letters.ㅒ)

    @Test @JsName("recognize_ye")
    fun `ㅖ perfect drawing recognized as ye`() = assertRecognizes(Letters.ㅖ)

    @Test @JsName("recognize_wa")
    fun `ㅘ perfect drawing recognized as wa`() = assertRecognizes(Letters.ㅘ)

    @Test @JsName("recognize_wo")
    fun `ㅝ perfect drawing recognized as wo`() = assertRecognizes(Letters.ㅝ)

    @Test @JsName("recognize_wae")
    fun `ㅙ perfect drawing recognized as wae`() = assertRecognizes(Letters.ㅙ)

    @Test @JsName("recognize_we")
    fun `ㅞ perfect drawing recognized as we`() = assertRecognizes(Letters.ㅞ)

    @Test @JsName("recognize_oe")
    fun `ㅚ perfect drawing recognized as oe`() = assertRecognizes(Letters.ㅚ)

    @Test @JsName("recognize_wi")
    fun `ㅟ perfect drawing recognized as wi`() = assertRecognizes(Letters.ㅟ)

    @Test @JsName("recognize_ui")
    fun `ㅢ perfect drawing recognized as ui`() = assertRecognizes(Letters.ㅢ)

    // --- Variant tests ---

    @Test @JsName("recognize_ui_connected")
    fun `ㅢ connected drawing recognized as ui`() =
        assertRecognizesDrawing(Letters.ㅢ, "connected", SyntheticDrawings.uiConnected())

    // --- Confusion tests: similar letters should NOT be confused ---

    @Test @JsName("giyeok_not_nieun")
    fun `ㄱ should not be confused with ㄴ`() = assertNotConfused(Letters.ㄱ, Letters.ㄴ)

    @Test @JsName("a_not_eo")
    fun `ㅏ should not be confused with ㅓ`() = assertNotConfused(Letters.ㅏ, Letters.ㅓ)

    @Test @JsName("o_not_u")
    fun `ㅗ should not be confused with ㅜ`() = assertNotConfused(Letters.ㅗ, Letters.ㅜ)

    @Test @JsName("ya_not_yeo")
    fun `ㅑ should not be confused with ㅕ`() = assertNotConfused(Letters.ㅑ, Letters.ㅕ)

    // --- Helper methods ---

    private fun assertRecognizes(letter: Letter) {
        assertRecognizesDrawing(letter, "wobbly", SyntheticDrawings.wobblyDrawing(letter))
        assertRecognizesDrawing(letter, "clean", SyntheticDrawings.cleanDrawing(letter))
    }

    private fun assertRecognizesDrawing(letter: Letter, variant: String, paths: List<List<DrawingPoint>>) {
        assertTrue(paths.isNotEmpty(), "No stroke data for ${letter.character}")
        val results = recognizer.recognize(paths)
        assertTrue(results.isNotEmpty(), "No recognition results for ${letter.character} ($variant)")
        assertEquals(
            letter, results.first().letter,
            "$variant: Expected ${letter.character} but got ${results.first().letter.character} " +
                "(coverage: ${results.first().coverage}%). " +
                "Top 3: ${results.take(3).map { "${it.letter.character}:${it.coverage}%" }}"
        )
        assertTrue(
            results.first().coverage >= recognizer.matchThreshold,
            "$variant: ${letter.character} coverage ${results.first().coverage}% is below threshold ${recognizer.matchThreshold}%"
        )
    }

    private fun assertNotConfused(drawLetter: Letter, confusedWith: Letter) {
        for ((variant, paths) in listOf("wobbly" to SyntheticDrawings.wobblyDrawing(drawLetter), "clean" to SyntheticDrawings.cleanDrawing(drawLetter))) {
            val results = recognizer.recognize(paths)
            val top = results.firstOrNull()
            assertTrue(
                top == null || top.letter != confusedWith || top.coverage < recognizer.matchThreshold,
                "$variant: Drawing ${drawLetter.character} was confused with ${confusedWith.character} " +
                    "(coverage: ${top?.coverage}%)"
            )
        }
    }

}
