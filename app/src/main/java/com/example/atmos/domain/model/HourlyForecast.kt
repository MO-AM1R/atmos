package com.example.atmos.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

data class HourlyForecast(
    val timestampUnix     : Long,
    val forecastDateText  : String,
    val temperature       : Double,
    val feelsLike         : Double,
    val minimumTemp       : Double,
    val maximumTemp       : Double,
    val pressureHpa       : Int,
    val humidityPercent   : Int,
    val windSpeedRaw      : Double,
    val windDirectionDeg  : Int,
    val cloudCoverPercent : Int,
    val visibilityMeters  : Int,
    val weatherConditionId: Int,
    val weatherMain       : String,
    val weatherDescription: String,
    val weatherIconCode   : String
)


fun List<HourlyForecast>.groupIntoDays(): List<ForecastDay> {
    return this
        .groupBy { forecast ->
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(forecast.timestampUnix * 1000))
        }
        .map { (_, hourlyList) ->
            val representative = hourlyList
                .minByOrNull { abs(
                    SimpleDateFormat("HH", Locale.getDefault())
                        .format(Date(it.timestampUnix * 1000)).toInt() - 12
                ) }

            ForecastDay(
                dayName           = SimpleDateFormat("EEEE", Locale.getDefault())
                    .format(Date((hourlyList.first().timestampUnix) * 1000)),
                dateLabel         = SimpleDateFormat("MMM d", Locale.getDefault())
                    .format(Date((hourlyList.first().timestampUnix) * 1000)),
                minTemp           = hourlyList.minOf { it.minimumTemp },
                maxTemp           = hourlyList.maxOf { it.maximumTemp },
                representativeIcon = representative?.weatherIconCode ?: "",
                timestampUnix     = hourlyList.first().timestampUnix
            )
        }
        .take(5)
}