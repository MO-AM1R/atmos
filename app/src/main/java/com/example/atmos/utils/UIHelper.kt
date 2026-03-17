package com.example.atmos.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import java.text.NumberFormat
import java.util.Locale


@Composable
fun TemperatureUnit.localizedSymbol(): String {
    return if (Locale.getDefault().language == "ar") arSymbol else symbol
}

@Composable
fun WindUnit.localizedSymbol(): String {
    return if (Locale.getDefault().language == "ar") arSymbol else symbol
}

@Composable
fun Language.localizedValue(): String {
    return if (Locale.getDefault().language == "ar") arValue else value
}

fun Number.toLocalizedDigits(locale: Locale = Locale.getDefault()): String {
    return NumberFormat.getInstance(locale).apply {
        isGroupingUsed        = false
        maximumFractionDigits = 1
    }.format(this)
}

fun String.toLocalizedDigits(locale: Locale = Locale.getDefault()): String {
    if (locale.language != "ar") return this

    return this.map { char ->
        when (char) {
            '0' -> '٠'
            '1' -> '١'
            '2' -> '٢'
            '3' -> '٣'
            '4' -> '٤'
            '5' -> '٥'
            '6' -> '٦'
            '7' -> '٧'
            '8' -> '٨'
            '9' -> '٩'
            '.' -> '٫'
            else -> char
        }
    }.joinToString("")
}