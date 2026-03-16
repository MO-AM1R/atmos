package com.example.atmos.data.database.dao

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.atmos.data.database.AppDatabase
import com.example.atmos.data.database.entity.AlertEntity
import com.example.atmos.data.enums.AlertType
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AlertDaoTest {

    private lateinit var alertDao: AlertDao
    private lateinit var database: AppDatabase
    private lateinit var dummyData: List<AlertEntity>

    @Before
    fun setup() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        database = Room.inMemoryDatabaseBuilder(
            application,
            AppDatabase::class.java
        ).build()

        alertDao = database.alertDao()

        dummyData = listOf(
            AlertEntity(
                id = 1,
                startTimeMs = System.currentTimeMillis(),
                endTimeMs = System.currentTimeMillis() + 1200,
                type = AlertType.ALARM.name,
                isEnabled = true,
                isExpired = false,
            ),
            AlertEntity(
                id = 2,
                startTimeMs = System.currentTimeMillis() + 3600,
                endTimeMs = System.currentTimeMillis() + 7200,
                type = AlertType.NOTIFICATION.name,
                isEnabled = true,
                isExpired = false,
            ),
            AlertEntity(
                id = 3,
                startTimeMs = System.currentTimeMillis() + 10000,
                endTimeMs = System.currentTimeMillis() + 20000,
                type = AlertType.ALARM.name,
                isEnabled = false,
                isExpired = false,
            ),
            AlertEntity(
                id = 4,
                startTimeMs = System.currentTimeMillis() - 5000,
                endTimeMs = System.currentTimeMillis() - 1000,
                type = AlertType.NOTIFICATION.name,
                isEnabled = true,
                isExpired = true, // ✅ Expired
            ),
            AlertEntity(
                id = 5,
                startTimeMs = System.currentTimeMillis() + 50000,
                endTimeMs = null,
                type = AlertType.ALARM.name,
                isEnabled = true,
                isExpired = false,
            ),
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getAllAlerts_retrieveAllAlerts() = runTest {
        dummyData.forEach { alertDao.insertAlert(it) }

        val result = alertDao.getAllAlerts().first()

        assertEquals(dummyData.size, result.size)
        assertEquals(dummyData, result.sortedBy { it.id })
    }

    @Test
    fun getAlertById_alertId_retrieveAlertWithSpecificId() = runTest {
        dummyData.forEach { alertDao.insertAlert(it) }

        val result = alertDao.getAlertById(1)

        assertNotNull(result)
        assertEquals(dummyData[0], result)
    }

    @Test
    fun getAlertById_invalidAlertId_returnsNull() = runTest {
        dummyData.forEach { alertDao.insertAlert(it) }

        val result = alertDao.getAlertById(999)

        assertNull(result)
    }

    @Test
    fun getActiveAlerts_retrieveActiveAlerts() = runTest {
        dummyData.forEach { alertDao.insertAlert(it) }

        val result = alertDao.getActiveAlerts()

        val expected = dummyData.filter { !it.isExpired && it.isEnabled }

        assertEquals(expected.size, result.size)
        assertTrue(result.all { !it.isExpired && it.isEnabled })
    }

    @Test
    fun insertAlert_task_retrieveNewAlertList() = runTest {
        val alert = dummyData[0]
        alertDao.insertAlert(alert)

        val result = alertDao.getAllAlerts().first()

        assertEquals(1, result.size)
        assertEquals(alert, result[0])
    }

    @Test
    fun deleteAlertById_alertId_retrieveNullWithTheDeletedAlertId() = runTest {
        dummyData.forEach { alertDao.insertAlert(it) }

        alertDao.deleteAlertById(1)

        val result = alertDao.getAlertById(1)

        assertNull(result)
    }

    @Test
    fun deleteAllAlerts_retrieveEmptyList() = runTest {
        dummyData.forEach { alertDao.insertAlert(it) }

        dummyData.forEach { alertDao.deleteAlertById(it.id) }

        val result = alertDao.getAllAlerts().first()

        assertTrue(result.isEmpty())
    }
}