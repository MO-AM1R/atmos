package com.example.atmos.data.repository

import com.example.atmos.data.datasource.local.AlertLocalDataSource
import com.example.atmos.data.mappers.toDomain
import com.example.atmos.data.mappers.toEntity
import com.example.atmos.domain.model.Alert
import com.example.atmos.domain.repository.AlertRepository
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@Singleton
class AlertRepositoryImpl @Inject constructor(
    private val localDataSource: AlertLocalDataSource
) : AlertRepository {

    override fun getAllAlerts(): Flow<List<Alert>> =
        localDataSource.getAllAlerts().map { list -> list.map { it.toDomain() } }

    override suspend fun getAlertById(id: Int): Alert? =
        localDataSource.getAlertById(id)?.toDomain()

    override suspend fun insertAlert(alert: Alert): Long =
        localDataSource.insertAlert(alert.toEntity())

    override suspend fun deleteAlert(alert: Alert) =
        localDataSource.deleteAlert(alert.toEntity())

    override suspend fun deleteAlertById(id: Int) =
        localDataSource.deleteAlertById(id)

    override suspend fun updateAlertEnabled(id: Int, isEnabled: Boolean) =
        localDataSource.updateAlertEnabled(id, isEnabled)

    override suspend fun markAlertExpired(id: Int) =
        localDataSource.markAlertExpired(id)

    override suspend fun getActiveAlerts(): List<Alert> {
        return localDataSource.getActiveAlerts().map { it.toDomain() }
    }
}