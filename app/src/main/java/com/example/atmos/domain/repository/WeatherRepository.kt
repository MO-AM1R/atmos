package com.example.atmos.domain.repository

import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        forceUpdate: Boolean = false,
        unit: String = TemperatureUnit.CELSIUS.apiValue,
        lang: String = Language.ENGLISH.apiValue,
    ): Flow<Resource<CurrentWeather>>

    fun getForecast(
        lat: Double,
        lon: Double,
        forceUpdate: Boolean = false,
        unit: String = TemperatureUnit.CELSIUS.apiValue,
        lang: String = Language.ENGLISH.apiValue,
    ): Flow<Resource<Forecast>>

    fun getWeatherForPoint(
        lat: Double,
        lon: Double,
        unit: String = TemperatureUnit.CELSIUS.apiValue,
        lang: String = Language.ENGLISH.apiValue,
    ): Flow<Resource<CurrentWeather>>

    suspend fun hasCache(): Boolean
}