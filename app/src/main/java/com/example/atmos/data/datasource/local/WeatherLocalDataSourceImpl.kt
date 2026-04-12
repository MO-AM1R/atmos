package com.example.atmos.data.datasource.local

import com.example.atmos.data.database.dao.ForecastDao
import com.example.atmos.data.database.dao.WeatherDao
import com.example.atmos.data.database.entity.CurrentWeatherEntity
import com.example.atmos.data.database.entity.HourlyForecastEntity
import com.example.atmos.utils.AppConstants
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Singleton

@Singleton
class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao
) : WeatherLocalDataSource {

    override suspend fun cacheCurrentWeather(currentWeatherEntity: CurrentWeatherEntity) {
        weatherDao.cacheCurrentWeather(currentWeatherEntity)
    }

    override suspend fun cacheForecast(hourlyForecasts: List<HourlyForecastEntity>) {
        forecastDao.cacheForecast(hourlyForecasts)
    }

    override fun getCachedWeather(): Flow<CurrentWeatherEntity?> {
        return weatherDao.getCachedWeather()
    }

    override fun getCachedForecasts(): Flow<List<HourlyForecastEntity>> {
        return forecastDao.getCachedForecasts()
    }

    override fun getCachedTime(): Flow<Long?> {
        return weatherDao.getCachedTime()
    }

    override fun isCacheValid(): Flow<Boolean> {
        val expiryTime = System.currentTimeMillis() - AppConstants.CACHE_DURATION_MS
        return combine(
            weatherDao.isCacheValid(expiryTime),
            forecastDao.isCacheValid(expiryTime)
        ) { weatherValid, forecastValid ->
            weatherValid && forecastValid
        }
    }

    override suspend fun clearAllCache() {
        weatherDao.clearCachedWeather()
        forecastDao.clearCachedForecast()
    }

    override suspend fun clearWeatherCache(){
        weatherDao.clearCachedWeather()
    }

    override suspend fun clearForecastCache(){
        forecastDao.clearCachedForecast()
    }
}