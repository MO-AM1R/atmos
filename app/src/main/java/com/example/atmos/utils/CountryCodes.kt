package com.example.atmos.utils
import java.util.Locale

fun String.toLocalizedCountryName(locale: Locale = Locale.getDefault()): String {
    if (this.isBlank()) return this
    return runCatching {
        Locale.Builder()
            .setRegion(this)
            .build()
            .getDisplayCountry(locale)
            .takeIf { it.isNotBlank() }
    }.getOrNull() ?: this
}

fun String.toFlagEmoji(): String {
    if (this.length != 2) return ""
    val firstChar = Character.codePointAt(this.uppercase(), 0) - 0x41 + 0x1F1E6
    val secondChar = Character.codePointAt(this.uppercase(), 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
}

fun String.toLocalizedCountryNameWithFlag(locale: Locale = Locale.getDefault()): String {
    val flag = toFlagEmoji()
    val name = toLocalizedCountryName(locale)
    return if (flag.isNotEmpty()) "$name $flag" else name
}

fun String.isValidCountryCode(): Boolean {
    if (this.length != 2) return false
    return runCatching {
        val resolved = Locale.Builder()
            .setRegion(this)
            .build()
            .getDisplayCountry(Locale.ENGLISH)
        resolved.isNotBlank() && resolved.uppercase() != this.uppercase()
    }.getOrDefault(false)
}