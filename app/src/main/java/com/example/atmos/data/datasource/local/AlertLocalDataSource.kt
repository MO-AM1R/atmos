package com.example.atmos.data.datasource.local

import com.example.atmos.data.database.entity.AlertEntity
import kotlinx.coroutines.flow.Flow

interface AlertLocalDataSource {
    fun getAllAlerts(): Flow<List<AlertEntity>>
    suspend fun getAlertById(id: Int): AlertEntity?
    suspend fun getActiveAlerts(): List<AlertEntity>
    suspend fun insertAlert(entity: AlertEntity): Long
    suspend fun deleteAlert(entity: AlertEntity)
    suspend fun deleteAlertById(id: Int)
    suspend fun updateAlertEnabled(id: Int, isEnabled: Boolean)
    suspend fun markAlertExpired(id: Int)
}