package com.dzikoysk.khangul.site

import Letter
import experimental.recognition.DrawingPoint
import experimental.recognition.HangulRecognizer
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Node
import org.w3c.dom.pointerevents.PointerEvent

class DrawingCanvas(
    parent: Node,
    private val recognizer: HangulRecognizer,
    private val size: Int = 400,
) {
    private val scale = size / 100.0

    val element: HTMLCanvasElement = parent.appendCanvas(size, size)
    private val ctx = element.getContext("2d") as CanvasRenderingContext2D

    private val guideOverlay: HTMLCanvasElement = parent.appendCanvas(size, size, "guide-overlay")
    private val guideCtx = guideOverlay.getContext("2d") as CanvasRenderingContext2D

    private val _paths = mutableListOf<MutableList<DrawingPoint>>()
    val paths: List<List<DrawingPoint>> get() = _paths
    val hasStrokes: Boolean get() = _paths.isNotEmpty()

    private var currentPath: MutableList<DrawingPoint>? = null
    private var isDrawing = false

    var onStrokeChanged: () -> Unit = {}

    init {
        bindPointerEvents()
        drawGrid()
    }

    fun undo() {
        if (_paths.isEmpty()) return
        _paths.removeAt(_paths.lastIndex)
        redraw()
        onStrokeChanged()
    }

    fun clear() {
        _paths.clear()
        currentPath = null
        clearGuide()
        redraw()
        onStrokeChanged()
    }

    fun showGuide(letter: Letter) {
        val sz = size.toDouble()
        guideCtx.clearRect(0.0, 0.0, sz, sz)
        guideCtx.globalAlpha = 0.2
        guideCtx.strokeStyle = "rgba(160,160,180,0.5)"
        guideCtx.lineWidth = 6.0
        guideCtx.roundStroke()
        for (stroke in recognizer.getReferenceStrokes(letter)) {
            guideCtx.drawPath(recognizer.sampleReferenceStroke(stroke, 40).toList(), scale)
        }
        guideCtx.globalAlpha = 1.0
    }

    fun clearGuide() {
        guideCtx.clearRect(0.0, 0.0, size.toDouble(), size.toDouble())
    }

    private fun drawGrid() {
        val sz = size.toDouble()
        ctx.fillStyle = "#1a1a2e"
        ctx.fillRect(0.0, 0.0, sz, sz)
        ctx.strokeStyle = "rgba(128,128,128,0.08)"
        ctx.setLineDash(arrayOf(4.0, 4.0))
        ctx.lineWidth = 1.0
        ctx.beginPath()
        ctx.moveTo(sz / 2, 0.0)
        ctx.lineTo(sz / 2, sz)
        ctx.moveTo(0.0, sz / 2)
        ctx.lineTo(sz, sz / 2)
        ctx.stroke()
        ctx.setLineDash(emptyArray())
    }

    private fun redraw() {
        drawGrid()
        ctx.strokeStyle = DRAW_COLOR
        ctx.lineWidth = 4.0
        ctx.roundStroke()
        ctx.globalAlpha = 0.9
        for (path in _paths) ctx.drawPath(path, scale)
        currentPath?.let { if (it.size > 1) ctx.drawPath(it, scale) }
        ctx.globalAlpha = 1.0
    }

    private fun bindPointerEvents() {
        element.addEventListener("pointerdown", { e ->
            val pe = e as PointerEvent
            isDrawing = true
            currentPath = mutableListOf(pointerToPoint(pe))
            element.asDynamic().setPointerCapture(pe.pointerId)
        })
        element.addEventListener("pointermove", { e ->
            if (!isDrawing) return@addEventListener
            currentPath?.add(pointerToPoint(e as PointerEvent))
            redraw()
        })
        element.addEventListener("pointerup", { finishStroke() })
        element.addEventListener("pointerleave", { finishStroke() })
    }

    private fun pointerToPoint(e: PointerEvent): DrawingPoint {
        val rect = element.getBoundingClientRect()
        val px = (e.clientX - rect.left) * (size / rect.width)
        val py = (e.clientY - rect.top) * (size / rect.height)
        return DrawingPoint(px / scale, py / scale)
    }

    private fun finishStroke() {
        if (!isDrawing) return
        isDrawing = false
        val path = currentPath
        currentPath = null
        if (path != null && path.size >= 2) {
            _paths.add(path)
            redraw()
            onStrokeChanged()
        }
    }

    companion object {
        private const val DRAW_COLOR = "#6c63ff"
    }
}
