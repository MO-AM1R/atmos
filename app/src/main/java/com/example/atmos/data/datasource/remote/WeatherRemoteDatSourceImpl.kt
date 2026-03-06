package com.example.atmos.data.datasource.remote
import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastResponseDto
import com.example.atmos.data.network.WeatherApiService
import javax.inject.Inject


class WeatherRemoteDatSourceImpl @Inject constructor(
    private val api: WeatherApiService
): WeatherRemoteDatSource {

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String,
    ): Result<CurrentWeatherResponseDto?>{
        return runCatching {
            api.getCurrentWeather(
                lat,
                lon,
                unit,
                lang
            ).body()
        }
    }

    override suspend fun getForecast(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String,
    ): Result<ForecastResponseDto?>{
        return runCatching {
            api.getForecast(
                lat,
                lon,
                unit,
                lang
            ).body()
        }
    }
}