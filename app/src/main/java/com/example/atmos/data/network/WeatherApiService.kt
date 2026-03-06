package com.example.atmos.data.network
import com.example.atmos.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

    @GET(AppConstants.WEATHER_BASE_URL + "forecast")
    suspend fun getForecast(
        @Query("lat")    lat: Double,
        @Query("lon")    lon: Double,
        @Query("units")  unit: String = _root_ide_package_.com.example.atmos.data.enums.TemperatureUnit.KELVIN.apiValue,
        @Query("lang")   lang: String = _root_ide_package_.com.example.atmos.data.enums.Language.ARABIC.apiValue,
    ): Response<com.example.atmos.data.dto.ForecastResponseDto>


    @GET(AppConstants.WEATHER_BASE_URL + "weather")
    suspend fun getCurrentWeather(
        @Query("lat")    lat: Double,
        @Query("lon")    lon: Double,
        @Query("units")  unit: String = _root_ide_package_.com.example.atmos.data.enums.TemperatureUnit.KELVIN.apiValue,
        @Query("lang")   lang: String = _root_ide_package_.com.example.atmos.data.enums.Language.ARABIC.apiValue,
    ): Response<com.example.atmos.data.dto.CurrentWeatherResponseDto>
}