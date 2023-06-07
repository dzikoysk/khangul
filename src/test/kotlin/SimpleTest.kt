import kotlin.test.Test
import kotlin.test.assertEquals

class TestClient {

    @Test
    fun testGreet() {
        val result = composeHangul("아ㄴ")
        println(result)
        assertEquals("안", result)
    }

} 