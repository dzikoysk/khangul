import HangulGroups.LeadingConsonantsCount
import HangulGroups.InitialCount
import HangulGroups.SyllableBase
import HangulGroups.TrailingConsonantsCount
import HangulGroups.SyllableCount
import HangulGroups.VowelBase
import HangulGroups.MedialVowelCount
import HangulGroups.doubledRelativeFinale
import HangulGroups.doubledRelativeMedial
import HangulGroups.finaleCodes
import HangulGroups.initialCodes
import kotlin.math.floor

object HangulGroups {

    /* First consonants */
    private val initial = arrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
        'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )
    val initialCodes = initial.map { it.code }.toTypedArray()

    /* Last consonants */
    private val finale = arrayOf(
        0.toChar(),
        'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )
    val finaleCodes = finale.map { it.code }.toTypedArray()

    /* Relative */
    val doubledRelativeMedial = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 800, 801, 820, 0, 0, 1304, 1305, 1320, 0, 0, 1820)
    /* Relative finale */
    val doubledRelativeFinale = arrayOf(0, 0, 0, 119, 0, 422, 427, 0, 0, 801, 816, 817, 819, 825, 826, 827, 0, 0, 1719, 0, 1919)

    /* First syllable (가) */
    const val SyllableBase = 44032
    /* First vowel (ㅏ) */
    const val VowelBase = 12623

    const val MedialVowelCount = 21
    /* Initial factor */
    const val InitialCount = 588
    /* Medial factor */
    val TrailingConsonantsCount = finaleCodes.size
    val LeadingConsonantsCount = initialCodes.size
    val SyllableCount = LeadingConsonantsCount * MedialVowelCount * TrailingConsonantsCount // (11172) all possible variants

    init {
        require(LeadingConsonantsCount == 19) { "Initial count must be equal to 19" }
        require(TrailingConsonantsCount == 28) { "Finale count must be equal to 28" }
    }

}

fun composeHangul(input: String): String {
    if (input.isEmpty()) return ""

    var syllableCode = input[0].code
    var result = fromCharCode(syllableCode)

    for (charIdx in 1 until input.length) {
        val currentCode: Int = input[charIdx].code
        var initialCode = initialCodes.indexOf(syllableCode)
        val vowelDiff = currentCode - VowelBase

        if (initialCode != -1) {
            if (vowelDiff in 0 until MedialVowelCount) {
                syllableCode = SyllableBase + (initialCode * MedialVowelCount + vowelDiff) * TrailingConsonantsCount
                result = result.sliceExclusive(0..result.length) + fromCharCode(syllableCode)
                continue
            }
        }

        val relativeSyllableCode = syllableCode - SyllableBase
        val finalConsonant = relativeSyllableCode % TrailingConsonantsCount

        if (relativeSyllableCode in 0..11144 && finalConsonant == 0) {
            val currentFinale = finaleCodes.indexOf(currentCode)
            if (currentFinale != -1) {
                syllableCode += currentFinale
                result = result.sliceExclusive(0..result.length) + fromCharCode(syllableCode)
                continue
            }
            val mediaVowelBase = (relativeSyllableCode % InitialCount) / TrailingConsonantsCount
            val doubledMedialVowel = doubledRelativeMedial.indexOf((mediaVowelBase * 100) + (currentCode - VowelBase))
            if (doubledMedialVowel > 0) {
                syllableCode += (doubledMedialVowel - mediaVowelBase) * TrailingConsonantsCount
                result = result.sliceExclusive(0..result.length) + fromCharCode(syllableCode)
                continue
            }
        }

        if (relativeSyllableCode in 0 until SyllableCount && finalConsonant != 0) {
            if (vowelDiff in 0 until MedialVowelCount) {
                initialCode = initialCodes.indexOf(finaleCodes[finalConsonant])
                if (initialCode in 0 until LeadingConsonantsCount) {
                    result = result.sliceExclusive(0..result.length) + fromCharCode(syllableCode - finalConsonant)
                    syllableCode = SyllableBase + (initialCode * MedialVowelCount + vowelDiff) * TrailingConsonantsCount
                    result += fromCharCode(syllableCode)
                    continue
                }
                if (finalConsonant < doubledRelativeFinale.size && doubledRelativeFinale[finalConsonant] != 0) {
                    result = result.sliceExclusive(0..result.length) + fromCharCode(syllableCode - finalConsonant + floor(doubledRelativeFinale[finalConsonant] / 100.0).toInt())
                    syllableCode = SyllableBase + (initialCodes.indexOf(finaleCodes[(doubledRelativeFinale[finalConsonant] % 100)]) * MedialVowelCount + vowelDiff) * TrailingConsonantsCount
                    result += fromCharCode(syllableCode)
                    continue
                }
            }
            val doubledFinaleConsonant = doubledRelativeFinale.indexOf((finalConsonant * 100) + finaleCodes.indexOf(currentCode))
            if (doubledFinaleConsonant > 0) {
                syllableCode = syllableCode + doubledFinaleConsonant - finalConsonant
                result = result.sliceExclusive(0..result.length) + fromCharCode(syllableCode)
                continue
            }
        }

        syllableCode = currentCode
        result += fromCharCode(currentCode)
    }

    return result
}

fun String.sliceExclusive(range: IntRange): String =
    this.slice(range.first until range.last - 2)

fun fromCharCode(code: Int): String =
    Char(code).toString()