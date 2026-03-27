@file:OptIn(ExperimentalJsExport::class)

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.random.Random

@JsExport
@Suppress("unused", "MemberVisibilityCanBePrivate")
object Hangul {

    val consonants get() = Letters.consonants
    val vowels get() = Letters.vowels
    val complexVowels get() = Letters.complexVowels
    val tenseConsonants get() = Letters.tenseConsonants

    fun generateRandomSyllable(): String {
        val ctx = HangulContext()

        if (Random.nextBoolean()) {
            ctx.appendLetters(consonants.random().character)
        } else {
            ctx.appendLetter("ㅇ")
        }

        ctx.appendLetters(vowels.random().character)

        if (Random.nextBoolean()) {
            ctx.appendLetters(complexVowels.random().character)
        }

        if (Random.nextDouble() < 0.3) {
            ctx.appendLetters(tenseConsonants.random().character)
        }

        val result = ctx.getValue()

        if (result.length > 1) {
            return generateRandomSyllable()
        }

        return result
    }

    fun getBasicLetters(): List<Letter> =
        Letters.getBasicLetters()

    fun getAll(): List<Letter> =
        Letters.getAll()

}
