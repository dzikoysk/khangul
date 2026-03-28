package experimental.recognition.steps

import Letters
import experimental.recognition.*
import experimental.recognition.math.*
import kotlin.math.abs
import kotlin.math.roundToInt

// Final score = weighted sum of (count, distribution, direction, position, relation) × unmapped penalty
private const val WEIGHT_COUNT = 0.10
private const val WEIGHT_DISTRIBUTION = 0.10
private const val WEIGHT_DIRECTION = 0.20
private const val WEIGHT_POSITION = 0.25
private const val WEIGHT_RELATION = 0.35


private const val COUNT_PENALTY_PER_DIFF = 0.40
private const val COUNT_EARLY_EXIT_DIFF = 2
private const val DIST_PENALTY_PER_MISMATCH = 0.25
private const val UNMAPPED_PENALTY_PER_STROKE = 0.30
private const val POSITION_STRICTNESS = 3.5
private const val MATCH_THRESHOLD = 0.30
private const val HV_BOUNDARY_CREDIT = 0.30
private const val HV_BOUNDARY_ANGLE = 20.0
private const val RELATION_FALLBACK = 0.30
private const val CONNECTION_MISMATCH_PENALTY = -0.30


private const val RELATION_H_WEIGHT = 0.25
private const val RELATION_V_WEIGHT = 0.25
private const val RELATION_CONN_WEIGHT = 0.50
private const val CONN_POINT_WEIGHT = 0.50
private const val CONN_TYPE_WEIGHT = 0.30
private const val CONN_QUALITY_WEIGHT = 0.20
private const val PARTIAL_POSITION_CREDIT = 0.40


private const val DIR_WEIGHT = 0.70
private const val POS_WEIGHT = 0.30



data class ComparisonResult(val score: Double, val debug: String)

private data class StrokeMapping(val refIdx: Int, val userIdx: Int, val score: Double)

/**
 * Compare two structural signatures and return a similarity score (0-1).
 */
fun compareSignatures(user: StructuralSignature, ref: StructuralSignature): ComparisonResult {
    val debug = StringBuilder()

    val countDiff = abs(user.strokeCount - ref.strokeCount)
    val countScore = linearFalloff(countDiff.toDouble(), rate = COUNT_PENALTY_PER_DIFF)
    debug.append("Strokes: ${user.strokeCount}/${ref.strokeCount} | ")

    if (user.strokeCount == 0 || ref.strokeCount == 0) {
        return ComparisonResult(0.0, debug.toString())
    }
    if (countDiff >= COUNT_EARLY_EXIT_DIFF) {
        return ComparisonResult(countScore * 0.5, debug.append("Too different").toString())
    }

    val distributionScore = scoreTypeDistribution(user.strokes, ref.strokes)
    val finalMapping = findBestStrokeMappings(user, ref)

    val maxStrokeCount = maxOf(user.strokeCount, ref.strokeCount)
    val totalUnmapped = (user.strokeCount - finalMapping.size) + (ref.strokeCount - finalMapping.size)
    val directionScore = if (maxStrokeCount > 0) finalMapping.sumOf { it.score } / maxStrokeCount else 0.0
    val unmappedPenalty = linearFalloff(totalUnmapped.toDouble(), rate = UNMAPPED_PENALTY_PER_STROKE)
    debug.append("Dir: ${(directionScore * 100).roundToInt()}% Unmap: $totalUnmapped | ")

    val positionAccuracy = scorePositionAccuracy(finalMapping, user, ref, maxStrokeCount)
    debug.append("PosAcc: ${(positionAccuracy * 100).roundToInt()}% | ")

    val relationScore = scoreRelations(finalMapping, user, ref)
    debug.append("Pos: ${if (finalMapping.size < 2) "N/A" else "${(relationScore * 100).roundToInt()}%"}")

    val baseScore = countScore * WEIGHT_COUNT +
        distributionScore * WEIGHT_DISTRIBUTION +
        directionScore * WEIGHT_DIRECTION +
        positionAccuracy * WEIGHT_POSITION +
        relationScore * WEIGHT_RELATION
    val finalScore = baseScore * unmappedPenalty
    debug.append(" | Dist: ${(distributionScore * 100).roundToInt()}% Pen: ${(unmappedPenalty * 100).roundToInt()}%")

    return ComparisonResult(finalScore, debug.toString())
}



private fun scoreTypeDistribution(userStrokes: List<AnalyzedStroke>, refStrokes: List<AnalyzedStroke>): Double {
    fun count(strokes: List<AnalyzedStroke>, direction: StrokeDirection) = strokes.count { it.direction == direction }
    val mismatch = abs(count(userStrokes, StrokeDirection.HORIZONTAL) - count(refStrokes, StrokeDirection.HORIZONTAL)) +
        abs(count(userStrokes, StrokeDirection.VERTICAL) - count(refStrokes, StrokeDirection.VERTICAL)) +
        abs(count(userStrokes, StrokeDirection.CIRCLE) - count(refStrokes, StrokeDirection.CIRCLE))
    return linearFalloff(mismatch.toDouble(), rate = DIST_PENALTY_PER_MISMATCH)
}

private fun scoreDirectionMatch(refStroke: AnalyzedStroke, userStroke: AnalyzedStroke): Double {
    if (refStroke.direction == StrokeDirection.CIRCLE || userStroke.direction == StrokeDirection.CIRCLE) {
        return if (refStroke.direction == userStroke.direction) 1.0 else 0.0
    }
    val angleDiff = angleDifference180(refStroke.angle, userStroke.angle)
    return if (refStroke.direction == userStroke.direction) {
        linearFalloff(angleDiff / 90.0, rate = 0.5, floor = 0.5)
    } else {
        if (angleDiff < HV_BOUNDARY_ANGLE) HV_BOUNDARY_CREDIT else 0.0
    }
}

private fun findBestStrokeMappings(user: StructuralSignature, ref: StructuralSignature): List<StrokeMapping> {
    val candidates = mutableListOf<StrokeMapping>()

    for (refIndex in ref.strokes.indices) {
        for (userIndex in user.strokes.indices) {
            val dirScore = scoreDirectionMatch(ref.strokes[refIndex], user.strokes[userIndex])
            val posDist = ref.strokes[refIndex].centerPoint.toVec2().distanceTo(user.strokes[userIndex].centerPoint.toVec2())
            val posScore = linearFalloff(posDist, rate = POSITION_STRICTNESS)
            val matchScore = dirScore * DIR_WEIGHT + posScore * POS_WEIGHT

            if (dirScore > 0 && matchScore > MATCH_THRESHOLD) {
                candidates.add(StrokeMapping(refIndex, userIndex, matchScore))
            }
        }
    }

    candidates.sortByDescending { it.score }
    val usedRef = mutableSetOf<Int>()
    val usedUser = mutableSetOf<Int>()
    val result = mutableListOf<StrokeMapping>()

    for (mapping in candidates) {
        if (mapping.refIdx !in usedRef && mapping.userIdx !in usedUser) {
            usedRef.add(mapping.refIdx)
            usedUser.add(mapping.userIdx)
            result.add(mapping)
        }
    }

    return result
}

private fun scorePositionAccuracy(
    mappings: List<StrokeMapping>,
    user: StructuralSignature,
    ref: StructuralSignature,
    maxStrokeCount: Int,
): Double {
    val total = mappings.sumOf { mapping ->
        val dist = ref.strokes[mapping.refIdx].centerPoint.toVec2()
            .distanceTo(user.strokes[mapping.userIdx].centerPoint.toVec2())
        quadraticFalloff(dist, rate = 2.0)
    }
    return if (maxStrokeCount > 0) total / maxStrokeCount else 0.0
}

private fun scoreRelations(
    mappings: List<StrokeMapping>,
    user: StructuralSignature,
    ref: StructuralSignature,
): Double {
    if (mappings.size < 2) return RELATION_FALLBACK

    var positionMatches = 0.0
    var totalChecks = 0

    for (i in mappings.indices) {
        for (j in i + 1 until mappings.size) {
            val refRelation = ref.relations[mappings[i].refIdx][mappings[j].refIdx]
            val userRelation = user.relations[mappings[i].userIdx][mappings[j].userIdx]
            totalChecks++

            val horizontalMatch = scorePositionMatch(refRelation.horizontalPosition, userRelation.horizontalPosition)
            val verticalMatch = scorePositionMatch(refRelation.verticalPosition, userRelation.verticalPosition)
            val connectionMatch = scoreConnectionMatch(refRelation, userRelation)
            val connectionMismatch = if (refRelation.connected != userRelation.connected) CONNECTION_MISMATCH_PENALTY else 0.0

            positionMatches += maxOf(0.0,
                horizontalMatch * RELATION_H_WEIGHT +
                    verticalMatch * RELATION_V_WEIGHT +
                    connectionMatch * RELATION_CONN_WEIGHT +
                    connectionMismatch)
        }
    }

    return if (totalChecks > 0) positionMatches / totalChecks else RELATION_FALLBACK
}

private fun <T> scorePositionMatch(refPosition: T, userPosition: T): Double = when {
    refPosition == userPosition -> 1.0
    refPosition == HorizontalPosition.OVERLAPPING || userPosition == HorizontalPosition.OVERLAPPING -> PARTIAL_POSITION_CREDIT
    refPosition == VerticalPosition.OVERLAPPING || userPosition == VerticalPosition.OVERLAPPING -> PARTIAL_POSITION_CREDIT
    else -> 0.0
}

private fun scoreConnectionMatch(refRelation: StrokeRelation, userRelation: StrokeRelation): Double {
    if (!refRelation.connected && !userRelation.connected) return 1.0
    if (refRelation.connected != userRelation.connected) return 0.0

    val pointMatch = when {
        refRelation.connectionPointOnStroke2 == userRelation.connectionPointOnStroke2 -> 1.0
        refRelation.connectionPointOnStroke2.isAdjacentTo(userRelation.connectionPointOnStroke2) -> PARTIAL_POSITION_CREDIT
        else -> 0.0
    }

    val typeMatch = when {
        refRelation.connectionType == userRelation.connectionType -> 1.0
        refRelation.connectionType.sharesPart(userRelation.connectionType) -> 0.5
        else -> 0.0
    }

    val qualityMatch = 1.0 - abs(refRelation.connectionQuality - userRelation.connectionQuality)

    return pointMatch * CONN_POINT_WEIGHT + typeMatch * CONN_TYPE_WEIGHT + qualityMatch * CONN_QUALITY_WEIGHT
}

private fun ConnectionPoint.isAdjacentTo(other: ConnectionPoint): Boolean = when (this) {
    ConnectionPoint.START -> other == ConnectionPoint.MIDDLE
    ConnectionPoint.MIDDLE -> other == ConnectionPoint.START || other == ConnectionPoint.END
    ConnectionPoint.END -> other == ConnectionPoint.MIDDLE
    ConnectionPoint.NONE -> false
}

private fun String.sharesPart(other: String): Boolean {
    val thisParts = split("-")
    val otherParts = other.split("-")
    return thisParts.size == 2 && otherParts.size == 2 &&
        (thisParts[0] == otherParts[0] || thisParts[1] == otherParts[1])
}



/**
 * Recognize which letter the user drew. Returns all candidates sorted by score descending.
 */
fun recognizeShape(userPaths: List<List<DrawingPoint>>, flexible: Boolean = false): List<RecognitionResult> {
    val userSignature = createSignature(userPaths, flexible) ?: return emptyList()

    val results = mutableListOf<RecognitionResult>()

    for (letter in Letters.getAll()) {
        val strokeDefs = letter.referenceStrokes.toList()
        if (strokeDefs.isEmpty()) continue
        val refSignature = createRefSignature(strokeDefs) ?: continue
        val comparison = compareSignatures(userSignature, refSignature)
        results.add(RecognitionResult(
            letter = letter,
            coverage = (comparison.score * 100).roundToInt(),
            debug = comparison.debug,
        ))
    }

    results.sortByDescending { it.coverage }
    return results
}
