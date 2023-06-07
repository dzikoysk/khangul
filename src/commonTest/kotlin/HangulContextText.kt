import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class TestClient {

    @Test
    @JsName("convertToHangul")
    fun `should convert composable letters into hangul syllable`() {
        assertEquals("안", composeHangul("아ㄴ"))
        assertEquals("아", composeHangul("ㅇㅏ"))
    }

    @Test
    @JsName("convertToHangul2")
    fun `should covert `() {
        val context = HangulContext()

        context.appendLetter('ㅇ')
        assertEquals("ㅇ", context.getValue())

        context.appendLetter('ㅏ')
        assertEquals("아", context.getValue())

        context.appendLetter('ㄴ')
        assertEquals("안", context.getValue())

        context.appendLetter('ㄴ')
        assertEquals("안ㄴ", context.getValue())

        context.appendLetter('ㅕ')
        assertEquals("안녀", context.getValue())

        context.appendLetter('ㅇ')
        assertEquals("안녕", context.getValue())

        context.appendLetter('ㅎ')
        assertEquals("안녕ㅎ", context.getValue())

        context.appendLetter('ㅏ')
        assertEquals("안녕하", context.getValue())

        context.appendLetter('ㅅ')
        assertEquals("안녕핫", context.getValue())

        context.appendLetter('ㅔ')
        assertEquals("안녕하세", context.getValue())

        context.appendLetter('ㅇ')
        assertEquals("안녕하셍", context.getValue())

        context.appendLetter('ㅛ')
        assertEquals("안녕하세요", context.getValue())
    }

} 