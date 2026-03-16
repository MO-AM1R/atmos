package com.example.atmos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.atmos.data.database.entity.AlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {

    @Query("SELECT * FROM alerts ORDER BY startTimeMs ASC")
    fun getAllAlerts(): Flow<List<AlertEntity>>

    @Query("SELECT * FROM alerts WHERE id = :id LIMIT 1")
    suspend fun getAlertById(id: Int): AlertEntity?

    @Query("SELECT * FROM alerts WHERE isExpired = 0 AND isEnabled = 1")
    suspend fun getActiveAlerts(): List<AlertEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(entity: AlertEntity): Long

    @Delete
    suspend fun deleteAlert(entity: AlertEntity)

    @Query("DELETE FROM alerts WHERE id = :id")
    suspend fun deleteAlertById(id: Int)

    @Query("UPDATE alerts SET isEnabled = :isEnabled WHERE id = :id")
    suspend fun updateAlertEnabled(id: Int, isEnabled: Boolean)

    @Query("UPDATE alerts SET isExpired = 1, isEnabled = 0 WHERE id = :id")
    suspend fun markAlertExpired(id: Int)
}