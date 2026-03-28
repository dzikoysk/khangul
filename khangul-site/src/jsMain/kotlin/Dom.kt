package com.dzikoysk.khangul.site

import experimental.recognition.DrawingPoint
import kotlinx.browser.document
import org.w3c.dom.*
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag

// -- Element builders --

fun Node.appendDiv(className: String = ""): HTMLDivElement =
    (document.createElement("div") as HTMLDivElement).also {
        if (className.isNotEmpty()) it.className = className
        appendChild(it)
    }

fun Node.appendSpan(className: String = ""): HTMLSpanElement =
    (document.createElement("span") as HTMLSpanElement).also {
        if (className.isNotEmpty()) it.className = className
        appendChild(it)
    }

fun Node.appendCanvas(width: Int, height: Int, className: String = ""): HTMLCanvasElement =
    (document.createElement("canvas") as HTMLCanvasElement).also {
        it.width = width
        it.height = height
        if (className.isNotEmpty()) it.className = className
        appendChild(it)
    }

fun Node.appendButton(text: String): HTMLButtonElement =
    (document.createElement("button") as HTMLButtonElement).also {
        it.textContent = text
        appendChild(it)
    }

fun Node.appendSelect(): HTMLSelectElement =
    (document.createElement("select") as HTMLSelectElement).also { appendChild(it) }

fun Node.appendInput(type: String): HTMLInputElement =
    (document.createElement("input") as HTMLInputElement).also {
        it.type = type
        appendChild(it)
    }

fun Node.appendLabel(text: String): HTMLLabelElement =
    (document.createElement("label") as HTMLLabelElement).also {
        it.textContent = text
        appendChild(it)
    }

fun Node.appendOption(value: String, text: String): HTMLOptionElement =
    (document.createElement("option") as HTMLOptionElement).also {
        it.value = value
        it.textContent = text
        appendChild(it)
    }

// -- Canvas context helpers (avoid asDynamic() noise at call sites) --

fun CanvasRenderingContext2D.roundStroke() {
    asDynamic().lineCap = "round"
    asDynamic().lineJoin = "round"
}

fun CanvasRenderingContext2D.setFont(font: String, align: String = "start", baseline: String = "alphabetic") {
    asDynamic().font = font
    asDynamic().textAlign = align
    asDynamic().textBaseline = baseline
}

fun CanvasRenderingContext2D.drawPath(points: List<DrawingPoint>, scale: Double) {
    if (points.size < 2) return
    beginPath()
    moveTo(points[0].x * scale, points[0].y * scale)
    for (i in 1 until points.size) lineTo(points[i].x * scale, points[i].y * scale)
    stroke()
}

// -- JS interop --

@JsName("URL")
private external object JsURL {
    fun createObjectURL(blob: Blob): String
    fun revokeObjectURL(url: String)
}

fun createBlob(content: String, type: String): Blob {
    val options: dynamic = js("({})")
    options.type = type
    return Blob(arrayOf(content).unsafeCast<Array<dynamic>>(), options.unsafeCast<BlobPropertyBag>())
}

fun downloadBlob(blob: Blob, filename: String) {
    val a = document.createElement("a") as HTMLAnchorElement
    val url = JsURL.createObjectURL(blob)
    a.href = url
    a.download = filename
    a.click()
    JsURL.revokeObjectURL(url)
}
