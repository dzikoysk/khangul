package com.dzikoysk.khangul.site

import experimental.recognition.HangulRecognizer
import experimental.recognition.RecognitionResult
import kotlinx.browser.document
import org.w3c.dom.*

fun main() {
    document.addEventListener("DOMContentLoaded", {
        KhangulApp().start()
    })
}

class KhangulApp {
    private val recognizer = HangulRecognizer()
    private val allLetters = recognizer.getAllLetters()

    private lateinit var drawingCanvas: DrawingCanvas
    private lateinit var strokeInfo: HTMLElement
    private lateinit var resultList: HTMLElement
    private lateinit var undoBtn: HTMLButtonElement
    private lateinit var letterSelect: HTMLSelectElement
    private lateinit var variantInput: HTMLInputElement
    private lateinit var downloadBtns: List<HTMLButtonElement>
    private lateinit var resultsTab: HTMLElement
    private lateinit var downloadTab: HTMLElement

    fun start() {
        val body = document.body ?: return
        buildUI(body)
        updateControls()
    }

    // -- UI construction --

    private fun buildUI(body: HTMLElement) {
        (document.createElement("h1") as HTMLElement).also {
            it.textContent = "Khangul Drawing Tool"
            body.appendChild(it)
        }

        val container = body.appendDiv("container")
        buildCanvasPanel(container.appendDiv("canvas-panel"))
        buildResultsPanel(container.appendDiv("results-panel"))
    }

    private fun buildCanvasPanel(panel: HTMLElement) {
        val wrapper = panel.appendDiv("canvas-wrapper")
        drawingCanvas = DrawingCanvas(wrapper, recognizer)
        drawingCanvas.onStrokeChanged = ::onStrokeChanged

        strokeInfo = panel.appendDiv("stroke-info").apply {
            textContent = "Draw a Hangul letter"
        }

        val controls = panel.appendDiv("controls")
        undoBtn = controls.appendButton("Undo Stroke").apply {
            disabled = true
            addEventListener("click", { drawingCanvas.undo() })
        }
        controls.appendButton("Clear").apply {
            addEventListener("click", { drawingCanvas.clear() })
        }
    }

    private fun buildResultsPanel(panel: HTMLElement) {
        // Tabs
        val tabBar = panel.appendDiv("tab-bar")
        val resultsTabBtn = tabBar.appendDiv("tab active").apply { textContent = "Results" }
        val downloadTabBtn = tabBar.appendDiv("tab").apply { textContent = "Download" }

        resultsTab = panel.appendDiv()
        resultList = resultsTab.appendDiv("result-list").apply {
            textContent = "Results will appear here after drawing"
        }

        downloadTab = panel.appendDiv().apply { style.display = "none" }
        buildDownloadSection(downloadTab.appendDiv("download-section"))

        // Tab switching
        val tabs = listOf(resultsTabBtn to resultsTab, downloadTabBtn to downloadTab)
        for ((btn, content) in tabs) {
            btn.addEventListener("click", {
                tabs.forEach { (b, c) ->
                    b.classList.remove("active")
                    c.style.display = "none"
                }
                btn.classList.add("active")
                content.style.display = ""
            })
        }
    }

    private fun buildDownloadSection(section: HTMLElement) {
        val row1 = section.appendDiv("download-row")
        row1.appendLabel("Letter:")
        letterSelect = row1.appendSelect()
        for (letter in allLetters) {
            letterSelect.appendOption(letter.character, "${letter.character} (${letter.name})")
        }

        val row2 = section.appendDiv("download-row")
        row2.appendLabel("Variant:")
        variantInput = row2.appendInput("number").apply { value = "1"; min = "1" }

        val row3 = section.appendDiv("download-row")
        val jsonBtn = row3.appendButton("Download JSON").apply {
            disabled = true
            addEventListener("click", { downloadJson() })
        }
        val pngBtn = row3.appendButton("Download PNG").apply {
            disabled = true
            addEventListener("click", { downloadPng() })
        }
        val bothBtn = row3.appendButton("Download Both").apply {
            disabled = true
            addEventListener("click", { downloadJson(); downloadPng() })
        }
        downloadBtns = listOf(jsonBtn, pngBtn, bothBtn)

        section.appendDiv("download-hint").apply {
            innerHTML = """Files save as <code>{letter}/captured_{n}.json</code> and <code>.png</code>"""
        }
    }

    // -- Event handling --

    private fun onStrokeChanged() {
        if (drawingCanvas.hasStrokes) {
            runRecognition()
        } else {
            resultList.innerHTML = ""
            resultList.textContent = "Results will appear here after drawing"
            strokeInfo.textContent = "Draw a Hangul letter"
            drawingCanvas.clearGuide()
        }
        updateControls()
    }

    private fun updateControls() {
        val has = drawingCanvas.hasStrokes
        undoBtn.disabled = !has
        downloadBtns.forEach { it.disabled = !has }
    }

    // -- Recognition --

    private fun runRecognition() {
        val results = recognizer.recognize(drawingCanvas.paths)

        val count = drawingCanvas.paths.size
        strokeInfo.textContent = "$count stroke${if (count != 1) "s" else ""} drawn"

        resultList.innerHTML = ""
        for ((i, result) in results.take(10).withIndex()) {
            resultList.appendChild(createResultItem(result, isTop = i == 0))
        }

        val best = results.firstOrNull()
        if (best != null && best.coverage >= GUIDE_THRESHOLD) {
            drawingCanvas.showGuide(best.letter)
            letterSelect.value = best.letter.character
        }
    }

    private fun createResultItem(result: RecognitionResult, isTop: Boolean): HTMLElement {
        val cov = result.coverage
        val isGood = cov >= recognizer.matchThreshold

        return (document.createElement("div") as HTMLDivElement).apply {
            className = if (isTop) "result-item top" else "result-item"

            appendSpan("result-char").apply { textContent = result.letter.character }

            appendDiv("result-name").apply {
                innerHTML = "${result.letter.name}<br><small>${result.letter.romanization.joinToString("/")}</small>"
            }

            appendDiv("bar-bg").apply {
                appendDiv("bar-fill").apply {
                    style.width = "${cov}%"
                    style.background = if (isGood) "#4a8a4a" else "#8a4a4a"
                }
            }

            appendSpan("result-score").apply {
                textContent = "${cov}%"
                style.color = if (isGood) "#6f6" else "#f66"
            }
        }
    }

    // -- Downloads --

    private fun selectedLetter(): String = letterSelect.value
    private fun selectedVariant(): String = variantInput.value.ifEmpty { "1" }
    private fun exportFilename(ext: String): String = "${selectedLetter()}_captured_${selectedVariant()}.$ext"

    private fun downloadJson() {
        val json = TestdataExport.toJson(selectedLetter(), drawingCanvas.paths)
        downloadBlob(createBlob(json, "application/json"), exportFilename("json"))
    }

    private fun downloadPng() {
        TestdataExport.renderPng(selectedLetter(), drawingCanvas.paths).toBlob(
            { blob -> if (blob != null) downloadBlob(blob, exportFilename("png")) },
            "image/png"
        )
    }

    companion object {
        private const val GUIDE_THRESHOLD = 20
    }
}
