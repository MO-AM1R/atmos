package com.example.atmos.data.enums

enum class Language(val apiValue: String, val value: String, val arValue: String) {
    ENGLISH(
        apiValue = "en",
        value    = "English",
        arValue  = "الإنجليزية"
    ),
    ARABIC(
        apiValue = "ar",
        value    = "Arabic",
        arValue  = "العربية"
    )
}