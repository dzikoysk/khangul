@file:OptIn(ExperimentalJsExport::class)

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.random.Random

@JsExport
@Suppress("unused", "MemberVisibilityCanBePrivate")
object Hangul {

    class Letter(
        val koreanLetter: String,
        vararg val romanization: String,
    )

    val consonants = listOf(
        Letter("ㄱ", "g", "k"),
        Letter("ㄴ", "n"),
        Letter("ㄷ", "d", "t"),
        Letter("ㄹ", "r", "l"),
        Letter("ㅁ", "m"),
        Letter("ㅂ", "b", "p"),
        Letter("ㅅ", "s"),
        Letter("ㅇ", "ng"),
        Letter("ㅈ", "j"),
        Letter("ㅊ", "ch"),
        Letter("ㅋ", "k"),
        Letter("ㅌ", "t"),
        Letter("ㅍ", "p"),
        Letter("ㅎ", "h"),
    )

    val vowels = listOf(
        Letter("ㅏ", "a"),
        Letter("ㅑ", "ya"),
        Letter("ㅓ", "eo"),
        Letter("ㅕ", "yeo"),
        Letter("ㅗ", "o"),
        Letter("ㅛ", "yo"),
        Letter("ㅜ", "u"),
        Letter("ㅠ", "yu"),
        Letter("ㅡ", "eu"),
        Letter("ㅣ", "i"),
    )

    val combinationVowels = listOf(
        Letter("ㅐ", "ae"),
        Letter("ㅔ", "e"),
        Letter("ㅒ", "yae"),
        Letter("ㅖ", "ye"),
        Letter("ㅘ", "wa"),
        Letter("ㅝ", "wo"),
        Letter("ㅙ", "wae"),
        Letter("ㅞ", "we"),
        Letter("ㅚ", "oe"),
        Letter("ㅟ", "wi"),
        Letter("ㅢ", "ui"),
    )

    val tenseConsonants = listOf(
        Letter("ㄲ", "kk"),
        Letter("ㄸ", "tt"),
        Letter("ㅃ", "pp"),
        Letter("ㅆ", "ss"),
        Letter("ㅉ", "jj"),
    )

    fun generateRandomSyllable(): String {
        val ctx = HangulContext()

        if (Random.nextBoolean()) {
            ctx.appendLetters(consonants.random().koreanLetter)
        } else {
            ctx.appendLetter("ㅇ")
        }

        ctx.appendLetters(vowels.random().koreanLetter)

        if (Random.nextBoolean()) {
            ctx.appendLetters(combinationVowels.random().koreanLetter)
        }

        if (Random.nextDouble() < 0.3) {
            ctx.appendLetters(tenseConsonants.random().koreanLetter)
        }

        val result = ctx.getValue()

        if (result.length > 1) {
            return generateRandomSyllable()
        }

        return result
    }

    fun getBasicLetters(): List<Letter> =
        consonants + vowels

    fun getAll(): List<Letter> =
        consonants + tenseConsonants + vowels + combinationVowels

}