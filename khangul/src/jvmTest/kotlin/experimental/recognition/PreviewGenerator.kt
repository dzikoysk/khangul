package experimental.recognition

import Letter
import Letters
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.test.Test

/**
 * JVM-only utility: generates test data for the recognition module.
 *
 * Outputs for each letter:
 * - JSON file with the paths data (the actual test input — machine-readable)
 * - PNG file with color-coded stroke preview (for human review)
 *
 * Run these tests to regenerate all test data into testdata/.
 */
class PreviewGenerator {

    private val baseDir = File("testdata")

    private val strokeColors = listOf(
        Color(220, 50, 50),    // red
        Color(50, 100, 220),   // blue
        Color(50, 180, 50),    // green
        Color(220, 140, 20),   // orange
        Color(160, 50, 200),   // purple
        Color(20, 180, 180),   // cyan
        Color(200, 50, 130),   // pink
        Color(120, 120, 20),   // olive
    )

    companion object {
        private const val IMAGE_SIZE = 400
        private const val STROKE_WIDTH = 3.5f
        private const val MARKER_RADIUS = 10
    }

    /**
     * Structure:
     * ```
     * testdata/
     *   ㄱ/
     *     synthetic_1.json
     *     synthetic_1.png
     *   ㅇ/
     *     synthetic_1.json
     *     synthetic_1.png
     *   _overview_grid.png
     * ```
     *
     * Later you add captured drawings:
     * ```
     *   ㄱ/
     *     synthetic_1.json / .png
     *     captured_1.json / .png
     *     captured_2.json / .png
     * ```
     */
    @Test
    fun generateAllSyntheticData() {
        var count = 0

        for (letter in Letters.getAll()) {
            val letterDir = File(baseDir, letter.character).also { it.mkdirs() }

            val hasCircle = letter.referenceStrokes.any { step -> step.any { it.type == StrokeType.CIRCLE } }

            // synthetic_1: wobbly circles for letters with circles, clean otherwise
            val primary = SyntheticDrawings.wobblyDrawing(letter, 80)
            if (primary.isEmpty()) continue
            File(letterDir, "synthetic_1.json").writeText(pathsToJson(letter.character, primary))
            ImageIO.write(renderDrawing(letter, primary), "PNG", File(letterDir, "synthetic_1.png"))

            // synthetic_2: clean circle variant (only for letters that have circle strokes)
            if (hasCircle) {
                val clean = SyntheticDrawings.cleanDrawing(letter, 80)
                File(letterDir, "synthetic_2.json").writeText(pathsToJson(letter.character, clean))
                ImageIO.write(renderDrawing(letter, clean), "PNG", File(letterDir, "synthetic_2.png"))
            }

            count++
        }

        // ㅢ connected variant
        val uiDir = File(baseDir, Letters.ㅢ.character).also { it.mkdirs() }
        val uiConnected = SyntheticDrawings.uiConnected(80)
        File(uiDir, "synthetic_2.json").writeText(pathsToJson(Letters.ㅢ.character, uiConnected))
        ImageIO.write(renderDrawing(Letters.ㅢ, uiConnected), "PNG", File(uiDir, "synthetic_2.png"))

        println("Generated $count letters in ${baseDir.absolutePath}")
    }

    @Test
    fun generateOverviewGrid() {
        val allLetters = Letters.getAll()
        val cols = 10
        val rows = (allLetters.size + cols - 1) / cols
        val cellSize = 120
        val padding = 4

        val totalWidth = cols * cellSize + (cols + 1) * padding
        val totalHeight = rows * cellSize + (rows + 1) * padding
        val gridImage = BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_ARGB)
        val g = gridImage.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.color = Color(30, 30, 30)
        g.fillRect(0, 0, totalWidth, totalHeight)

        for ((idx, letter) in allLetters.withIndex()) {
            val col = idx % cols
            val row = idx / cols
            val x = padding + col * (cellSize + padding)
            val y = padding + row * (cellSize + padding)

            val paths = SyntheticDrawings.cleanDrawing(letter, 60)
            if (paths.isEmpty()) continue

            val cellImage = renderDrawing(letter, paths, cellSize)
            g.drawImage(cellImage, x, y, null)
        }

        g.dispose()
        baseDir.mkdirs()
        ImageIO.write(gridImage, "PNG", File(baseDir, "_overview_grid.png"))
        println("Generated overview grid: ${baseDir.absolutePath}/_overview_grid.png")
    }

    // --- JSON serialization (no external dependencies) ---

    private fun pathsToJson(letter: String, paths: List<List<DrawingPoint>>): String {
        val sb = StringBuilder()
        sb.appendLine("{")
        sb.appendLine("  \"letter\": \"$letter\",")
        sb.appendLine("  \"paths\": [")
        for ((strokeIdx, stroke) in paths.withIndex()) {
            sb.append("    [")
            for ((ptIdx, pt) in stroke.withIndex()) {
                sb.append("{\"x\":${formatCoord(pt.x)},\"y\":${formatCoord(pt.y)}}")
                if (ptIdx < stroke.size - 1) sb.append(",")
            }
            sb.append("]")
            if (strokeIdx < paths.size - 1) sb.appendLine(",") else sb.appendLine()
        }
        sb.appendLine("  ]")
        sb.append("}")
        return sb.toString()
    }

    private fun formatCoord(v: Double): String {
        val rounded = Math.round(v * 100.0) / 100.0
        return if (rounded == rounded.toLong().toDouble()) "${rounded.toLong()}.0" else "$rounded"
    }

    // --- PNG rendering ---

    private fun renderDrawing(
        letter: Letter,
        paths: List<List<DrawingPoint>>,
        size: Int = IMAGE_SIZE,
    ): BufferedImage {
        val scale = size / 100.0
        val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
        val g = image.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)

        // Background
        g.color = Color(245, 245, 250)
        g.fillRect(0, 0, size, size)

        // Grid crosshair
        g.color = Color(200, 200, 210)
        g.stroke = BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, floatArrayOf(4f, 4f), 0f)
        g.drawLine(0, size / 2, size, size / 2)
        g.drawLine(size / 2, 0, size / 2, size)

        // Draw stroke lines first, then markers on top
        val strokeWidth = (STROKE_WIDTH * size / IMAGE_SIZE).toFloat()
        for ((strokeIdx, path) in paths.withIndex()) {
            if (path.size < 2) continue
            val color = strokeColors[strokeIdx % strokeColors.size]
            g.color = color
            g.stroke = BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            for (i in 0 until path.size - 1) {
                g.drawLine(
                    (path[i].x * scale).toInt(), (path[i].y * scale).toInt(),
                    (path[i + 1].x * scale).toInt(), (path[i + 1].y * scale).toInt(),
                )
            }
        }

        // Place numbered labels off the stroke lines.
        // For each start point, try many candidates (multiple angles × multiple radii)
        // and pick the one furthest from all strokes and already-placed labels.
        val markerR = MARKER_RADIUS * size / IMAGE_SIZE
        val baseDist = markerR + (7 * size / IMAGE_SIZE)

        // Pre-compute all stroke points in pixel space (subsampled for speed)
        val allStrokePixels = paths.flatMap { path ->
            path.indices.step(3).map { i ->
                (path[i].x * scale).toInt() to (path[i].y * scale).toInt()
            }
        }

        data class MarkerInfo(
            val idx: Int, val startX: Int, val startY: Int,
            val labelX: Int, val labelY: Int, val color: Color,
        )

        fun minDistToStrokes(x: Int, y: Int): Double =
            allStrokePixels.minOf { (px, py) ->
                val dx = (x - px).toDouble()
                val dy = (y - py).toDouble()
                kotlin.math.sqrt(dx * dx + dy * dy)
            }

        val placedLabels = mutableListOf<MarkerInfo>()
        val angleSteps = 16
        val radii = listOf(baseDist, baseDist + markerR * 2)

        // Collect start positions to detect shared start points
        val startPositions = paths.withIndex()
            .filter { (_, p) -> p.size >= 2 }
            .map { (_, p) -> (p.first().x * scale).toInt() to (p.first().y * scale).toInt() }

        for ((idx, path) in paths.withIndex()) {
            if (path.size < 2) continue
            val sx = (path.first().x * scale).toInt()
            val sy = (path.first().y * scale).toInt()
            val color = strokeColors[idx % strokeColors.size]

            // Check if another stroke shares this start point
            val hasConflict = startPositions.withIndex().any { (otherIdx, pos) ->
                otherIdx != idx &&
                    kotlin.math.abs(pos.first - sx) < markerR * 2 &&
                    kotlin.math.abs(pos.second - sy) < markerR * 2
            } || placedLabels.any { other ->
                kotlin.math.abs(other.labelX - sx) < markerR * 2 &&
                    kotlin.math.abs(other.labelY - sy) < markerR * 2
            }

            if (!hasConflict) {
                // No conflict — place label directly on the start point
                placedLabels.add(MarkerInfo(idx, sx, sy, sx, sy, color))
                continue
            }

            // Direction the stroke goes — bias the label toward this side
            val ref = path[minOf(path.size / 4, path.lastIndex).coerceAtLeast(1)]
            val strokeDx = (ref.x * scale) - sx
            val strokeDy = (ref.y * scale) - sy
            val strokeLen = kotlin.math.sqrt(strokeDx * strokeDx + strokeDy * strokeDy).coerceAtLeast(1.0)
            val strokeDirX = strokeDx / strokeLen
            val strokeDirY = strokeDy / strokeLen

            var bestX = sx
            var bestY = sy - baseDist
            var bestScore = -1.0

            for (radius in radii) {
                for (step in 0 until angleSteps) {
                    val angle = step * kotlin.math.PI * 2.0 / angleSteps
                    val candDirX = kotlin.math.cos(angle)
                    val candDirY = kotlin.math.sin(angle)
                    val cx = sx + (candDirX * radius).toInt()
                    val cy = sy + (candDirY * radius).toInt()

                    val strokeDist = minDistToStrokes(cx, cy)
                    val labelClearance = if (placedLabels.isEmpty()) Double.MAX_VALUE
                        else placedLabels.minOf { other ->
                            val dx = (cx - other.labelX).toDouble()
                            val dy = (cy - other.labelY).toDouble()
                            kotlin.math.sqrt(dx * dx + dy * dy)
                        }
                    val clearance = minOf(strokeDist, labelClearance)
                    // Bonus for being on the same side as the stroke direction
                    val dot = candDirX * strokeDirX + candDirY * strokeDirY
                    val directionBonus = (1.0 + dot * 0.5) // range 0.5–1.5
                    val score = clearance * directionBonus
                    if (score > bestScore) {
                        bestScore = score
                        bestX = cx
                        bestY = cy
                    }
                }
            }

            placedLabels.add(MarkerInfo(idx, sx, sy, bestX, bestY, color))
        }

        // Draw start dots and numbered labels
        for (marker in placedLabels) {
            val dotR = markerR / 2
            // Small dot at actual start
            g.color = marker.color
            g.fillOval(marker.startX - dotR, marker.startY - dotR, dotR * 2, dotR * 2)
            // Numbered circle label
            g.color = marker.color
            g.fillOval(marker.labelX - markerR, marker.labelY - markerR, markerR * 2, markerR * 2)
            g.color = Color.WHITE
            g.stroke = BasicStroke(1.5f)
            g.drawOval(marker.labelX - markerR, marker.labelY - markerR, markerR * 2, markerR * 2)
            g.font = Font("SansSerif", Font.BOLD, (11 * size / IMAGE_SIZE).coerceAtLeast(8))
            val text = "${marker.idx + 1}"
            val fm = g.fontMetrics
            g.drawString(text, marker.labelX - fm.stringWidth(text) / 2, marker.labelY + fm.ascent / 2 - 1)
        }

        // Draw end markers (skip if overlapping a label)
        for ((strokeIdx, path) in paths.withIndex()) {
            if (path.size < 2) continue
            val endX = (path.last().x * scale).toInt()
            val endY = (path.last().y * scale).toInt()
            val hitsLabel = placedLabels.any { m ->
                kotlin.math.abs(endX - m.labelX) < markerR &&
                    kotlin.math.abs(endY - m.labelY) < markerR
            }
            if (hitsLabel) continue
            val dotR = markerR / 2
            g.color = strokeColors[strokeIdx % strokeColors.size].darker()
            g.fillOval(endX - dotR, endY - dotR, dotR * 2, dotR * 2)
        }

        // Letter label in corner
        g.color = Color(80, 80, 80)
        g.font = Font("SansSerif", Font.PLAIN, (14 * size / IMAGE_SIZE).coerceAtLeast(10))
        g.drawString("${letter.character} (${letter.name})", 6 * size / IMAGE_SIZE, 16 * size / IMAGE_SIZE)

        g.dispose()
        return image
    }

}
