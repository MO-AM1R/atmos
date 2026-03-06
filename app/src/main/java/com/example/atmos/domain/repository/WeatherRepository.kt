package com.example.atmos.domain.repository

import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastResponseDto
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String = TemperatureUnit.KELVIN.apiValue,
        lang: String = Language.ARABIC.apiValue,
    ): Flow<ApiResult<CurrentWeatherResponseDto?>>


    fun getForecast(
        lat: Double,
        lon: Double,
        unit: String = TemperatureUnit.KELVIN.apiValue,
        lang: String = Language.ARABIC.apiValue,
    ): Flow<ApiResult<ForecastResponseDto?>>
}