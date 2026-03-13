package com.example.atmos.data.datasource.remote
import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastResponseDto
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.TemperatureUnit


interface WeatherRemoteDataSource {

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String = TemperatureUnit.KELVIN.apiValue,
        lang: String = Language.ARABIC.apiValue,
    ): Result<CurrentWeatherResponseDto?>


    suspend fun getForecast(
        lat: Double,
        lon: Double,
        unit: String = TemperatureUnit.KELVIN.apiValue,
        lang: String = Language.ARABIC.apiValue,
    ): Result<ForecastResponseDto?>
}