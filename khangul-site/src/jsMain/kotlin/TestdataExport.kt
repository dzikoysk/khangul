package com.dzikoysk.khangul.site

import Letters
import experimental.recognition.DrawingPoint
import kotlinx.browser.document
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.PI
import kotlin.math.roundToInt

object TestdataExport {

    private val STROKE_COLORS = arrayOf(
        "#e53935", "#1e88e5", "#43a047", "#fb8c00",
        "#8e24aa", "#00acc1", "#d81b60", "#827717"
    )

    fun toJson(letter: String, paths: List<List<DrawingPoint>>): String = buildString {
        appendLine("{")
        appendLine("""  "letter": "$letter",""")
        appendLine("  \"paths\": [")
        paths.forEachIndexed { i, path ->
            append("    [")
            append(path.joinToString(", ") { pt ->
                val x = (pt.x * 100).roundToInt() / 100.0
                val y = (pt.y * 100).roundToInt() / 100.0
                """{"x": $x, "y": $y}"""
            })
            append("]")
            if (i < paths.lastIndex) appendLine(",") else appendLine()
        }
        appendLine("  ]")
        append("}")
    }

    fun renderPng(letter: String, paths: List<List<DrawingPoint>>): HTMLCanvasElement {
        val canvas = document.createElement("canvas") as HTMLCanvasElement
        canvas.width = PNG_SIZE
        canvas.height = PNG_SIZE
        val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

        drawPngBackground(ctx)
        drawPngStrokes(ctx, paths)
        drawPngLabel(ctx, letter)

        return canvas
    }

    private fun drawPngBackground(ctx: CanvasRenderingContext2D) {
        val sz = PNG_SIZE.toDouble()
        ctx.fillStyle = "#f5f5fa"
        ctx.fillRect(0.0, 0.0, sz, sz)
        ctx.strokeStyle = "#ddd"
        ctx.setLineDash(arrayOf(4.0, 4.0))
        ctx.lineWidth = 1.0
        ctx.beginPath()
        ctx.moveTo(sz / 2, 0.0); ctx.lineTo(sz / 2, sz)
        ctx.moveTo(0.0, sz / 2); ctx.lineTo(sz, sz / 2)
        ctx.stroke()
        ctx.setLineDash(emptyArray())
    }

    private fun drawPngStrokes(ctx: CanvasRenderingContext2D, paths: List<List<DrawingPoint>>) {
        val scale = PNG_SIZE / 100.0

        for ((i, path) in paths.withIndex()) {
            if (path.size < 2) continue
            val color = STROKE_COLORS[i % STROKE_COLORS.size]

            ctx.strokeStyle = color
            ctx.lineWidth = 3.5
            ctx.roundStroke()
            ctx.drawPath(path, scale)

            // Start marker with stroke number
            ctx.fillStyle = color
            ctx.beginPath()
            ctx.arc(path[0].x * scale, path[0].y * scale, 6.0, 0.0, PI * 2)
            ctx.fill()
            ctx.fillStyle = "#fff"
            ctx.setFont("bold 9px sans-serif", align = "center", baseline = "middle")
            ctx.fillText("${i + 1}", path[0].x * scale, path[0].y * scale)

            // End marker
            ctx.fillStyle = color
            ctx.globalAlpha = 0.6
            ctx.beginPath()
            ctx.arc(path.last().x * scale, path.last().y * scale, 3.0, 0.0, PI * 2)
            ctx.fill()
            ctx.globalAlpha = 1.0
        }
    }

    private fun drawPngLabel(ctx: CanvasRenderingContext2D, letter: String) {
        val info = Letters.findByCharacter(letter) ?: return
        ctx.fillStyle = "#333"
        ctx.setFont("bold 16px sans-serif", align = "left", baseline = "bottom")
        ctx.fillText("${info.character} (${info.name})", 8.0, (PNG_SIZE - 6).toDouble())
    }

    private const val PNG_SIZE = 400
}
