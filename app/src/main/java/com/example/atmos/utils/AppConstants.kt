package com.example.atmos.utils
import com.example.atmos.BuildConfig


object AppConstants {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val WEATHER_BASE_URL = "data/2.5/"
    const val GEO_BASE_URL = "geo/1.0/"

    const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY
}