@file:OptIn(ExperimentalJsExport::class)

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
enum class LetterCategory {
    CONSONANT,
    TENSE_CONSONANT,
    VOWEL,
    COMPLEX_VOWEL
}

@JsExport
class Letter(
    val character: String,
    val name: String,
    val romanization: Array<String>,
    val category: LetterCategory,
    val referenceStrokes: Array<Array<experimental.recognition.ReferenceStroke>> = emptyArray(),
) {

    override fun toString(): String = character

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Letter) return false
        return character == other.character
    }

    override fun hashCode(): Int = character.hashCode()

}
