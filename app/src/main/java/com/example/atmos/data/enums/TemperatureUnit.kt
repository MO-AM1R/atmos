package com.example.atmos.data.enums

enum class TemperatureUnit(val apiValue: String, val symbol: String, val arSymbol: String) {
    CELSIUS(
        apiValue = "metric",
        symbol   = "°C",
        arSymbol = "م°"
    ),
    FAHRENHEIT(
        apiValue = "imperial",
        symbol   = "°F",
        arSymbol = "ف°"
    ),
    KELVIN(
        apiValue = "standard",
        symbol   = "K",
        arSymbol = "ك"
    )
}