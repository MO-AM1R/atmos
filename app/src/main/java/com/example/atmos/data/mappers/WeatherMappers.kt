package com.example.atmos.data.mappers
import com.example.atmos.data.database.entity.CurrentWeatherEntity
import com.example.atmos.data.database.entity.HourlyForecastEntity
import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastItemDto
import com.example.atmos.data.dto.ForecastResponseDto
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.domain.model.HourlyForecast


fun CurrentWeatherResponseDto.toEntity(): CurrentWeatherEntity {
    val condition = weatherConditions.firstOrNull()
    return CurrentWeatherEntity(
        id                 = 1,
        cityName           = cityName,
        countryCode        = sun.countryCode.orEmpty(),
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
        visibilityMeters   = visibilityMeters,
        weatherConditionId = condition?.conditionId ?: 0,
        weatherMain        = condition?.conditionMain.orEmpty(),
        weatherDescription = condition?.description.orEmpty(),
        weatherIconCode    = condition?.iconCode.orEmpty(),
        sunriseUnix        = sun.sunriseUnix ?: 0L,
        sunsetUnix         = sun.sunsetUnix ?: 0L,
        timezoneOffset     = timezoneOffset,
        timestampUnix      = timestampUnix,
        cachedAtUnix       = System.currentTimeMillis()
    )
}

fun ForecastResponseDto.toEntityList(): List<HourlyForecastEntity> {
    return forecastItems.map { item ->
        val condition = item.weatherConditions.firstOrNull()
        HourlyForecastEntity(
            timestampUnix      = item.timestampUnix,
            cityName           = city.cityName,
            countryCode        = city.countryCode,
            latitude           = city.coordinate.latitude,
            longitude          = city.coordinate.longitude,
            timezoneOffset     = city.timezoneOffset,
            forecastDateText   = item.forecastDateText,
            temperature        = item.temperature.temperature,
            feelsLike          = item.temperature.feelsLike,
            minimumTemp        = item.temperature.minimumTemp,
            maximumTemp        = item.temperature.maximumTemp,
            pressureHpa        = item.temperature.pressureHpa,
            humidityPercent    = item.temperature.humidityPercent,
            windSpeedRaw       = item.wind.speedMetersPerSec,
            windDirectionDeg   = item.wind.directionDegrees,
            cloudCoverPercent  = item.clouds.coveragePercent,
            visibilityMeters   = item.visibilityMeters,
            weatherConditionId = condition?.conditionId ?: 0,
            weatherMain        = condition?.conditionMain.orEmpty(),
            weatherDescription = condition?.description.orEmpty(),
            weatherIconCode    = condition?.iconCode.orEmpty(),
            cachedAtUnix       = System.currentTimeMillis()
        )
    }
}

fun CurrentWeatherResponseDto.toDomain(): CurrentWeather {
    val condition = weatherConditions.firstOrNull()
    return CurrentWeather(
        cityName           = cityName,
        countryCode        = sun.countryCode.orEmpty(),
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
        weatherConditionId = condition?.conditionId ?: 0,
        weatherMain        = condition?.conditionMain.orEmpty(),
        weatherDescription = condition?.description.orEmpty(),
        weatherIconCode    = condition?.iconCode.orEmpty(),
        sunriseUnix        = sun.sunriseUnix ?: 0L,
        sunsetUnix         = sun.sunsetUnix ?: 0L,
        timezoneOffset     = timezoneOffset,
        timestampUnix      = timestampUnix
    )
}

fun ForecastResponseDto.toDomain(): Forecast {
    return Forecast(
        cityName        = city.cityName,
        countryCode     = city.countryCode,
        latitude        = city.coordinate.latitude,
        longitude       = city.coordinate.longitude,
        timezoneOffset  = city.timezoneOffset,
        hourlyForecasts = forecastItems.map { it.toDomain() }
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
        visibilityMeters   = visibilityMeters ?: 0,
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

fun List<HourlyForecastEntity>.toDomain(): Forecast {
    val first = this.first()
    return Forecast(
        cityName        = first.cityName,
        countryCode     = first.countryCode,
        latitude        = first.latitude,
        longitude       = first.longitude,
        timezoneOffset  = first.timezoneOffset,
        hourlyForecasts = this.map { it.toDomain() }
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
        visibilityMeters   = visibilityMeters ?: 0,
        weatherConditionId = weatherConditionId,
        weatherMain        = weatherMain,
        weatherDescription = weatherDescription,
        weatherIconCode    = weatherIconCode
    )
}

fun ForecastItemDto.toDomain(): HourlyForecast {
    val condition = weatherConditions.firstOrNull()
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
        weatherConditionId = condition?.conditionId ?: 0,
        weatherMain        = condition?.conditionMain.orEmpty(),
        weatherDescription = condition?.description.orEmpty(),
        weatherIconCode    = condition?.iconCode.orEmpty()
    )
}