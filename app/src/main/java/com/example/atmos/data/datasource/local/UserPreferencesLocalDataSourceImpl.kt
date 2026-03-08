package com.example.atmos.data.datasource.local

import android.content.Context
import androidx.core.content.edit
import com.example.atmos.data.dto.UserPreferencesDto
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.utils.AppConstants.SharedPreferences
import com.example.atmos.utils.AppConstants.SharedPreferences.Keys
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale.getDefault
import javax.inject.Inject

class UserPreferencesLocalDataSourceImpl
@Inject constructor(@ApplicationContext context: Context) : UserPreferencesLocalDataSource {

    private val prefs by lazy {
        context.getSharedPreferences(SharedPreferences.NAME, Context.MODE_PRIVATE)
    }

    override fun saveUserPreferences(userPreferencesDto: UserPreferencesDto) {
        prefs.edit(commit = true) {
            this.putString(
                Keys.LANGUAGE_OPTION_KEY,
                userPreferencesDto.languageOption.name.lowercase(getDefault())
            )

            this.putString(
                Keys.LOCATION_OPTION_KEY,
                userPreferencesDto.locationOption.name.lowercase(getDefault())
            )

            this.putString(
                Keys.WIND_UNIT_OPTION_KEY,
                userPreferencesDto.windUnitOption.name.lowercase(getDefault())
            )

            this.putString(
                Keys.TEMPERATURE_UNIT_OPTION_KEY,
                userPreferencesDto.temperatureUnitOption.name.lowercase(getDefault())
            )
        }
    }

    override fun updateOption(languageOption: Language) {
        prefs.edit(commit = true) {
            this.putString(
                Keys.LANGUAGE_OPTION_KEY,
                languageOption.name.lowercase(getDefault())
            )
        }
    }

    override fun updateOption(locationOption: LocationOption) {
        prefs.edit(commit = true) {
            this.putString(
                Keys.LOCATION_OPTION_KEY,
                locationOption.name.lowercase(getDefault())
            )
        }
    }

    override fun updateOption(windUnitOption: WindUnit) {
        prefs.edit(commit = true) {
            this.putString(
                Keys.WIND_UNIT_OPTION_KEY,
                windUnitOption.name.lowercase(getDefault())
            )
        }
    }

    override fun updateOption(temperatureUnitOption: TemperatureUnit) {
        prefs.edit(commit = true) {
            this.putString(
                Keys.TEMPERATURE_UNIT_OPTION_KEY,
                temperatureUnitOption.name.lowercase(getDefault())
            )
        }
    }

    override fun seeOnboarding() {
        prefs.edit(commit = true) {
            this.putBoolean(
                Keys.ONBOARDING_SEEN_BEFORE,
                true
            )
        }
    }
    
    override fun isOnboardingSeenBefore(): Boolean {
        return prefs.contains(Keys.ONBOARDING_SEEN_BEFORE)
    }
}