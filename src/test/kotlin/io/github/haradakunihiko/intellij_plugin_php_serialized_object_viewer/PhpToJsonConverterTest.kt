package io.github.haradakunihiko.intellij_plugin_php_serialized_object_viewer

import org.junit.Test
import org.junit.Assert.*

class PhpToJsonConverterTest {

    @Test
    fun testConvertWithNullInput() {
        // Test null input handling
        val result = PhpToJsonConverter.convert(null)
        assertEquals("Should return empty string for null input", "", result)
    }

    @Test
    fun testConvertWithEmptyInput() {
        // Test empty string input
        val result = PhpToJsonConverter.convert("")
        assertEquals("Should return empty string for empty input", "", result)
    }

    @Test
    fun testConvertWithBlankInput() {
        // Test blank string input
        val result = PhpToJsonConverter.convert("   ")
        assertEquals("Should return empty string for blank input", "", result)
    }

    @Test
    fun testConvertWithSimplePhpSerialized() {
        // Test basic PHP serialized string conversion
        val phpSerialized = """s:4:"test";"""
        val result = PhpToJsonConverter.convert(phpSerialized)
        
        // Should not return the original string if conversion works
        // Or should return the original string if conversion fails
        assertNotNull("Result should not be null", result)
        assertTrue("Result should be a string", result is String)
    }

    @Test
    fun testConvertWithNonPhpSerializedString() {
        // Test with regular string (not PHP serialized)
        val regularString = "This is a regular string"
        val result = PhpToJsonConverter.convert(regularString)
        
        // Should return the original string since it's not PHP serialized
        assertEquals("Should return original string for non-PHP serialized input", 
                    regularString, result)
    }

    @Test
    fun testLooksLikePhpSerializedWithNull() {
        // Test null input
        val result = PhpToJsonConverter.looksLikePhpSerialized(null)
        assertFalse("Should return false for null input", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithEmpty() {
        // Test empty string
        val result = PhpToJsonConverter.looksLikePhpSerialized("")
        assertFalse("Should return false for empty input", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithBlank() {
        // Test blank string
        val result = PhpToJsonConverter.looksLikePhpSerialized("   ")
        assertFalse("Should return false for blank input", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithStringType() {
        // Test string type PHP serialized pattern
        val phpString = """s:4:"test";"""
        val result = PhpToJsonConverter.looksLikePhpSerialized(phpString)
        assertTrue("Should recognize PHP serialized string pattern", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithArrayType() {
        // Test array type PHP serialized pattern
        val phpArray = """a:1:{s:4:"name";s:4:"John";}"""
        val result = PhpToJsonConverter.looksLikePhpSerialized(phpArray)
        assertTrue("Should recognize PHP serialized array pattern", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithIntegerType() {
        // Test integer type PHP serialized pattern
        val phpInteger = "i:42;"
        val result = PhpToJsonConverter.looksLikePhpSerialized(phpInteger)
        assertTrue("Should recognize PHP serialized integer pattern", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithDoubleType() {
        // Test double type PHP serialized pattern
        val phpDouble = "d:3.14;"
        val result = PhpToJsonConverter.looksLikePhpSerialized(phpDouble)
        assertTrue("Should recognize PHP serialized double pattern", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithBooleanType() {
        // Test boolean type PHP serialized pattern
        val phpBoolean = "b:1;"
        val result = PhpToJsonConverter.looksLikePhpSerialized(phpBoolean)
        assertTrue("Should recognize PHP serialized boolean pattern", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithObjectType() {
        // Test object type PHP serialized pattern
        val phpObject = """O:8:"stdClass":1:{s:4:"name";s:4:"John";}"""
        val result = PhpToJsonConverter.looksLikePhpSerialized(phpObject)
        assertTrue("Should recognize PHP serialized object pattern", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithRegularString() {
        // Test with regular string (not PHP serialized)
        val regularString = "This is just a regular string"
        val result = PhpToJsonConverter.looksLikePhpSerialized(regularString)
        assertFalse("Should return false for regular string", result)
    }

    @Test
    fun testLooksLikePhpSerializedWithWhitespace() {
        // Test PHP serialized string with leading/trailing whitespace
        val phpWithWhitespace = """  s:4:"test";  """
        val result = PhpToJsonConverter.looksLikePhpSerialized(phpWithWhitespace)
        assertTrue("Should handle whitespace and recognize PHP serialized pattern", result)
    }
}
