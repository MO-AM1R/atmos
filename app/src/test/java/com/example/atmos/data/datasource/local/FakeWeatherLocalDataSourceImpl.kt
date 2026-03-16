package com.example.atmos.data.datasource.local

import com.example.atmos.data.database.entity.CurrentWeatherEntity
import com.example.atmos.data.database.entity.HourlyForecastEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeWeatherLocalDataSourceImpl(
    currentWeather: CurrentWeatherEntity,
    hourlyForecasts: List<HourlyForecastEntity>
) : FakeWeatherLocalDataSource {

    var shouldThrowError = false
    var errorMessage = "Cache error"

    private var cachedWeather: CurrentWeatherEntity? = currentWeather
    private var cachedForecasts: MutableList<HourlyForecastEntity> = hourlyForecasts.toMutableList()
    private var cachedTime: Long? = currentWeather.cachedAtUnix
    private var cacheValid: Boolean = true

    private val weatherFlow   = MutableStateFlow<CurrentWeatherEntity?>(currentWeather)
    private val forecastsFlow = MutableStateFlow(hourlyForecasts)
    private val timeFlow      = MutableStateFlow<Long?>(currentWeather.cachedAtUnix)
    private val cacheValidFlow = MutableStateFlow(true)

    override suspend fun cacheCurrentWeather(currentWeatherEntity: CurrentWeatherEntity) {
        if (shouldThrowError) throw Exception(errorMessage)
        cachedWeather    = currentWeatherEntity
        cachedTime       = System.currentTimeMillis()
        cacheValid       = true
        weatherFlow.value    = currentWeatherEntity
        timeFlow.value       = cachedTime
        cacheValidFlow.value = true
    }

    override suspend fun cacheForecast(hourlyForecasts: List<HourlyForecastEntity>) {
        if (shouldThrowError) throw Exception(errorMessage)
        cachedForecasts.clear()
        cachedForecasts.addAll(hourlyForecasts)
        forecastsFlow.value = cachedForecasts.toList()
    }

    override fun getCachedWeather(): Flow<CurrentWeatherEntity?> {
        if (shouldThrowError) throw Exception(errorMessage)
        return weatherFlow
    }

    override fun getCachedForecasts(): Flow<List<HourlyForecastEntity>> {
        if (shouldThrowError) throw Exception(errorMessage)
        return forecastsFlow
    }

    override fun getCachedTime(): Flow<Long?> {
        if (shouldThrowError) throw Exception(errorMessage)
        return timeFlow
    }

    override fun isCacheValid(): Flow<Boolean> {
        if (shouldThrowError) throw Exception(errorMessage)
        return cacheValidFlow
    }

    override suspend fun clearAllCache() {
        if (shouldThrowError) throw Exception(errorMessage)
        cachedWeather        = null
        cachedTime           = null
        cacheValid           = false
        cachedForecasts.clear()
        weatherFlow.value    = null
        forecastsFlow.value  = emptyList()
        timeFlow.value       = null
        cacheValidFlow.value = false
    }

    fun getCachedWeatherSync(): CurrentWeatherEntity?       = cachedWeather
    fun getCachedForecastsSync(): List<HourlyForecastEntity> = cachedForecasts.toList()
    fun isCacheValidSync(): Boolean                          = cacheValid
}