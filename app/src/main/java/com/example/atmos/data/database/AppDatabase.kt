package com.example.atmos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.atmos.data.database.dao.ForecastDao
import com.example.atmos.data.database.dao.WeatherDao
import com.example.atmos.data.database.entity.CurrentWeatherEntity
import com.example.atmos.data.database.entity.HourlyForecastEntity


@Database(
    entities = [
        CurrentWeatherEntity::class,
        HourlyForecastEntity::class,
    ],
    version  = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun forecastDao(): ForecastDao
}