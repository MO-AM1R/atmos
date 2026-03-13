package com.example.atmos.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.atmos.data.database.entity.HourlyForecastEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheForecast(hourlyForecasts: List<HourlyForecastEntity>)

    @Query("DELETE FROM HOURLY_FORECAST")
    suspend fun clearCachedForecast()

    @Query("SELECT cachedAtUnix FROM HOURLY_FORECAST LIMIT 1")
    fun getCachedTime(): Flow<Long?>

    @Query("SELECT * FROM HOURLY_FORECAST")
    fun getCachedForecasts(): Flow<List<HourlyForecastEntity>>

    @Query("SELECT COUNT(*) FROM HOURLY_FORECAST WHERE cachedAtUnix > :expiryTime")
    fun isCacheValid(expiryTime: Long): Flow<Boolean>
}
