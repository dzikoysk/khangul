import kotlin.math.min

enum class RemovePolicy {
    DEFAULT,
    REFORMAT_ON_DELETE
}

class HangulContext(
    initialPhrase: String = "",
    private val removePolicy: RemovePolicy = RemovePolicy.DEFAULT
) {

    private var content = initialPhrase
    private var caretPosition = initialPhrase.length

    fun appendLetter(letter: Char) {
        insertAtCaret(letter.toString())
        val deleted = deleteAtCaret(2)
        insertAtCaret(composeHangul(deleted))
    }

    fun removeLastLetter() {
        var deleted = deleteAtCaret(1)

        if (deleted.isNotEmpty() && deleted[0].code in 56320..57343) {
            deleted = deleteAtCaret(1) + deleted
        }

        val decomposed = decomposeHangul(deleted)

        if (decomposed.length > 1) {
            when (removePolicy) {
                RemovePolicy.DEFAULT -> insertAtCaret(composeHangul(decomposed.dropLast(1)))
                RemovePolicy.REFORMAT_ON_DELETE -> decomposed.dropLast(1).forEach { appendLetter(it) }
            }

        }
    }

    private fun insertAtCaret(value: String) {
        val selectionStart = getSelectionStart()
        val selectionEnd = getSelectionEnd()
        this.content = content.substring(0, selectionStart) + value + content.substring(selectionEnd)
        this.caretPosition = content.length
    }

    private fun deleteAtCaret(count: Int, to: Int = 0): String {
        val selectionStart = getSelectionStart()
        val selectionEnd = getSelectionEnd()
        val contentLength = content.length

        val deleteFrom = min(count, selectionStart)
        val deleteTo = when {
            selectionEnd + to > contentLength -> contentLength - selectionEnd
            else -> to
        }

        val deleted = content.substring(selectionStart - deleteFrom, selectionEnd + deleteTo)
        this.content = content.substring(0, selectionStart - deleteFrom) + content.substring(selectionEnd + deleteTo)
        setCaretPosition(selectionStart - deleteFrom /*, 0 */)
        return deleted
    }

    private fun setCaretPosition(position: Int) {
        this.caretPosition = position
    }

    private fun getSelectionEnd(): Int =
        caretPosition

    private fun getSelectionStart(): Int =
        caretPosition

    fun getValue(): String =
        content

}