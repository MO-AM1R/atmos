package com.example.atmos.domain.repository

import com.example.atmos.domain.model.Alert
import kotlinx.coroutines.flow.Flow

interface AlertRepository {
    fun getAllAlerts(): Flow<List<Alert>>
    suspend fun getAlertById(id: Int): Alert?
    suspend fun insertAlert(alert: Alert): Long
    suspend fun deleteAlert(alert: Alert)
    suspend fun deleteAlertById(id: Int)
    suspend fun updateAlertEnabled(id: Int, isEnabled: Boolean)
    suspend fun markAlertExpired(id: Int)
    suspend fun getActiveAlerts(): List<Alert>
}
