package com.example.atmos.domain.model

data class CurrentWeather(
    val cityName          : String,
    val countryCode       : String,
    val latitude          : Double,
    val longitude         : Double,
    val temperature       : Double,
    val feelsLike         : Double,
    val minimumTemp       : Double,
    val maximumTemp       : Double,
    val pressureHpa       : Int,
    val humidityPercent   : Int,
    val windSpeedRaw      : Double,
    val windDirectionDeg  : Int,
    val cloudCoverPercent : Int,
    val visibilityMeters  : Int,
    val weatherConditionId: Int,
    val weatherMain       : String,
    val weatherDescription: String,
    val weatherIconCode   : String,
    val sunriseUnix       : Long,
    val sunsetUnix        : Long,
    val timezoneOffset    : Int,
    val timestampUnix     : Long
)