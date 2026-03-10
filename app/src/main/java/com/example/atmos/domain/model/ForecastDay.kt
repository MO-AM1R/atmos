package com.example.atmos.domain.model

data class ForecastDay(
    val cityName          : String,
    val countryCode       : String,
    val latitude          : Double,
    val longitude         : Double,
    val timezoneOffset    : Int,
    val hourlyForecasts   : List<HourlyForecast>
)