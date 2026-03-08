package com.example.atmos.data.enums

enum class TemperatureUnit(val apiValue: String, val symbol: String) {
    CELSIUS(apiValue = "metric",   symbol = "°C"),
    FAHRENHEIT(apiValue = "imperial", symbol = "°F"),
    KELVIN(apiValue = "standard", symbol = "K")
}