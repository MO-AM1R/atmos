package com.example.atmos.domain.model

data class FavoriteWeatherItem(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val cityName: String = "",
    val weatherMain: String = "",
    val weatherIcon: String = "",
    val temperature: Double = 0.0,
    val minTemp: Double = 0.0,
    val maxTemp: Double = 0.0,
    val isLoading: Boolean = true,
    val error: String? = null
)