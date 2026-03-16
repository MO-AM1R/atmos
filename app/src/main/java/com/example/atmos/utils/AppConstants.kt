package com.example.atmos.utils
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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
            val LOCATION_OPTION        = stringPreferencesKey("location_option")
            val LATITUDE               = doublePreferencesKey("latitude")
            val LONGITUDE              = doublePreferencesKey("longitude")
            val STORED_LOCATION_NAME   = stringPreferencesKey("stored_location_name")
            val TEMPERATURE_UNIT       = stringPreferencesKey("temperature_unit")
            val WIND_UNIT              = stringPreferencesKey("wind_unit")
            val LANGUAGE               = stringPreferencesKey("language")
            val HAS_SEEN_ONBOARDING    = booleanPreferencesKey("has_seen_onboarding")
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

    object AlertConstants {
        const val EXTRA_ALERT_ID   = "alert_id"
        const val EXTRA_ALERT_TYPE = "alert_type"
        const val EXTRA_IS_END     = "is_end"
        const val NOTIFICATION_ID  = 1001
        const val ALARM_NOTIFICATION_ID = 1002
    }
}