package com.example.atmos.data.datasource.local

import com.example.atmos.data.database.dao.AlertDao
import com.example.atmos.data.database.entity.AlertEntity
import jakarta.inject.Singleton
import javax.inject.Inject


@Singleton
class AlertLocalDataSourceImpl @Inject constructor(
    private val dao: AlertDao
) : AlertLocalDataSource {

    override fun getAllAlerts() = dao.getAllAlerts()

    override suspend fun getAlertById(id: Int) = dao.getAlertById(id)

    override suspend fun getActiveAlerts() = dao.getActiveAlerts()

    override suspend fun insertAlert(entity: AlertEntity) = dao.insertAlert(entity)

    override suspend fun deleteAlert(entity: AlertEntity) = dao.deleteAlert(entity)

    override suspend fun deleteAlertById(id: Int) = dao.deleteAlertById(id)

    override suspend fun updateAlertEnabled(id: Int, isEnabled: Boolean) =
        dao.updateAlertEnabled(id, isEnabled)

    override suspend fun markAlertExpired(id: Int) = dao.markAlertExpired(id)
}