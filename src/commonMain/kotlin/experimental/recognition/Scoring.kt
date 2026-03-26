package experimental.recognition

import kotlin.math.*

// =============================================================================
// Scoring: structural signature → recognition results
//
// Compares user signature against reference signatures.
// Final score = baseScore × unmappedPenalty (multiplicative).
// =============================================================================

// --- Scoring weights ---
private const val WEIGHT_COUNT = 0.10
private const val WEIGHT_DISTRIBUTION = 0.10
private const val WEIGHT_DIRECTION = 0.20
private const val WEIGHT_POSITION = 0.25
private const val WEIGHT_RELATION = 0.35

// --- Scoring constants ---
private const val COUNT_PENALTY_PER_DIFF = 0.40          // 40% penalty per stroke count difference
private const val COUNT_EARLY_EXIT_DIFF = 2              // diff ≥ this → early exit with heavy penalty
private const val DIST_PENALTY_PER_MISMATCH = 0.25       // 25% per type distribution mismatch
private const val UNMAPPED_PENALTY_PER_STROKE = 0.30     // multiplicative penalty per unmapped stroke
private const val POSITION_STRICTNESS = 3.5              // multiplier for center-distance falloff
private const val MATCH_THRESHOLD = 0.30                 // minimum combined score to consider a match
private const val HV_BOUNDARY_CREDIT = 0.30              // credit for H↔V mismatch when angles are close
private const val HV_BOUNDARY_ANGLE = 20.0               // max angle diff for H↔V boundary credit
private const val RELATION_FALLBACK = 0.30               // relation score when < 2 matched strokes
private const val CONNECTION_MISMATCH_PENALTY = -0.30    // penalty when one pair connected, other not

// --- Relation sub-weights ---
private const val RELATION_H_WEIGHT = 0.25
private const val RELATION_V_WEIGHT = 0.25
private const val RELATION_CONN_WEIGHT = 0.50
private const val CONN_POINT_WEIGHT = 0.50
private const val CONN_TYPE_WEIGHT = 0.30
private const val CONN_QUALITY_WEIGHT = 0.20
private const val PARTIAL_POSITION_CREDIT = 0.40         // credit when one position is "overlapping"

// --- Direction scoring ---
private const val DIR_WEIGHT = 0.70
private const val POS_WEIGHT = 0.30


// =============================================================================
// Signature comparison
// =============================================================================

data class ComparisonResult(val score: Double, val debug: String)

/**
 * Compare two structural signatures and return a similarity score (0-1).
 */
fun compareSignatures(user: StructuralSignature, ref: StructuralSignature): ComparisonResult {
    val debug = StringBuilder()

    // Stroke count comparison
    val countDiff = abs(user.strokeCount - ref.strokeCount)
    val countScore = maxOf(0.0, 1.0 - countDiff * COUNT_PENALTY_PER_DIFF)
    debug.append("Strokes: ${user.strokeCount}/${ref.strokeCount} | ")

    if (user.strokeCount == 0 || ref.strokeCount == 0) {
        return ComparisonResult(0.0, debug.toString())
    }

    if (countDiff >= COUNT_EARLY_EXIT_DIFF) {
        return ComparisonResult(countScore * 0.5, debug.append("Too different").toString())
    }

    // Type distribution (H/V/circle)
    fun typeDistribution(strokes: List<AnalyzedStroke>): Triple<Int, Int, Int> {
        var h = 0; var v = 0; var c = 0
        for (s in strokes) when (s.direction) {
            StrokeDirection.HORIZONTAL -> h++
            StrokeDirection.VERTICAL -> v++
            StrokeDirection.CIRCLE -> c++
        }
        return Triple(h, v, c)
    }

    val (uh, uv, uc) = typeDistribution(user.strokes)
    val (rh, rv, rc) = typeDistribution(ref.strokes)
    val distMismatch = abs(uh - rh) + abs(uv - rv) + abs(uc - rc)
    val distScore = maxOf(0.0, 1.0 - distMismatch * DIST_PENALTY_PER_MISMATCH)

    // Greedy stroke mapping
    data class StrokeMapping(val refIdx: Int, val userIdx: Int, val score: Double)
    val mappings = mutableListOf<StrokeMapping>()

    for (r in ref.strokes.indices) {
        for (u in user.strokes.indices) {
            val refStroke = ref.strokes[r]
            val userStroke = user.strokes[u]

            // Direction matching
            var dirScore = 0.0
            if (refStroke.direction == StrokeDirection.CIRCLE || userStroke.direction == StrokeDirection.CIRCLE) {
                dirScore = if (refStroke.direction == userStroke.direction) 1.0 else 0.0
            } else if (refStroke.direction == userStroke.direction) {
                val refAngle = ((refStroke.angle % 180.0) + 180.0) % 180.0
                val userAngle = ((userStroke.angle % 180.0) + 180.0) % 180.0
                var angleDiff = abs(refAngle - userAngle)
                if (angleDiff > 90.0) angleDiff = 180.0 - angleDiff
                dirScore = maxOf(0.5, 1.0 - angleDiff / 90.0 * 0.5)
            } else {
                val refAngle = ((refStroke.angle % 180.0) + 180.0) % 180.0
                val userAngle = ((userStroke.angle % 180.0) + 180.0) % 180.0
                var angleDiff = abs(refAngle - userAngle)
                if (angleDiff > 90.0) angleDiff = 180.0 - angleDiff
                dirScore = if (angleDiff < HV_BOUNDARY_ANGLE) HV_BOUNDARY_CREDIT else 0.0
            }

            // Position similarity
            val posDist = distance(refStroke.centerPoint, userStroke.centerPoint)
            val posScore = maxOf(0.0, 1.0 - posDist * POSITION_STRICTNESS)

            val matchScore = dirScore * DIR_WEIGHT + posScore * POS_WEIGHT

            if (dirScore > 0 && matchScore > MATCH_THRESHOLD) {
                mappings.add(StrokeMapping(r, u, matchScore))
            }
        }
    }

    // Greedy assignment: best non-conflicting matches
    mappings.sortByDescending { it.score }
    val usedRef = mutableSetOf<Int>()
    val usedUser = mutableSetOf<Int>()
    val finalMapping = mutableListOf<StrokeMapping>()
    var totalDirScore = 0.0

    for (m in mappings) {
        if (m.refIdx !in usedRef && m.userIdx !in usedUser) {
            usedRef.add(m.refIdx)
            usedUser.add(m.userIdx)
            finalMapping.add(m)
            totalDirScore += m.score
        }
    }

    // Unmapped strokes penalty
    val unmappedUser = user.strokeCount - usedUser.size
    val unmappedRef = ref.strokeCount - usedRef.size
    val totalUnmapped = unmappedUser + unmappedRef
    val maxStrokeCount = maxOf(user.strokeCount, ref.strokeCount)
    val directionScore = if (maxStrokeCount > 0) totalDirScore / maxStrokeCount else 0.0
    val unmappedPenalty = maxOf(0.0, 1.0 - totalUnmapped * UNMAPPED_PENALTY_PER_STROKE)
    debug.append("Dir: ${(directionScore * 100).roundToInt()}% Unmap: $totalUnmapped | ")

    // Position accuracy (quadratic falloff)
    var positionAccuracy = 0.0
    for (m in finalMapping) {
        val refS = ref.strokes[m.refIdx]
        val userS = user.strokes[m.userIdx]
        val dist = distance(refS.centerPoint, userS.centerPoint)
        positionAccuracy += maxOf(0.0, 1.0 - (dist * 2.0).pow(2))
    }
    positionAccuracy = if (maxStrokeCount > 0) positionAccuracy / maxStrokeCount else 0.0
    debug.append("PosAcc: ${(positionAccuracy * 100).roundToInt()}% | ")

    // Relation scoring
    val relationScore: Double
    if (finalMapping.size < 2) {
        relationScore = RELATION_FALLBACK
        debug.append("Pos: N/A")
    } else {
        var positionMatches = 0.0
        var totalPositionChecks = 0

        for (i in finalMapping.indices) {
            for (j in i + 1 until finalMapping.size) {
                val refRel = ref.relations[finalMapping[i].refIdx][finalMapping[j].refIdx]
                val userRel = user.relations[finalMapping[i].userIdx][finalMapping[j].userIdx]
                totalPositionChecks++

                // Horizontal position match
                val hMatch = when {
                    refRel.horizontalPosition == userRel.horizontalPosition -> 1.0
                    refRel.horizontalPosition == HorizontalPosition.OVERLAPPING || userRel.horizontalPosition == HorizontalPosition.OVERLAPPING -> PARTIAL_POSITION_CREDIT
                    else -> 0.0  // opposite = zero
                }

                // Vertical position match
                val vMatch = when {
                    refRel.verticalPosition == userRel.verticalPosition -> 1.0
                    refRel.verticalPosition == VerticalPosition.OVERLAPPING || userRel.verticalPosition == VerticalPosition.OVERLAPPING -> PARTIAL_POSITION_CREDIT
                    else -> 0.0
                }

                // Connection match
                var connMatch = 0.0
                if (refRel.connected && userRel.connected) {
                    val pointMatch = when {
                        refRel.connectionPointOnStroke2 == userRel.connectionPointOnStroke2 -> 1.0
                        // Adjacent positions get partial credit
                        (refRel.connectionPointOnStroke2 == ConnectionPoint.START && userRel.connectionPointOnStroke2 == ConnectionPoint.MIDDLE) ||
                        (refRel.connectionPointOnStroke2 == ConnectionPoint.MIDDLE && userRel.connectionPointOnStroke2 == ConnectionPoint.START) ||
                        (refRel.connectionPointOnStroke2 == ConnectionPoint.END && userRel.connectionPointOnStroke2 == ConnectionPoint.MIDDLE) ||
                        (refRel.connectionPointOnStroke2 == ConnectionPoint.MIDDLE && userRel.connectionPointOnStroke2 == ConnectionPoint.END) -> PARTIAL_POSITION_CREDIT
                        else -> 0.0  // start vs end = 0
                    }

                    var typeMatch = 0.0
                    if (refRel.connectionType == userRel.connectionType) {
                        typeMatch = 1.0
                    } else {
                        val refParts = refRel.connectionType.split("-")
                        val userParts = userRel.connectionType.split("-")
                        if (refParts.size == 2 && userParts.size == 2) {
                            if (refParts[0] == userParts[0] || refParts[1] == userParts[1]) {
                                typeMatch = 0.5
                            }
                        }
                    }

                    val qualityMatch = 1.0 - abs(refRel.connectionQuality - userRel.connectionQuality)
                    connMatch = pointMatch * CONN_POINT_WEIGHT + typeMatch * CONN_TYPE_WEIGHT + qualityMatch * CONN_QUALITY_WEIGHT
                } else if (!refRel.connected && !userRel.connected) {
                    connMatch = 1.0
                }

                val connectionMismatch = if (refRel.connected != userRel.connected) CONNECTION_MISMATCH_PENALTY else 0.0

                positionMatches += maxOf(0.0, hMatch * RELATION_H_WEIGHT + vMatch * RELATION_V_WEIGHT + connMatch * RELATION_CONN_WEIGHT + connectionMismatch)
            }
        }

        relationScore = if (totalPositionChecks > 0) positionMatches / totalPositionChecks else RELATION_FALLBACK
        debug.append("Pos: ${(relationScore * 100).roundToInt()}%")
    }

    val baseScore = countScore * WEIGHT_COUNT +
        distScore * WEIGHT_DISTRIBUTION +
        directionScore * WEIGHT_DIRECTION +
        positionAccuracy * WEIGHT_POSITION +
        relationScore * WEIGHT_RELATION
    val finalScore = baseScore * unmappedPenalty
    debug.append(" | Dist: ${(distScore * 100).roundToInt()}% Pen: ${(unmappedPenalty * 100).roundToInt()}%")

    return ComparisonResult(finalScore, debug.toString())
}


// =============================================================================
// Full recognition
// =============================================================================

/**
 * Recognize which letter the user drew. Returns all candidates sorted by score descending.
 */
fun recognizeShape(userPaths: List<List<DrawingPoint>>, flexible: Boolean = false): List<RecognitionResult> {
    val userSig = createSignature(userPaths, flexible) ?: return emptyList()

    // Build debug prefix
    var h = 0; var v = 0
    val angles = mutableListOf<Int>()
    for (s in userSig.strokes) {
        when (s.direction) {
            StrokeDirection.HORIZONTAL -> h++
            StrokeDirection.VERTICAL -> v++
            else -> {}
        }
        angles.add(s.angle.roundToInt())
    }
    val userDebug = "User: ${userSig.strokeCount} [${h}H ${v}V] angles:[${angles.joinToString(",")}] | "

    val results = mutableListOf<RecognitionResult>()

    for (letter in ReferenceData.allLetters) {
        val strokeDefs = ReferenceData.strokes[letter] ?: continue
        val refSig = createRefSignature(strokeDefs) ?: continue
        val (score, debug) = compareSignatures(userSig, refSig)
        results.add(RecognitionResult(
            letter = letter,
            coverage = (score * 100).roundToInt(),
            debug = userDebug + debug,
        ))
    }

    results.sortByDescending { it.coverage }
    return results
}
