package com.example.atmos.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.atmos.data.database.entity.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Query("DELETE FROM CURRENT_WEATHER")
    suspend fun clearCachedWeather()

    @Query("SELECT cachedAtUnix FROM CURRENT_WEATHER LIMIT 1")
    fun getCachedTime(): Flow<Long?>

    @Query("SELECT * FROM CURRENT_WEATHER LIMIT 1")
    fun getCachedWeather(): Flow<CurrentWeatherEntity?>

    @Query("SELECT COUNT(*) FROM CURRENT_WEATHER WHERE cachedAtUnix > :expiryTime")
    fun isCacheValid(expiryTime: Long): Flow<Boolean>
}