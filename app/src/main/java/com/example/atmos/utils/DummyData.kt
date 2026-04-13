package com.example.atmos.utils

import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.domain.model.ForecastDay
import com.example.atmos.domain.model.HourlyForecast
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.ui.home.state.HomeScreenState
import com.example.atmos.ui.home.state.HomeUiState

object DummyData {

    val cairoCurrentWeather = CurrentWeather(
        cityName = "Cairo",
        countryCode = "EG",
        latitude = 30.0444,
        longitude = 31.2357,
        temperature = 28.5,
        feelsLike = 30.2,
        minimumTemp = 26.0,
        maximumTemp = 31.0,
        pressureHpa = 1013,
        humidityPercent = 55,
        windSpeedRaw = 3.5,
        windDirectionDeg = 180,
        cloudCoverPercent = 40,
        visibilityMeters = 10000,
        weatherConditionId = 802,
        weatherMain = "Clouds",
        weatherDescription = "scattered clouds",
        weatherIconCode = "03d",
        sunriseUnix = 1717214400L,
        sunsetUnix = 1717263600L,
        timezoneOffset = 7200,
        timestampUnix = 1717200000L
    )

    val londonCurrentWeather = CurrentWeather(
        cityName           = "London",
        countryCode        = "GB",
        latitude           = 51.5074,
        longitude          = -0.1278,
        temperature        = 18.0,
        feelsLike          = 17.5,
        minimumTemp        = 15.0,
        maximumTemp        = 20.0,
        pressureHpa        = 1010,
        humidityPercent    = 80,
        windSpeedRaw       = 5.2,
        windDirectionDeg   = 220,
        cloudCoverPercent  = 75,
        visibilityMeters   = 8000,
        weatherConditionId = 500,
        weatherMain        = "Rain",
        weatherDescription = "light rain",
        weatherIconCode    = "10d",
        sunriseUnix        = 1717210800L,
        sunsetUnix         = 1717268400L,
        timezoneOffset     = 3600,
        timestampUnix      = 1717200000L
    )

    val extremeHeatWeather = CurrentWeather(
        cityName           = "Riyadh",
        countryCode        = "SA",
        latitude           = 24.7136,
        longitude          = 46.6753,
        temperature        = 48.0,
        feelsLike          = 52.0,
        minimumTemp        = 44.0,
        maximumTemp        = 50.0,
        pressureHpa        = 998,
        humidityPercent    = 10,
        windSpeedRaw       = 6.5,
        windDirectionDeg   = 310,
        cloudCoverPercent  = 0,
        visibilityMeters   = 10000,
        weatherConditionId = 800,
        weatherMain        = "Clear",
        weatherDescription = "clear sky",
        weatherIconCode    = "01d",
        sunriseUnix        = 1717207200L,
        sunsetUnix         = 1717256400L,
        timezoneOffset     = 10800,
        timestampUnix      = 1717230000L
    )

    val stormWeather = CurrentWeather(
        cityName           = "Miami",
        countryCode        = "US",
        latitude           = 25.7617,
        longitude          = -80.1918,
        temperature        = 29.0,
        feelsLike          = 35.0,
        minimumTemp        = 27.0,
        maximumTemp        = 31.0,
        pressureHpa        = 990,
        humidityPercent    = 95,
        windSpeedRaw       = 25.0,
        windDirectionDeg   = 270,
        cloudCoverPercent  = 100,
        visibilityMeters   = 1000,
        weatherConditionId = 202,
        weatherMain        = "Thunderstorm",
        weatherDescription = "thunderstorm with heavy rain",
        weatherIconCode    = "11d",
        sunriseUnix        = 1717234800L,
        sunsetUnix         = 1717285200L,
        timezoneOffset     = -14400,
        timestampUnix      = 1717260000L
    )

    val hourlyForecast1 = HourlyForecast(
        timestampUnix = 1717200000L,
        forecastDateText = "2024-06-01 12:00:00",
        temperature = 28.5,
        feelsLike = 30.2,
        minimumTemp = 26.0,
        maximumTemp = 31.0,
        pressureHpa = 1013,
        humidityPercent = 55,
        windSpeedRaw = 3.5,
        windDirectionDeg = 180,
        cloudCoverPercent = 40,
        visibilityMeters = 10000,
        weatherConditionId = 802,
        weatherMain = "Clouds",
        weatherDescription = "scattered clouds",
        weatherIconCode = "03d"
    )

    val hourlyForecast2 = HourlyForecast(
        timestampUnix      = 1717210800L,
        forecastDateText   = "2024-06-01 15:00:00",
        temperature        = 30.1,
        feelsLike          = 32.0,
        minimumTemp        = 27.0,
        maximumTemp        = 32.0,
        pressureHpa        = 1012,
        humidityPercent    = 50,
        windSpeedRaw       = 4.2,
        windDirectionDeg   = 200,
        cloudCoverPercent  = 25,
        visibilityMeters   = 10000,
        weatherConditionId = 801,
        weatherMain        = "Clouds",
        weatherDescription = "few clouds",
        weatherIconCode    = "02d"
    )

    val hourlyForecast3 = HourlyForecast(
        timestampUnix      = 1717221600L,
        forecastDateText   = "2024-06-01 18:00:00",
        temperature        = 27.3,
        feelsLike          = 28.5,
        minimumTemp        = 25.0,
        maximumTemp        = 29.0,
        pressureHpa        = 1011,
        humidityPercent    = 65,
        windSpeedRaw       = 2.8,
        windDirectionDeg   = 160,
        cloudCoverPercent  = 60,
        visibilityMeters   = 9000,
        weatherConditionId = 803,
        weatherMain        = "Clouds",
        weatherDescription = "broken clouds",
        weatherIconCode    = "04d"
    )

    val hourlyForecast4 = HourlyForecast(
        timestampUnix      = 1717232400L,
        forecastDateText   = "2024-06-01 21:00:00",
        temperature        = 24.0,
        feelsLike          = 24.5,
        minimumTemp        = 22.0,
        maximumTemp        = 26.0,
        pressureHpa        = 1012,
        humidityPercent    = 72,
        windSpeedRaw       = 2.0,
        windDirectionDeg   = 140,
        cloudCoverPercent  = 80,
        visibilityMeters   = 8000,
        weatherConditionId = 500,
        weatherMain        = "Rain",
        weatherDescription = "light rain",
        weatherIconCode    = "10n"
    )

    val hourlyForecast5 = HourlyForecast(
        timestampUnix      = 1717243200L,
        forecastDateText   = "2024-06-02 00:00:00",
        temperature        = 22.0,
        feelsLike          = 22.3,
        minimumTemp        = 20.0,
        maximumTemp        = 24.0,
        pressureHpa        = 1013,
        humidityPercent    = 78,
        windSpeedRaw       = 1.5,
        windDirectionDeg   = 120,
        cloudCoverPercent  = 90,
        visibilityMeters   = 7000,
        weatherConditionId = 804,
        weatherMain        = "Clouds",
        weatherDescription = "overcast clouds",
        weatherIconCode    = "04n"
    )

    val hourlyForecasts = listOf(
        hourlyForecast1,
        hourlyForecast2,
        hourlyForecast3,
        hourlyForecast4,
        hourlyForecast5
    )

    val forecastDay1 = ForecastDay(
        dayName = "Saturday",
        dateLabel = "Jun 1",
        minTemp = 22.0,
        maxTemp = 32.0,
        representativeIcon = "03d",
        timestampUnix = 1717200000L
    )

    val forecastDay2 = ForecastDay(
        dayName           = "Sunday",
        dateLabel         = "Jun 2",
        minTemp           = 20.0,
        maxTemp           = 30.0,
        representativeIcon = "02d",
        timestampUnix     = 1717286400L
    )

    val forecastDay3 = ForecastDay(
        dayName           = "Monday",
        dateLabel         = "Jun 3",
        minTemp           = 19.0,
        maxTemp           = 28.0,
        representativeIcon = "10d",
        timestampUnix     = 1717372800L
    )

    val forecastDay4 = ForecastDay(
        dayName           = "Tuesday",
        dateLabel         = "Jun 4",
        minTemp           = 21.0,
        maxTemp           = 31.0,
        representativeIcon = "01d",
        timestampUnix     = 1717459200L
    )

    val forecastDay5 = ForecastDay(
        dayName           = "Wednesday",
        dateLabel         = "Jun 5",
        minTemp           = 23.0,
        maxTemp           = 33.0,
        representativeIcon = "04d",
        timestampUnix     = 1717545600L
    )

    val forecastDays = listOf(
        forecastDay1,
        forecastDay2,
        forecastDay3,
        forecastDay4,
        forecastDay5
    )

    val forecast = Forecast(
        hourlyForecasts = hourlyForecasts,
        cityName = "Giza",
        countryCode = "120123",
        latitude = 30.3223,
        longitude = 31.0023,
        timezoneOffset = 12
    )

    val cairoForecast = Forecast(
        cityName        = "Cairo",
        countryCode     = "EG",
        latitude        = 30.0444,
        longitude       = 31.2357,
        timezoneOffset  = 7200,
        hourlyForecasts = hourlyForecasts
    )

    val londonForecast = Forecast(
        cityName        = "London",
        countryCode     = "GB",
        latitude        = 51.5074,
        longitude       = -0.1278,
        timezoneOffset  = 3600,
        hourlyForecasts = listOf(
            hourlyForecast1.copy(
                temperature        = 18.0,
                feelsLike          = 17.5,
                minimumTemp        = 15.0,
                maximumTemp        = 20.0,
                humidityPercent    = 80,
                weatherConditionId = 500,
                weatherMain        = "Rain",
                weatherDescription = "light rain",
                weatherIconCode    = "10d"
            ),
            hourlyForecast2.copy(
                temperature        = 17.0,
                feelsLike          = 16.0,
                minimumTemp        = 14.0,
                maximumTemp        = 19.0,
                humidityPercent    = 85,
                weatherConditionId = 501,
                weatherMain        = "Rain",
                weatherDescription = "moderate rain",
                weatherIconCode    = "10d"
            )
        )
    )

    val nightUiState = HomeUiState(
        currentWeather  = londonCurrentWeather,
        forecastDays    = londonForecast,
        point           = StoredPoint(30.0444, 31.2357),
        isDataLoaded    = true,
        isConnected     = true,
        screenState     = HomeScreenState.Success,
        userPreferences = UserPreferences(
            temperatureUnitOption = TemperatureUnit.CELSIUS,
            windUnitOption = WindUnit.METERS_PER_SECOND,
            languageOption = Language.ENGLISH,
            locationOption = LocationOption.GPS,
            storedPoint = null
        )
    )

}