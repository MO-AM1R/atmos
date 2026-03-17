package com.example.atmos.utils

import androidx.compose.runtime.Composable
import com.example.atmos.R
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
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


fun String?.nullableToWeatherIconRes(): Int {
    if (this == null) return R.drawable.ic_weather_cloudy

    val isDay = this.endsWith('d')

    return when (this.take(2)) {
        "01" -> if (isDay) R.drawable.ic_weather_sunny else R.drawable.ic_weather_night
        "02" -> if (isDay) R.drawable.ic_weather_partly_cloudy else R.drawable.ic_weather_night
        "03",
        "04" -> R.drawable.ic_weather_cloudy

        "09",
        "10" -> R.drawable.ic_weather_rainy

        "11" -> R.drawable.ic_weather_thunder
        "13" -> R.drawable.ic_weather_snowy
        "50" -> R.drawable.ic_weather_foggy
        else -> R.drawable.ic_weather_cloudy
    }
}

fun String.toWeatherIconRes(): Int {
    val isDay = this.endsWith('d')

    return when (this.take(2)) {
        "01" -> if (isDay) R.drawable.ic_weather_sunny else R.drawable.ic_weather_night
        "02" -> if (isDay) R.drawable.ic_weather_partly_cloudy else R.drawable.ic_weather_night
        "03",
        "04" -> R.drawable.ic_weather_cloudy

        "09",
        "10" -> R.drawable.ic_weather_rainy

        "11" -> R.drawable.ic_weather_thunder
        "13" -> R.drawable.ic_weather_snowy
        "50" -> R.drawable.ic_weather_foggy
        else -> R.drawable.ic_weather_cloudy
    }
}
