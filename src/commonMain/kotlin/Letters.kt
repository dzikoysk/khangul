@file:OptIn(ExperimentalJsExport::class)

import LetterCategory.COMPLEX_VOWEL
import LetterCategory.CONSONANT
import LetterCategory.TENSE_CONSONANT
import LetterCategory.VOWEL
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Suppress("unused", "NonAsciiCharacters", "ObjectPropertyName")
object Letters {

    // --- Consonants ---

    val ㄱ = Letter("ㄱ", "giyeok", arrayOf("g", "k"), CONSONANT)
    val ㄴ = Letter("ㄴ", "nieun", arrayOf("n"), CONSONANT)
    val ㄷ = Letter("ㄷ", "digeut", arrayOf("d", "t"), CONSONANT)
    val ㄹ = Letter("ㄹ", "rieul", arrayOf("r", "l"), CONSONANT)
    val ㅁ = Letter("ㅁ", "mieum", arrayOf("m"), CONSONANT)
    val ㅂ = Letter("ㅂ", "bieup", arrayOf("b", "p"), CONSONANT)
    val ㅅ = Letter("ㅅ", "siot", arrayOf("s"), CONSONANT)
    val ㅇ = Letter("ㅇ", "ieung", arrayOf("ng"), CONSONANT)
    val ㅈ = Letter("ㅈ", "jieut", arrayOf("j"), CONSONANT)
    val ㅊ = Letter("ㅊ", "chieut", arrayOf("ch"), CONSONANT)
    val ㅋ = Letter("ㅋ", "kieuk", arrayOf("k"), CONSONANT)
    val ㅌ = Letter("ㅌ", "tieut", arrayOf("t"), CONSONANT)
    val ㅍ = Letter("ㅍ", "pieup", arrayOf("p"), CONSONANT)
    val ㅎ = Letter("ㅎ", "hieut", arrayOf("h"), CONSONANT)

    val consonants = listOf(ㄱ, ㄴ, ㄷ, ㄹ, ㅁ, ㅂ, ㅅ, ㅇ, ㅈ, ㅊ, ㅋ, ㅌ, ㅍ, ㅎ)

    // --- Tense consonants ---

    val ㄲ = Letter("ㄲ", "ssang-giyeok", arrayOf("kk"), TENSE_CONSONANT)
    val ㄸ = Letter("ㄸ", "ssang-digeut", arrayOf("tt"), TENSE_CONSONANT)
    val ㅃ = Letter("ㅃ", "ssang-bieup", arrayOf("pp"), TENSE_CONSONANT)
    val ㅆ = Letter("ㅆ", "ssang-siot", arrayOf("ss"), TENSE_CONSONANT)
    val ㅉ = Letter("ㅉ", "ssang-jieut", arrayOf("jj"), TENSE_CONSONANT)

    val tenseConsonants = listOf(ㄲ, ㄸ, ㅃ, ㅆ, ㅉ)

    // --- Vowels ---

    val ㅏ = Letter("ㅏ", "a", arrayOf("a"), VOWEL)
    val ㅑ = Letter("ㅑ", "ya", arrayOf("ya"), VOWEL)
    val ㅓ = Letter("ㅓ", "eo", arrayOf("eo"), VOWEL)
    val ㅕ = Letter("ㅕ", "yeo", arrayOf("yeo"), VOWEL)
    val ㅗ = Letter("ㅗ", "o", arrayOf("o"), VOWEL)
    val ㅛ = Letter("ㅛ", "yo", arrayOf("yo"), VOWEL)
    val ㅜ = Letter("ㅜ", "u", arrayOf("u"), VOWEL)
    val ㅠ = Letter("ㅠ", "yu", arrayOf("yu"), VOWEL)
    val ㅡ = Letter("ㅡ", "eu", arrayOf("eu"), VOWEL)
    val ㅣ = Letter("ㅣ", "i", arrayOf("i"), VOWEL)

    val vowels = listOf(ㅏ, ㅑ, ㅓ, ㅕ, ㅗ, ㅛ, ㅜ, ㅠ, ㅡ, ㅣ)

    // --- Complex vowels ---

    val ㅐ = Letter("ㅐ", "ae", arrayOf("ae"), COMPLEX_VOWEL)
    val ㅔ = Letter("ㅔ", "e", arrayOf("e"), COMPLEX_VOWEL)
    val ㅒ = Letter("ㅒ", "yae", arrayOf("yae"), COMPLEX_VOWEL)
    val ㅖ = Letter("ㅖ", "ye", arrayOf("ye"), COMPLEX_VOWEL)
    val ㅘ = Letter("ㅘ", "wa", arrayOf("wa"), COMPLEX_VOWEL)
    val ㅝ = Letter("ㅝ", "wo", arrayOf("wo"), COMPLEX_VOWEL)
    val ㅙ = Letter("ㅙ", "wae", arrayOf("wae"), COMPLEX_VOWEL)
    val ㅞ = Letter("ㅞ", "we", arrayOf("we"), COMPLEX_VOWEL)
    val ㅚ = Letter("ㅚ", "oe", arrayOf("oe"), COMPLEX_VOWEL)
    val ㅟ = Letter("ㅟ", "wi", arrayOf("wi"), COMPLEX_VOWEL)
    val ㅢ = Letter("ㅢ", "ui", arrayOf("ui"), COMPLEX_VOWEL)

    val complexVowels = listOf(ㅐ, ㅔ, ㅒ, ㅖ, ㅘ, ㅝ, ㅙ, ㅞ, ㅚ, ㅟ, ㅢ)

    fun getBasicLetters(): List<Letter> = consonants + vowels

    fun getAll(): List<Letter> = consonants + tenseConsonants + vowels + complexVowels

    fun findByCharacter(character: String): Letter? = getAll().find { it.character == character }

}
