package com.example.atmos.data.datasource.remote

import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastResponseDto

interface FakeWeatherRemoteDataSource: WeatherRemoteDataSource {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String,
    ): Result<CurrentWeatherResponseDto?>


    override  suspend fun getForecast(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String,
    ): Result<ForecastResponseDto?>
}