@file:OptIn(ExperimentalJsExport::class)

import LetterCategory.COMPLEX_VOWEL
import LetterCategory.CONSONANT
import LetterCategory.TENSE_CONSONANT
import LetterCategory.VOWEL
import experimental.recognition.ReferenceStroke
import experimental.recognition.StrokeType.CIRCLE
import experimental.recognition.StrokeType.CURVE
import experimental.recognition.StrokeType.LINE
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Suppress("unused", "NonAsciiCharacters", "ObjectPropertyName")
object Letters {

    // --- Consonants ---

    val ㄱ = Letter("ㄱ", "giyeok", arrayOf("g", "k"), CONSONANT, arrayOf(
        arrayOf(
            ReferenceStroke(20.0, 25.0, 80.0, 25.0, LINE),
            ReferenceStroke(80.0, 25.0, 80.0, 80.0, LINE),
        ),
    ))
    val ㄴ = Letter("ㄴ", "nieun", arrayOf("n"), CONSONANT, arrayOf(
        arrayOf(
            ReferenceStroke(20.0, 20.0, 20.0, 80.0, LINE),
            ReferenceStroke(20.0, 80.0, 80.0, 80.0, LINE),
        ),
    ))
    val ㄷ = Letter("ㄷ", "digeut", arrayOf("d", "t"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(20.0, 20.0, 80.0, 20.0, LINE)),
        arrayOf(
            ReferenceStroke(20.0, 20.0, 20.0, 80.0, LINE),
            ReferenceStroke(20.0, 80.0, 80.0, 80.0, LINE),
        ),
    ))
    val ㄹ = Letter("ㄹ", "rieul", arrayOf("r", "l"), CONSONANT, arrayOf(
        arrayOf(
            ReferenceStroke(15.0, 25.0, 85.0, 25.0, LINE),
            ReferenceStroke(85.0, 25.0, 85.0, 48.0, LINE),
        ),
        arrayOf(ReferenceStroke(85.0, 48.0, 15.0, 48.0, LINE)),
        arrayOf(
            ReferenceStroke(15.0, 48.0, 15.0, 72.0, LINE),
            ReferenceStroke(15.0, 72.0, 85.0, 72.0, LINE),
        ),
    ))
    val ㅁ = Letter("ㅁ", "mieum", arrayOf("m"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(20.0, 28.0, 20.0, 72.0, LINE)),
        arrayOf(
            ReferenceStroke(20.0, 28.0, 80.0, 28.0, LINE),
            ReferenceStroke(80.0, 28.0, 80.0, 72.0, LINE),
        ),
        arrayOf(ReferenceStroke(20.0, 72.0, 80.0, 72.0, LINE)),
    ))
    val ㅂ = Letter("ㅂ", "bieup", arrayOf("b", "p"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(25.0, 25.0, 25.0, 75.0, LINE)),
        arrayOf(ReferenceStroke(75.0, 25.0, 75.0, 75.0, LINE)),
        arrayOf(ReferenceStroke(25.0, 50.0, 75.0, 50.0, LINE)),
        arrayOf(ReferenceStroke(25.0, 75.0, 75.0, 75.0, LINE)),
    ))
    val ㅅ = Letter("ㅅ", "siot", arrayOf("s"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(50.0, 20.0, 15.0, 80.0, CURVE, controlX = 45.0, controlY = 55.0)),
        arrayOf(ReferenceStroke(50.0, 20.0, 85.0, 80.0, CURVE, controlX = 55.0, controlY = 55.0)),
    ))
    val ㅇ = Letter("ㅇ", "ieung", arrayOf("ng"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(50.0, 20.0, 50.0, 80.0, CIRCLE)),
    ))
    val ㅈ = Letter("ㅈ", "jieut", arrayOf("j"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(20.0, 20.0, 80.0, 20.0, LINE)),
        arrayOf(ReferenceStroke(50.0, 20.0, 18.0, 80.0, CURVE, controlX = 45.0, controlY = 52.0)),
        arrayOf(ReferenceStroke(50.0, 20.0, 82.0, 80.0, CURVE, controlX = 55.0, controlY = 52.0)),
    ))
    val ㅊ = Letter("ㅊ", "chieut", arrayOf("ch"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(50.0, 12.0, 50.0, 30.0, LINE)),
        arrayOf(ReferenceStroke(20.0, 30.0, 80.0, 30.0, LINE)),
        arrayOf(ReferenceStroke(50.0, 30.0, 18.0, 85.0, CURVE, controlX = 45.0, controlY = 58.0)),
        arrayOf(ReferenceStroke(50.0, 30.0, 82.0, 85.0, CURVE, controlX = 55.0, controlY = 58.0)),
    ))
    val ㅋ = Letter("ㅋ", "kieuk", arrayOf("k"), CONSONANT, arrayOf(
        arrayOf(
            ReferenceStroke(25.0, 25.0, 75.0, 25.0, LINE),
            ReferenceStroke(75.0, 25.0, 75.0, 75.0, LINE),
        ),
        arrayOf(ReferenceStroke(25.0, 50.0, 75.0, 50.0, LINE)),
    ))
    val ㅌ = Letter("ㅌ", "tieut", arrayOf("t"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(20.0, 25.0, 80.0, 25.0, LINE)),
        arrayOf(
            ReferenceStroke(20.0, 25.0, 20.0, 75.0, LINE),
            ReferenceStroke(20.0, 75.0, 80.0, 75.0, LINE),
        ),
        arrayOf(ReferenceStroke(20.0, 50.0, 80.0, 50.0, LINE)),
    ))
    val ㅍ = Letter("ㅍ", "pieup", arrayOf("p"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(25.0, 28.0, 75.0, 28.0, LINE)),
        arrayOf(ReferenceStroke(35.0, 28.0, 35.0, 72.0, LINE)),
        arrayOf(ReferenceStroke(65.0, 28.0, 65.0, 72.0, LINE)),
        arrayOf(ReferenceStroke(25.0, 72.0, 75.0, 72.0, LINE)),
    ))
    val ㅎ = Letter("ㅎ", "hieut", arrayOf("h"), CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(50.0, 15.0, 50.0, 30.0, LINE)),
        arrayOf(ReferenceStroke(25.0, 30.0, 75.0, 30.0, LINE)),
        arrayOf(ReferenceStroke(50.0, 38.0, 50.0, 78.0, CIRCLE, controlX = 25.0)),
    ))

    val consonants = listOf(ㄱ, ㄴ, ㄷ, ㄹ, ㅁ, ㅂ, ㅅ, ㅇ, ㅈ, ㅊ, ㅋ, ㅌ, ㅍ, ㅎ)

    // --- Tense consonants ---

    val ㄲ = Letter("ㄲ", "ssang-giyeok", arrayOf("kk"), TENSE_CONSONANT, arrayOf(
        arrayOf(
            ReferenceStroke(10.0, 25.0, 45.0, 25.0, LINE),
            ReferenceStroke(45.0, 25.0, 45.0, 80.0, LINE),
        ),
        arrayOf(
            ReferenceStroke(55.0, 25.0, 90.0, 25.0, LINE),
            ReferenceStroke(90.0, 25.0, 90.0, 80.0, LINE),
        ),
    ))
    val ㄸ = Letter("ㄸ", "ssang-digeut", arrayOf("tt"), TENSE_CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(8.0, 20.0, 45.0, 20.0, LINE)),
        arrayOf(
            ReferenceStroke(8.0, 20.0, 8.0, 80.0, LINE),
            ReferenceStroke(8.0, 80.0, 45.0, 80.0, LINE),
        ),
        arrayOf(ReferenceStroke(55.0, 20.0, 92.0, 20.0, LINE)),
        arrayOf(
            ReferenceStroke(55.0, 20.0, 55.0, 80.0, LINE),
            ReferenceStroke(55.0, 80.0, 92.0, 80.0, LINE),
        ),
    ))
    val ㅃ = Letter("ㅃ", "ssang-bieup", arrayOf("pp"), TENSE_CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(10.0, 20.0, 10.0, 80.0, LINE)),
        arrayOf(ReferenceStroke(42.0, 20.0, 42.0, 80.0, LINE)),
        arrayOf(ReferenceStroke(10.0, 50.0, 42.0, 50.0, LINE)),
        arrayOf(ReferenceStroke(10.0, 80.0, 42.0, 80.0, LINE)),
        arrayOf(ReferenceStroke(58.0, 20.0, 58.0, 80.0, LINE)),
        arrayOf(ReferenceStroke(90.0, 20.0, 90.0, 80.0, LINE)),
        arrayOf(ReferenceStroke(58.0, 50.0, 90.0, 50.0, LINE)),
        arrayOf(ReferenceStroke(58.0, 80.0, 90.0, 80.0, LINE)),
    ))
    val ㅆ = Letter("ㅆ", "ssang-siot", arrayOf("ss"), TENSE_CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(30.0, 20.0, 8.0, 80.0, CURVE, controlX = 26.0, controlY = 55.0)),
        arrayOf(ReferenceStroke(30.0, 20.0, 50.0, 80.0, CURVE, controlX = 34.0, controlY = 55.0)),
        arrayOf(ReferenceStroke(70.0, 20.0, 50.0, 80.0, CURVE, controlX = 66.0, controlY = 55.0)),
        arrayOf(ReferenceStroke(70.0, 20.0, 92.0, 80.0, CURVE, controlX = 74.0, controlY = 55.0)),
    ))
    val ㅉ = Letter("ㅉ", "ssang-jieut", arrayOf("jj"), TENSE_CONSONANT, arrayOf(
        arrayOf(ReferenceStroke(8.0, 20.0, 45.0, 20.0, LINE)),
        arrayOf(ReferenceStroke(27.0, 20.0, 10.0, 80.0, CURVE, controlX = 24.0, controlY = 52.0)),
        arrayOf(ReferenceStroke(27.0, 20.0, 43.0, 80.0, CURVE, controlX = 30.0, controlY = 52.0)),
        arrayOf(ReferenceStroke(55.0, 20.0, 92.0, 20.0, LINE)),
        arrayOf(ReferenceStroke(74.0, 20.0, 57.0, 80.0, CURVE, controlX = 71.0, controlY = 52.0)),
        arrayOf(ReferenceStroke(74.0, 20.0, 90.0, 80.0, CURVE, controlX = 77.0, controlY = 52.0)),
    ))

    val tenseConsonants = listOf(ㄲ, ㄸ, ㅃ, ㅆ, ㅉ)

    // --- Vowels ---

    val ㅏ = Letter("ㅏ", "a", arrayOf("a"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(35.0, 10.0, 35.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(35.0, 45.0, 55.0, 45.0, LINE)),
    ))
    val ㅑ = Letter("ㅑ", "ya", arrayOf("ya"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(30.0, 10.0, 30.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(30.0, 35.0, 50.0, 35.0, LINE)),
        arrayOf(ReferenceStroke(30.0, 60.0, 50.0, 60.0, LINE)),
    ))
    val ㅓ = Letter("ㅓ", "eo", arrayOf("eo"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(65.0, 10.0, 65.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(45.0, 45.0, 65.0, 45.0, LINE)),
    ))
    val ㅕ = Letter("ㅕ", "yeo", arrayOf("yeo"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(70.0, 10.0, 70.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(50.0, 35.0, 70.0, 35.0, LINE)),
        arrayOf(ReferenceStroke(50.0, 60.0, 70.0, 60.0, LINE)),
    ))
    val ㅗ = Letter("ㅗ", "o", arrayOf("o"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(50.0, 37.0, 50.0, 55.0, LINE)),
        arrayOf(ReferenceStroke(10.0, 55.0, 90.0, 55.0, LINE)),
    ))
    val ㅛ = Letter("ㅛ", "yo", arrayOf("yo"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(35.0, 37.0, 35.0, 55.0, LINE)),
        arrayOf(ReferenceStroke(65.0, 37.0, 65.0, 55.0, LINE)),
        arrayOf(ReferenceStroke(10.0, 55.0, 90.0, 55.0, LINE)),
    ))
    val ㅜ = Letter("ㅜ", "u", arrayOf("u"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(10.0, 45.0, 90.0, 45.0, LINE)),
        arrayOf(ReferenceStroke(50.0, 45.0, 50.0, 63.0, LINE)),
    ))
    val ㅠ = Letter("ㅠ", "yu", arrayOf("yu"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(15.0, 45.0, 85.0, 45.0, LINE)),
        arrayOf(ReferenceStroke(35.0, 45.0, 35.0, 66.0, LINE)),
        arrayOf(ReferenceStroke(65.0, 45.0, 65.0, 66.0, LINE)),
    ))
    val ㅡ = Letter("ㅡ", "eu", arrayOf("eu"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(10.0, 50.0, 90.0, 50.0, LINE)),
    ))
    val ㅣ = Letter("ㅣ", "i", arrayOf("i"), VOWEL, arrayOf(
        arrayOf(ReferenceStroke(50.0, 10.0, 50.0, 90.0, LINE)),
    ))

    val vowels = listOf(ㅏ, ㅑ, ㅓ, ㅕ, ㅗ, ㅛ, ㅜ, ㅠ, ㅡ, ㅣ)

    // --- Complex vowels ---

    val ㅐ = Letter("ㅐ", "ae", arrayOf("ae"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(25.0, 10.0, 25.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(25.0, 45.0, 60.0, 45.0, LINE)),
        arrayOf(ReferenceStroke(60.0, 10.0, 60.0, 90.0, LINE)),
    ))
    val ㅔ = Letter("ㅔ", "e", arrayOf("e"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(40.0, 10.0, 40.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(25.0, 45.0, 40.0, 45.0, LINE)),
        arrayOf(ReferenceStroke(60.0, 10.0, 60.0, 90.0, LINE)),
    ))
    val ㅒ = Letter("ㅒ", "yae", arrayOf("yae"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(22.0, 10.0, 22.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(22.0, 35.0, 58.0, 35.0, LINE)),
        arrayOf(ReferenceStroke(22.0, 60.0, 58.0, 60.0, LINE)),
        arrayOf(ReferenceStroke(58.0, 10.0, 58.0, 90.0, LINE)),
    ))
    val ㅖ = Letter("ㅖ", "ye", arrayOf("ye"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(42.0, 10.0, 42.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(22.0, 35.0, 42.0, 35.0, LINE)),
        arrayOf(ReferenceStroke(22.0, 60.0, 42.0, 60.0, LINE)),
        arrayOf(ReferenceStroke(62.0, 10.0, 62.0, 90.0, LINE)),
    ))
    val ㅘ = Letter("ㅘ", "wa", arrayOf("wa"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(36.0, 51.0, 36.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(16.0, 64.0, 56.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(65.0, 10.0, 65.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(65.0, 50.0, 85.0, 50.0, LINE)),
    ))
    val ㅝ = Letter("ㅝ", "wo", arrayOf("wo"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(16.0, 50.0, 56.0, 50.0, LINE)),
        arrayOf(ReferenceStroke(36.0, 50.0, 36.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(65.0, 10.0, 65.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(50.0, 70.0, 65.0, 70.0, LINE)),
    ))
    val ㅙ = Letter("ㅙ", "wae", arrayOf("wae"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(26.0, 51.0, 26.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(8.0, 64.0, 44.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(58.0, 10.0, 58.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(58.0, 50.0, 85.0, 50.0, LINE)),
        arrayOf(ReferenceStroke(85.0, 10.0, 85.0, 90.0, LINE)),
    ))
    val ㅞ = Letter("ㅞ", "we", arrayOf("we"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(12.0, 50.0, 50.0, 50.0, LINE)),
        arrayOf(ReferenceStroke(31.0, 50.0, 31.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(45.0, 70.0, 60.0, 70.0, LINE)),
        arrayOf(ReferenceStroke(60.0, 10.0, 60.0, 90.0, LINE)),
        arrayOf(ReferenceStroke(78.0, 10.0, 78.0, 90.0, LINE)),
    ))
    val ㅚ = Letter("ㅚ", "oe", arrayOf("oe"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(32.0, 51.0, 32.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(12.0, 64.0, 52.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(68.0, 10.0, 68.0, 90.0, LINE)),
    ))
    val ㅟ = Letter("ㅟ", "wi", arrayOf("wi"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(16.0, 50.0, 56.0, 50.0, LINE)),
        arrayOf(ReferenceStroke(36.0, 50.0, 36.0, 64.0, LINE)),
        arrayOf(ReferenceStroke(68.0, 10.0, 68.0, 90.0, LINE)),
    ))
    val ㅢ = Letter("ㅢ", "ui", arrayOf("ui"), COMPLEX_VOWEL, arrayOf(
        arrayOf(ReferenceStroke(10.0, 58.0, 62.0, 58.0, LINE)),
        arrayOf(ReferenceStroke(66.0, 10.0, 66.0, 90.0, LINE)),
    ))

    val complexVowels = listOf(ㅐ, ㅔ, ㅒ, ㅖ, ㅘ, ㅝ, ㅙ, ㅞ, ㅚ, ㅟ, ㅢ)

    fun getBasicLetters(): List<Letter> = consonants + vowels

    fun getAll(): List<Letter> = consonants + tenseConsonants + vowels + complexVowels

    fun findByCharacter(character: String): Letter? = getAll().find { it.character == character }

}
