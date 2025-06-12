package io.github.haradakunihiko.intellij_plugin_php_serialized_object_viewer

import com.intellij.openapi.ide.CopyPasteManager
import java.awt.*
import java.awt.datatransfer.DataFlavor
import java.time.LocalTime
import javax.swing.*
import javax.swing.border.TitledBorder

class ClipboardMonitorPanel : JPanel(BorderLayout()) {

    private val clipboardContent: JTextArea
    private val statusLabel: JLabel
    private val clipboardTimer: Timer
    private var lastClipboardContent = ""

    init {
        val components = initializeUI()
        statusLabel = components.first
        clipboardContent = components.second
        clipboardTimer = createClipboardTimer()
        startClipboardMonitoring()
    }

    private fun initializeUI(): Pair<JLabel, JTextArea> {
        preferredSize = Dimension(400, 300)

        val mainPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
        }

        // Status label
        val statusLbl = JLabel("Monitoring clipboard...").apply {
            border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
        }
        mainPanel.add(statusLbl)

        // Clipboard content display area
        val clipboardTextArea = JTextArea(15, 30).apply {
            isEditable = false
            lineWrap = true
            wrapStyleWord = true
            font = Font(Font.MONOSPACED, Font.PLAIN, 11)
        }

        val clipboardScrollPane = JScrollPane(clipboardTextArea).apply {
            border = TitledBorder("Clipboard Content")
        }
        mainPanel.add(clipboardScrollPane)

        add(mainPanel, BorderLayout.CENTER)

        return Pair(statusLbl, clipboardTextArea)
    }

    private fun createClipboardTimer(): Timer {
        return Timer(1000) { checkClipboard() }
    }

    private fun startClipboardMonitoring() {
        clipboardTimer.start()
    }

    private fun checkClipboard() {
        SwingUtilities.invokeLater {
            try {
                val manager = CopyPasteManager.getInstance()
                val contents = manager.contents

                if (contents?.isDataFlavorSupported(DataFlavor.stringFlavor) == true) {
                    val currentContent = contents.getTransferData(DataFlavor.stringFlavor) as String

                    if (currentContent != lastClipboardContent) {
                        updateClipboardDisplay(currentContent)
                    }
                } else {
                    statusLabel.apply {
                        text = "No text data in clipboard"
                        foreground = Color.GRAY
                    }
                }
            } catch (ex: Exception) {
                statusLabel.apply {
                    text = "Error: ${ex.message}"
                    foreground = Color.RED
                }
            }
        }
    }

    private fun updateClipboardDisplay(content: String) {
        // Try PHP to JSON conversion
        val processedContent = PhpToJsonConverter.convert(content)

        // Update only if changed from last time
        if (processedContent != lastClipboardContent) {
            if (processedContent != content) {
                statusLabel.apply {
                    text = "PHP serialized data detected and converted (${getCurrentTime()})"
                    foreground = Color.GREEN
                }
                clipboardContent.apply {
                    text = processedContent
                    caretPosition = 0
                }
                lastClipboardContent = processedContent
            }
        }
    }

    private fun getCurrentTime(): String {
        return LocalTime.now().toString().substring(0, 8)
    }

    // Stop timer when panel is closed
    fun dispose() {
        if (clipboardTimer.isRunning) {
            clipboardTimer.stop()
        }
    }
}
