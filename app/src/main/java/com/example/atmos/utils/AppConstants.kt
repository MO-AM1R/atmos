package com.example.atmos.utils
import com.example.atmos.BuildConfig


object AppConstants {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val ICONS_BASE_URL = "https://openweathermap.org/payload/api/media/file/"
    const val WEATHER_BASE_URL = "data/2.5/"
    const val GEO_BASE_URL = "geo/1.0/"

    const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY
    const val MAP_PUBLIC_API_KEY = BuildConfig.MAP_PUBLIC_API_KEY

    const val CACHE_DURATION_MS = 120 * 60 * 1000L

    object SharedPreferences {
        const val NAME = "atmos_shared_preference"

        object Keys{
            const val LOCATION_OPTION_KEY = "location_option"
            const val LANGUAGE_OPTION_KEY = "language_option"
            const val TEMPERATURE_UNIT_OPTION_KEY = "temperature_unit_option"
            const val WIND_UNIT_OPTION_KEY = "wind_unit_option"
            const val ONBOARDING_SEEN_BEFORE = "onboarding_seen_before"
        }
    }

    object MapConstants {
        const val DEFAULT_ZOOM      = 14.0
        const val DEFAULT_LATITUDE  = 30.017209732769217
        const val DEFAULT_LONGITUDE = 31.179706931405747
        const val SEARCH_API_KEY    = BuildConfig.MAP_PRIVATE_API_KEY
        const val MARKER_SIZE       = 1.5
        const val CURRENT_LOC_SIZE  = 0.8
        const val SELECTED_POINT_KEY  = "selected_point"
    }
}