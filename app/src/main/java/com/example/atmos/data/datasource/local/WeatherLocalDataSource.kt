package com.example.atmos.data.datasource.local

import com.example.atmos.data.database.entity.CurrentWeatherEntity
import com.example.atmos.data.database.entity.HourlyForecastEntity
import kotlinx.coroutines.flow.Flow


interface WeatherLocalDataSource {
    suspend fun cacheCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)
    suspend fun cacheForecast(hourlyForecasts: List<HourlyForecastEntity>)

    fun getCachedWeather(): Flow<CurrentWeatherEntity?>
    fun getCachedForecasts(): Flow<List<HourlyForecastEntity>>

    fun getCachedTime(): Flow<Long?>
    fun isCacheValid(): Flow<Boolean>

    suspend fun clearAllCache()
}