package com.example.atmos.data.datasource.remote

import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastResponseDto

class FakeWeatherRemoteDataSourceImpl(
    private val currentWeather: CurrentWeatherResponseDto,
    private val hourlyForecasts: ForecastResponseDto
) : FakeWeatherRemoteDataSource {

    var shouldThrowError = false
    var errorMessage = "Network error"

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Result<CurrentWeatherResponseDto?> {
        if (shouldThrowError) return Result.failure(Exception(errorMessage))
        return Result.success(currentWeather)
    }

    override suspend fun getForecast(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Result<ForecastResponseDto?> {
        if (shouldThrowError) return Result.failure(Exception(errorMessage))
        return Result.success(hourlyForecasts)
    }
}