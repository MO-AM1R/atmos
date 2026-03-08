package com.example.atmos.utils
import com.example.atmos.BuildConfig


object AppConstants {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val WEATHER_BASE_URL = "data/2.5/"
    const val GEO_BASE_URL = "geo/1.0/"

    const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY

    object SharedPreferences {
        const val NAME = "atmos_shared_preference"

        object Keys{
            const val LOCATION_OPTION_KEY = "location_option"
            const val LANGUAGE_OPTION_KEY = "language_option"
            const val TEMPERATURE_UNIT_OPTION_KEY = "temperature_unit_option"
            const val WIND_UNIT_OPTION_KEY = "wind_unit_option"
        }
    }
}