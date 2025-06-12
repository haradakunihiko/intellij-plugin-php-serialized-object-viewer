package io.github.haradakunihiko.intellij_plugin_php_serialized_object_viewer

import io.github.haradakunihiko.php_json_deserializer.PhpToJson

object PhpToJsonConverter {

    // Hold PhpToJson instance statically
    private val phpToJsonConverter = PhpToJson()

    /**
     * Convert PHP serialized data to JSON
     * Returns the original string if conversion fails
     *
     * @param phpSerializedData PHP serialized data
     * @return JSON string or original string
     */
    fun convert(phpSerializedData: String?): String {
        if (phpSerializedData.isNullOrBlank()) {
            return ""
        }

        return try {
            // Try PHP to JSON conversion
            phpToJsonConverter.convert(phpSerializedData, true) ?: phpSerializedData
        } catch (e: Exception) {
            // Return original string if conversion fails
            phpSerializedData
        }
    }

    /**
     * Simple check if data looks like PHP serialized data
     *
     * @param data String to check
     * @return true if data looks like PHP serialized data
     */
    fun looksLikePhpSerialized(data: String?): Boolean {
        if (data.isNullOrBlank()) {
            return false
        }

        val trimmed = data.trim()
        // Check typical PHP serialize patterns
        return trimmed.matches(Regex("^[aoisdb]:[0-9]+:.*")) ||
                trimmed.startsWith("a:") ||
                trimmed.startsWith("s:") ||
                trimmed.startsWith("i:") ||
                trimmed.startsWith("d:") ||
                trimmed.startsWith("b:") ||
                trimmed.startsWith("O:")
    }
}
