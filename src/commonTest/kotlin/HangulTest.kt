import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class HangulTest {

    @Test
    @JsName("vowels")
    fun `should have 10 vowels`() {
        assertEquals(10, Hangul.vowels.size)
    }

    @Test
    @JsName("consonants")
    fun `should have 14 consonants`() {
        assertEquals(14, Hangul.consonants.size)
    }

    @Test
    @JsName("random")
    fun `should generate random single letter symbols`() {
        repeat(50) {
            val symbol = Hangul.generateRandomSyllable().also { print(" $it ") }
            assertEquals(1, symbol.length)
        }
    }

}