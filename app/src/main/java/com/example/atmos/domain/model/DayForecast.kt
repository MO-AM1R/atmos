package com.example.atmos.domain.model

data class DayForecast(
    val dayName          : String,
    val dateLabel        : String,
    val minTemp          : Double,
    val maxTemp          : Double,
    val representativeIcon: String,
    val timestampUnix    : Long
)