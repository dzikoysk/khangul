import RemovePolicy.REFORMAT_ON_DELETE
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HangulContextTest {

    private val context = HangulContext()

    @Test
    @JsName("clear")
    fun `should clear content`() {
        context.appendLetter("a")
        assertEquals("a",context.getValue())

        context.clear()
        assertEquals("", context.getValue())
    }

    @Test
    @JsName("convertToSyllables")
    fun `should convert composable letters into hangul syllable`() {
        assertEquals("안", context.composeHangul("아ㄴ"))
        assertEquals("아", context.composeHangul("ㅇㅏ"))
    }

    @Test
    @JsName("letterByLetterConversion")
    fun `should progressively convert composable letters into hangul syllable`() {
        context.appendLetter("ㅇ")
        assertEquals("ㅇ", context.getValue())

        context.appendLetter("ㅏ")
        assertEquals("아", context.getValue())

        context.appendLetter("ㄴ")
        assertEquals("안", context.getValue())

        context.appendLetter("ㄴ")
        assertEquals("안ㄴ", context.getValue())

        context.appendLetter("ㅕ")
        assertEquals("안녀", context.getValue())

        context.appendLetter("ㅇ")
        assertEquals("안녕", context.getValue())

        context.appendLetter("ㅎ")
        assertEquals("안녕ㅎ", context.getValue())

        context.appendLetter("ㅏ")
        assertEquals("안녕하", context.getValue())

        context.appendLetter("ㅅ")
        assertEquals("안녕핫", context.getValue())

        context.appendLetter("ㅔ")
        assertEquals("안녕하세", context.getValue())

        context.appendLetter("ㅇ")
        assertEquals("안녕하셍", context.getValue())

        context.appendLetter("ㅛ")
        assertEquals("안녕하세요", context.getValue())
    }

    @Test
    @JsName("removeLastLetter")
    fun `should progressively drop last letters from syllables without reformat`() {
        val context = HangulContext("안녕하세요")

        context.removeLastLetter()
        assertEquals("안녕하세ㅇ", context.getValue())

        context.removeLastLetter()
        assertEquals("안녕하세", context.getValue())

        context.removeLastLetter()
        assertEquals("안녕하ㅅ", context.getValue())

        context.removeLastLetter()
        assertEquals("안녕하", context.getValue())

        context.removeLastLetter()
        assertEquals("안녕ㅎ", context.getValue())

        context.removeLastLetter()
        assertEquals("안녕", context.getValue())

        context.removeLastLetter()
        assertEquals("안녀", context.getValue())

        context.removeLastLetter()
        assertEquals("안ㄴ", context.getValue())

        context.removeLastLetter()
        assertEquals("안", context.getValue())

        context.removeLastLetter()
        assertEquals("아", context.getValue())

        context.removeLastLetter()
        assertEquals("ㅇ", context.getValue())

        context.removeLastLetter()
        assertEquals("", context.getValue())
    }

    @Test
    @JsName("removeLastLetterWithReformat")
    fun `should progressively drop last letters from syllables with reformat`() {
        val context = HangulContext(
            initialPhrase = "안녕하세요",
            removePolicy = REFORMAT_ON_DELETE
        )

        context.removeLastLetter()
        assertEquals("안녕하셍", context.getValue())
    }

} 