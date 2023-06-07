import HangulGroups.LeadingConsonantsCount
import HangulGroups.InitialCount
import HangulGroups.SyllableBase
import HangulGroups.TrailingConsonantsCount
import HangulGroups.SyllableCount
import HangulGroups.VowelBase
import HangulGroups.MedialVowelCount
import HangulGroups.doubledFinale
import HangulGroups.doubledMedial
import HangulGroups.finale
import HangulGroups.initial
import kotlin.math.floor

object HangulGroups {

    /* First consonants */
    val INITIAL = arrayOf(
        "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ",
        "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    )

    /* Last consonants */
    val FINALE = arrayOf(
        "", "ㄱ", "ㄲ", "ㄱㅅ", "ㄴ", "ㄴㅈ", "ㄴㅎ", "ㄷ", "ㄹ", "ㄹㄱ",
        "ㄹㅁ", "ㄹㅂ", "ㄹㅅ", "ㄹㅌ", "ㄹㅍ", "ㄹㅎ", "ㅁ", "ㅂ", "ㅂㅅ",
        "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    )

    val initial = arrayOf(12593, 12594, 12596, 12599, 12600, 12601, 12609, 12610, 12611, 12613, 12614, 12615, 12616, 12617, 12618, 12619, 12620, 12621, 12622)
    val finale = arrayOf(0, 12593, 12594, 12595, 12596, 12597, 12598, 12599, 12601, 12602, 12603, 12604, 12605, 12606, 12607, 12608, 12609, 12610, 12612, 12613, 12614, 12615, 12616, 12618, 12619, 12620, 12621, 12622)
    val doubledMedial = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 800, 801, 820, 0, 0, 1304, 1305, 1320, 0, 0, 1820)
    val doubledFinale = arrayOf(0, 0, 0, 119, 0, 422, 427, 0, 0, 801, 816, 817, 819, 825, 826, 827, 0, 0, 1719, 0, 1919)

    const val SyllableBase = 44032 // S
    const val ConsonantsBase = 4352 // L
    const val VowelBase = 12623 // V
    const val OldVowelBase = 4519 // T
    const val LeadingConsonantsCount = 19
    const val MedialVowelCount = 21
    const val TrailingConsonantsCount = 28
    const val InitialCount = 588
    const val SyllableCount = LeadingConsonantsCount * MedialVowelCount * TrailingConsonantsCount // (11172) all possible variants

    init {
        println("" + initial.size + " " + finale.size + " " + doubledMedial.size + " " + doubledFinale.size)
    }

}

fun composeHangul(input: String): String {
    if (input.isEmpty()) return ""

    var currentSymbolBase = input[0].code
    var result = fromCharCode(currentSymbolBase)

    for (charIdx in 1 until input.length) {
        val currentCode: Int = input[charIdx].code
        var initialCode = initial.indexOf(currentSymbolBase)
        val vowelDiff = currentCode - VowelBase

        if (initialCode != -1) {
            if (vowelDiff in 0 until MedialVowelCount) {
                currentSymbolBase = SyllableBase + (initialCode * MedialVowelCount + vowelDiff) * TrailingConsonantsCount
                result = result.sliceExclusive(0..result.length) + fromCharCode(currentSymbolBase)
                continue
            }
        }

        val syllableCode = currentSymbolBase - SyllableBase
        val finalConsonant = syllableCode % TrailingConsonantsCount

        if (syllableCode in 0..11144 && finalConsonant == 0) {
            val finaleCode = finale.indexOf(currentCode)
            if (finaleCode != -1) {
                currentSymbolBase += finaleCode
                result = result.sliceExclusive(0..result.length) + fromCharCode(currentSymbolBase)
                continue
            }
            val K = (syllableCode % InitialCount) / TrailingConsonantsCount
            val doubledMedialVowel = doubledMedial.indexOf((K * 100) + (currentCode - VowelBase))
            if (doubledMedialVowel > 0) {
                currentSymbolBase += (doubledMedialVowel - K) * TrailingConsonantsCount
                result = result.sliceExclusive(0..result.length) + fromCharCode(currentSymbolBase)
                continue
            }
        }

        if (syllableCode in 0 until SyllableCount && finalConsonant != 0) {
            if (vowelDiff in 0 until MedialVowelCount) {
                initialCode = initial.indexOf(finale[finalConsonant])
                if (initialCode in 0 until LeadingConsonantsCount) {
                    result = result.sliceExclusive(0..result.length) + fromCharCode(currentSymbolBase - finalConsonant)
                    currentSymbolBase = SyllableBase + (initialCode * MedialVowelCount + vowelDiff) * TrailingConsonantsCount
                    result += fromCharCode(currentSymbolBase)
                    continue
                }
                if (finalConsonant < doubledFinale.size && doubledFinale[finalConsonant] != 0) {
                    result = result.sliceExclusive(0..result.length) + fromCharCode(currentSymbolBase - finalConsonant + floor(doubledFinale[finalConsonant] / 100.0).toInt())
                    currentSymbolBase = SyllableBase + (initial.indexOf(finale[(doubledFinale[finalConsonant] % 100)]) * MedialVowelCount + vowelDiff) * TrailingConsonantsCount
                    result += fromCharCode(currentSymbolBase)
                    continue
                }
            }
            val doubledFinaleConsonant = doubledFinale.indexOf((finalConsonant * 100) + finale.indexOf(currentCode))
            if (doubledFinaleConsonant > 0) {
                currentSymbolBase = currentSymbolBase + doubledFinaleConsonant - finalConsonant
                result = result.sliceExclusive(0..result.length) + fromCharCode(currentSymbolBase)
                continue
            }
        }
        currentSymbolBase = currentCode
        result += fromCharCode(currentCode)
    }
    return result
}

fun String.sliceExclusive(range: IntRange): String =
    this.slice(range.first until range.last - 2)

fun fromCharCode(code: Int): String =
    Char(code).toString()