package com.example.atmos.data.repository

import com.example.atmos.data.datasource.local.UserPreferencesDataStore
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.domain.repository.UserPreferencesRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow


@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferencesDataStore
) : UserPreferencesRepository {

    override suspend fun hasSeenOnboarding(): Boolean {
        return dataStore.hasSeenOnboarding()
    }

    override suspend fun saveUserPreferences(userPreferences: UserPreferences?) =
        dataStore.saveUserPreferences(userPreferences)

    override suspend fun saveStoredPoint(point: StoredPoint) =
        dataStore.saveStoredPoint(point)

    override suspend fun saveStoredLocationName(name: String) =
        dataStore.saveStoredLocationName(name)

    override suspend fun getStoredLocationName(): String? =
        dataStore.getStoredLocationName()

    override suspend fun saveLocationOption(option: LocationOption) =
        dataStore.saveLocationOption(option)

    override suspend fun saveTemperatureUnit(unit: TemperatureUnit) =
        dataStore.saveTemperatureUnit(unit)

    override suspend fun saveWindUnit(unit: WindUnit) =
        dataStore.saveWindUnit(unit)

    override suspend fun saveLanguage(language: Language) =
        dataStore.saveLanguage(language)

    override suspend fun seeOnboarding() =
        dataStore.seeOnboarding()

    override fun getUserPreferences(): Flow<UserPreferences> {
        return dataStore.getUserPreferences()
    }
}