package com.example.atmos.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_day")
data class ForecastDayEntity(
    @PrimaryKey
    val cityName       : String,
    val countryCode    : String,
    val latitude       : Double,
    val longitude      : Double,
    val timezoneOffset : Int,
    val cachedAtUnix   : Long = System.currentTimeMillis()
)