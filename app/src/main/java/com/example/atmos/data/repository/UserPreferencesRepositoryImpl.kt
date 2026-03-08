package com.example.atmos.data.repository
import com.example.atmos.data.datasource.local.UserPreferencesLocalDataSource
import com.example.atmos.data.dto.UserPreferencesDto
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.userpreferences.UserPreferencesRepository
import jakarta.inject.Inject


class UserPreferencesRepositoryImpl @Inject constructor(
    val localDataSource: UserPreferencesLocalDataSource
): UserPreferencesRepository {
    override fun saveUserPreferences(userPreferencesDto: UserPreferencesDto?) {
        localDataSource.saveUserPreferences(userPreferencesDto ?: UserPreferencesDto())
    }

    override fun updateOption(languageOption: Language) {
        localDataSource.updateOption(languageOption)
    }

    override fun updateOption(locationOption: LocationOption) {
        localDataSource.updateOption(locationOption)
    }

    override fun updateOption(windUnitOption: WindUnit) {
        localDataSource.updateOption(windUnitOption)
    }

    override fun updateOption(temperatureUnitOption: TemperatureUnit) {
        localDataSource.updateOption(temperatureUnitOption)
    }

    override fun seeOnboarding() {
        localDataSource.seeOnboarding()
    }
    
    override fun isOnboardingSeenBefore(): Boolean {
        return localDataSource.isOnboardingSeenBefore()
    }
}