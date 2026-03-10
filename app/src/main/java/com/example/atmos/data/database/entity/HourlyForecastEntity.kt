package com.example.atmos.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName  = "hourly_forecast",
    primaryKeys = ["timestampUnix", "cityName"]
)
data class HourlyForecastEntity(
    val timestampUnix      : Long,
    val cityName           : String,
    val forecastDateText   : String,
    val temperature        : Double,
    val feelsLike          : Double,
    val minimumTemp        : Double,
    val maximumTemp        : Double,
    val pressureHpa        : Int,
    val humidityPercent    : Int,
    val windSpeedRaw       : Double,
    val windDirectionDeg   : Int,
    val cloudCoverPercent  : Int,
    val visibilityMeters   : Int,
    val weatherConditionId : Int,
    val weatherMain        : String,
    val weatherDescription : String,
    val weatherIconCode    : String,
    val cachedAtUnix       : Long = System.currentTimeMillis()
)