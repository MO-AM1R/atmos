package com.example.atmos.domain.repository

import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow


interface UserPreferencesRepository {
    suspend fun hasSeenOnboarding(): Boolean
    suspend fun saveUserPreferences(userPreferences: UserPreferences?)
    suspend fun saveStoredPoint(point: StoredPoint)
    suspend fun saveStoredLocationName(name: String)
    suspend fun getStoredLocationName(): String?
    suspend fun saveLocationOption(option: LocationOption)
    suspend fun saveTemperatureUnit(unit: TemperatureUnit)
    suspend fun saveWindUnit(unit: WindUnit)
    suspend fun saveLanguage(language: Language)
    suspend fun seeOnboarding()
    fun getUserPreferences(): Flow<UserPreferences>
}