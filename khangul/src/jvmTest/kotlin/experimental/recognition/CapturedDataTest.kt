package experimental.recognition

import Letters
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests recognition against real captured (hand-drawn) data from testdata/.
 * Each captured JSON file should be recognized as the letter in its parent directory.
 */
class CapturedDataTest {

    private val recognizer = HangulRecognizer()
    private val baseDir = File("testdata")

    @Test
    fun `captured drawings are recognized correctly`() {
        val letters = Letters.getAll()
        var tested = 0

        for (letter in letters) {
            val letterDir = File(baseDir, letter.character)
            if (!letterDir.exists()) continue

            val capturedFiles = letterDir.listFiles { f -> f.name.startsWith("captured_") && f.extension == "json" }
                ?.sortedBy { it.name }
                ?: continue

            for (file in capturedFiles) {
                val paths = parseJsonPaths(file.readText())
                assertTrue(paths.isNotEmpty(), "Empty paths in ${file.path}")

                val results = recognizer.recognize(paths)
                assertTrue(results.isNotEmpty(), "No results for ${file.path}")

                assertEquals(
                    letter, results.first().letter,
                    "${file.name}: Expected ${letter.character} but got ${results.first().letter.character} " +
                        "(coverage: ${results.first().coverage}%). " +
                        "Top 3: ${results.take(3).map { "${it.letter.character}:${it.coverage}%" }}"
                )
                tested++
            }
        }

        assertTrue(tested > 0, "No captured test data found")
    }

    private fun parseJsonPaths(json: String): List<List<DrawingPoint>> {
        val paths = mutableListOf<List<DrawingPoint>>()
        // Parse the "paths" array from the JSON
        val pathsStart = json.indexOf("\"paths\"")
        if (pathsStart == -1) return paths

        var depth = 0
        var inPath = false
        var currentPath = mutableListOf<DrawingPoint>()
        var i = json.indexOf('[', pathsStart)

        while (i < json.length) {
            when (json[i]) {
                '[' -> {
                    depth++
                    if (depth == 2) {
                        inPath = true
                        currentPath = mutableListOf()
                    }
                }
                ']' -> {
                    if (depth == 2 && inPath) {
                        if (currentPath.isNotEmpty()) paths.add(currentPath)
                        inPath = false
                    }
                    depth--
                    if (depth == 0) break
                }
                '{' -> {
                    if (inPath) {
                        val objEnd = json.indexOf('}', i)
                        val obj = json.substring(i, objEnd + 1)
                        val x = Regex(""""x":\s*([0-9.]+)""").find(obj)?.groupValues?.get(1)?.toDouble()
                        val y = Regex(""""y":\s*([0-9.]+)""").find(obj)?.groupValues?.get(1)?.toDouble()
                        if (x != null && y != null) currentPath.add(DrawingPoint(x, y))
                        i = objEnd
                    }
                }
            }
            i++
        }
        return paths
    }
}
