package com.example.atmos.data.mappers

import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastItemDto
import com.example.atmos.data.dto.ForecastResponseDto
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.ForecastDay
import com.example.atmos.domain.model.HourlyForecast

fun CurrentWeatherResponseDto.toDomain(): CurrentWeather {
    return CurrentWeather(
        cityName           = cityName,
        countryCode        = sun.countryCode ?: "",
        latitude           = coordinate.latitude,
        longitude          = coordinate.longitude,
        temperature        = temperature.temperature,
        feelsLike          = temperature.feelsLike,
        minimumTemp        = temperature.minimumTemp,
        maximumTemp        = temperature.maximumTemp,
        pressureHpa        = temperature.pressureHpa,
        humidityPercent    = temperature.humidityPercent,
        windSpeedRaw       = wind.speedMetersPerSec,
        windDirectionDeg   = wind.directionDegrees,
        cloudCoverPercent  = clouds.coveragePercent,
        visibilityMeters   = visibilityMeters ?: 0,
        weatherConditionId = weatherConditions.firstOrNull()?.conditionId ?: 0,
        weatherMain        = weatherConditions.firstOrNull()?.conditionMain ?: "",
        weatherDescription = weatherConditions.firstOrNull()?.description ?: "",
        weatherIconCode    = weatherConditions.firstOrNull()?.iconCode ?: "",
        sunriseUnix        = sun.sunriseUnix ?: 0L,
        sunsetUnix         = sun.sunsetUnix ?: 0L,
        timezoneOffset     = timezoneOffset,
        timestampUnix      = timestampUnix
    )
}

fun ForecastItemDto.toDomain(): HourlyForecast {
    return HourlyForecast(
        timestampUnix      = timestampUnix,
        forecastDateText   = forecastDateText,
        temperature        = temperature.temperature,
        feelsLike          = temperature.feelsLike,
        minimumTemp        = temperature.minimumTemp,
        maximumTemp        = temperature.maximumTemp,
        pressureHpa        = temperature.pressureHpa,
        humidityPercent    = temperature.humidityPercent,
        windSpeedRaw       = wind.speedMetersPerSec,
        windDirectionDeg   = wind.directionDegrees,
        cloudCoverPercent  = clouds.coveragePercent,
        visibilityMeters   = visibilityMeters ?: 0,
        weatherConditionId = weatherConditions.firstOrNull()?.conditionId ?: 0,
        weatherMain        = weatherConditions.firstOrNull()?.conditionMain ?: "",
        weatherDescription = weatherConditions.firstOrNull()?.description ?: "",
        weatherIconCode    = weatherConditions.firstOrNull()?.iconCode ?: ""
    )
}

fun ForecastResponseDto.toDomain(): ForecastDay {
    return ForecastDay(
        cityName        = city.cityName,
        countryCode     = city.countryCode,
        latitude        = city.coordinate.latitude,
        longitude       = city.coordinate.longitude,
        timezoneOffset  = city.timezoneOffset,
        hourlyForecasts = forecastItems.map { it.toDomain() }
    )
}