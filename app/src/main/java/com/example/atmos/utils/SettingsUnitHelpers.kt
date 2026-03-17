package com.example.atmos.utils

import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import java.util.Locale
import kotlin.math.roundToInt

fun Double.celsiusToFahrenheit(): Double = (this * 9 / 5) + 32

fun Double.celsiusToKelvin(): Double = this + 273.15

fun Double.convertTemperature(unit: TemperatureUnit): Double = when (unit) {
    TemperatureUnit.CELSIUS    -> this
    TemperatureUnit.FAHRENHEIT -> celsiusToFahrenheit()
    TemperatureUnit.KELVIN     -> celsiusToKelvin()
}

fun Double.formatTemperature(unit: TemperatureUnit): String {
    val symbol = if (Locale.getDefault().language == "ar") {
        unit.arSymbol
    } else {
        unit.symbol
    }
    return "${convertTemperature(unit).roundToInt()}$symbol"
}

fun Double.metersPerSecToMilesPerHour(): Double = this * 2.23694

fun Double.convertWindSpeed(unit: WindUnit): Double = when (unit) {
    WindUnit.METERS_PER_SECOND -> this
    WindUnit.MILES_PER_HOUR    -> metersPerSecToMilesPerHour()
}

fun Double.formatWindSpeed(unit: WindUnit): String {
    val symbol = if (Locale.getDefault().language == "ar") {
        unit.arSymbol
    } else {
        unit.symbol
    }
    return "${convertWindSpeed(unit).roundToInt()}$symbol"
}