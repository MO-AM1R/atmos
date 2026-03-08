package com.example.atmos.data.datasource.local
import com.example.atmos.data.dto.UserPreferencesDto
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit

interface UserPreferencesLocalDataSource {
    fun saveUserPreferences(userPreferencesDto: UserPreferencesDto)
    fun updateOption(languageOption: Language)
    fun updateOption(locationOption: LocationOption)
    fun updateOption(windUnitOption: WindUnit)
    fun updateOption(temperatureUnitOption: TemperatureUnit)
    fun isOnboardingSeenBefore(): Boolean
}