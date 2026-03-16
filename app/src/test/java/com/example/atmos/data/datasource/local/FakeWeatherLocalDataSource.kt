package com.example.atmos.data.datasource.local

import com.example.atmos.data.database.entity.CurrentWeatherEntity
import com.example.atmos.data.database.entity.HourlyForecastEntity
import kotlinx.coroutines.flow.Flow

interface FakeWeatherLocalDataSource: WeatherLocalDataSource {
    override suspend fun cacheCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)
    override suspend fun cacheForecast(hourlyForecasts: List<HourlyForecastEntity>)
    override fun getCachedWeather() : Flow<CurrentWeatherEntity?>
    override fun getCachedForecasts(): Flow<List<HourlyForecastEntity>>
    override fun getCachedTime(): Flow<Long?>
    override fun isCacheValid(): Flow<Boolean>
    override suspend fun clearAllCache()
}