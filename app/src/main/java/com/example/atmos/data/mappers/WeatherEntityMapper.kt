package com.example.atmos.data.mappers

import com.example.atmos.data.database.entity.CurrentWeatherEntity
import com.example.atmos.data.database.entity.ForecastDayEntity
import com.example.atmos.data.database.entity.HourlyForecastEntity
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.domain.model.HourlyForecast

fun CurrentWeather.toEntity(): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        cityName           = cityName,
        countryCode        = countryCode,
        latitude           = latitude,
        longitude          = longitude,
        temperature        = temperature,
        feelsLike          = feelsLike,
        minimumTemp        = minimumTemp,
        maximumTemp        = maximumTemp,
        pressureHpa        = pressureHpa,
        humidityPercent    = humidityPercent,
        windSpeedRaw       = windSpeedRaw,
        windDirectionDeg   = windDirectionDeg,
        cloudCoverPercent  = cloudCoverPercent,
        visibilityMeters   = visibilityMeters,
        weatherConditionId = weatherConditionId,
        weatherMain        = weatherMain,
        weatherDescription = weatherDescription,
        weatherIconCode    = weatherIconCode,
        sunriseUnix        = sunriseUnix,
        sunsetUnix         = sunsetUnix,
        timezoneOffset     = timezoneOffset,
        timestampUnix      = timestampUnix
    )
}

fun CurrentWeatherEntity.toDomain(): CurrentWeather {
    return CurrentWeather(
        cityName           = cityName,
        countryCode        = countryCode,
        latitude           = latitude,
        longitude          = longitude,
        temperature        = temperature,
        feelsLike          = feelsLike,
        minimumTemp        = minimumTemp,
        maximumTemp        = maximumTemp,
        pressureHpa        = pressureHpa,
        humidityPercent    = humidityPercent,
        windSpeedRaw       = windSpeedRaw,
        windDirectionDeg   = windDirectionDeg,
        cloudCoverPercent  = cloudCoverPercent,
        visibilityMeters   = visibilityMeters,
        weatherConditionId = weatherConditionId,
        weatherMain        = weatherMain,
        weatherDescription = weatherDescription,
        weatherIconCode    = weatherIconCode,
        sunriseUnix        = sunriseUnix,
        sunsetUnix         = sunsetUnix,
        timezoneOffset     = timezoneOffset,
        timestampUnix      = timestampUnix
    )
}

fun CurrentWeatherEntity.isExpired(): Boolean {
    val thirtyMinutesMillis = 30 * 60 * 1000L
    return System.currentTimeMillis() - cachedAtUnix > thirtyMinutesMillis
}

fun HourlyForecast.toEntity(cityName: String): HourlyForecastEntity {
    return HourlyForecastEntity(
        timestampUnix      = timestampUnix,
        cityName           = cityName,
        forecastDateText   = forecastDateText,
        temperature        = temperature,
        feelsLike          = feelsLike,
        minimumTemp        = minimumTemp,
        maximumTemp        = maximumTemp,
        pressureHpa        = pressureHpa,
        humidityPercent    = humidityPercent,
        windSpeedRaw       = windSpeedRaw,
        windDirectionDeg   = windDirectionDeg,
        cloudCoverPercent  = cloudCoverPercent,
        visibilityMeters   = visibilityMeters,
        weatherConditionId = weatherConditionId,
        weatherMain        = weatherMain,
        weatherDescription = weatherDescription,
        weatherIconCode    = weatherIconCode
    )
}

fun HourlyForecastEntity.toDomain(): HourlyForecast {
    return HourlyForecast(
        timestampUnix      = timestampUnix,
        forecastDateText   = forecastDateText,
        temperature        = temperature,
        feelsLike          = feelsLike,
        minimumTemp        = minimumTemp,
        maximumTemp        = maximumTemp,
        pressureHpa        = pressureHpa,
        humidityPercent    = humidityPercent,
        windSpeedRaw       = windSpeedRaw,
        windDirectionDeg   = windDirectionDeg,
        cloudCoverPercent  = cloudCoverPercent,
        visibilityMeters   = visibilityMeters,
        weatherConditionId = weatherConditionId,
        weatherMain        = weatherMain,
        weatherDescription = weatherDescription,
        weatherIconCode    = weatherIconCode
    )
}

fun HourlyForecastEntity.isExpired(): Boolean {
    val thirtyMinutesMillis = 30 * 60 * 1000L
    return System.currentTimeMillis() - cachedAtUnix > thirtyMinutesMillis
}

fun Forecast.toEntity(): ForecastDayEntity {
    return ForecastDayEntity(
        cityName       = cityName,
        countryCode    = countryCode,
        latitude       = latitude,
        longitude      = longitude,
        timezoneOffset = timezoneOffset
    )
}

fun ForecastDayEntity.toDomain(
    hourlyForecasts: List<HourlyForecastEntity>
): Forecast {
    return Forecast(
        cityName        = cityName,
        countryCode     = countryCode,
        latitude        = latitude,
        longitude       = longitude,
        timezoneOffset  = timezoneOffset,
        hourlyForecasts = hourlyForecasts.map { it.toDomain() }
    )
}