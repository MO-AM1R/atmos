package com.example.atmos.ui.alert.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.atmos.data.enums.AlertType
import com.example.atmos.data.workers.AlertScheduler
import com.example.atmos.domain.model.Alert
import com.example.atmos.domain.repository.AlertRepository
import com.example.atmos.ui.alert.state.AlertsEvent
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AlertsViewModelTest {

    private lateinit var viewModel: AlertsViewModel

    private val alertRepository: AlertRepository = mockk()
    private val alertScheduler: AlertScheduler = mockk()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val dummyAlerts = listOf(
        Alert(
            id = 1,
            startTimeMs = System.currentTimeMillis() + 3600,
            endTimeMs = System.currentTimeMillis() + 7200,
            type = AlertType.NOTIFICATION,
            isEnabled = true,
            isExpired = false
        ),
        Alert(
            id = 2,
            startTimeMs = System.currentTimeMillis() + 10000,
            endTimeMs = null,
            type = AlertType.ALARM,
            isEnabled = true,
            isExpired = false
        ),
        Alert(
            id = 3,
            startTimeMs = System.currentTimeMillis() + 20000,
            endTimeMs = null,
            type = AlertType.ALARM,
            isEnabled = false,
            isExpired = false
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { alertRepository.getAllAlerts() } returns flowOf(dummyAlerts)
        coEvery { alertRepository.insertAlert(any()) } returns 1L
        coEvery { alertRepository.deleteAlertById(any()) } just Runs
        coEvery { alertRepository.markAlertExpired(any()) } just Runs
        coEvery { alertRepository.updateAlertEnabled(any(), any()) } just Runs
        coEvery { alertRepository.getAlertById(any()) } returns dummyAlerts[0]
        coEvery { alertRepository.getActiveAlerts() } returns dummyAlerts
        coEvery { alertScheduler.schedule(any()) } just Runs
        coEvery { alertScheduler.cancel(any()) } just Runs

        viewModel = AlertsViewModel(
            alertRepository = alertRepository,
            alertScheduler = alertScheduler
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun onAddAlert_validAlert_insertsAndSchedules() = runTest {
        viewModel.onEvent(
            AlertsEvent.OnAddAlert(
                startTimeMs = System.currentTimeMillis() + 3600,
                endTimeMs = System.currentTimeMillis() + 7200,
                type = AlertType.NOTIFICATION
            )
        )

        coVerify { alertRepository.insertAlert(any()) }
        coVerify { alertScheduler.schedule(any()) }
    }

    @Test
    fun onAddAlert_alarmType_schedulesWithNoEndTime() = runTest {
        viewModel.onEvent(
            AlertsEvent.OnAddAlert(
                startTimeMs = System.currentTimeMillis() + 3600,
                endTimeMs = null,
                type = AlertType.ALARM
            )
        )

        coVerify {
            alertScheduler.schedule(
                match { it.endTimeMs == null && it.type == AlertType.ALARM }
            )
        }
    }

    @Test
    fun onDeleteAlert_existingAlert_removedFromUiImmediately() = runTest {
        val itemToDelete = viewModel.uiState.value.alerts[0]

        viewModel.onEvent(AlertsEvent.OnDeleteAlert(itemToDelete))

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.alerts.any { it.id == itemToDelete.id })
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onDeleteAlert_existingAlert_snackbarIsVisible() = runTest {
        val itemToDelete = viewModel.uiState.value.alerts[0]

        viewModel.onEvent(AlertsEvent.OnDeleteAlert(itemToDelete))

        assertTrue(viewModel.uiState.value.snackbarState.isVisible)
        assertEquals(itemToDelete, viewModel.uiState.value.snackbarState.item)
    }

    @Test
    fun onDeleteAlert_afterDelay_deletesFromRepository() = runTest {
        val itemToDelete = viewModel.uiState.value.alerts[0]

        viewModel.onEvent(AlertsEvent.OnDeleteAlert(itemToDelete))

        advanceTimeBy(5000L)

        coVerify { alertRepository.deleteAlertById(itemToDelete.id) }
        coVerify { alertScheduler.cancel(itemToDelete.id) }
    }

    @Test
    fun onDeleteAlert_deleteTwiceQuickly_firstItemDeletedImmediately() = runTest {
        val firstItem = viewModel.uiState.value.alerts[0]
        val secondItem = viewModel.uiState.value.alerts[1]

        viewModel.onEvent(AlertsEvent.OnDeleteAlert(firstItem))
        viewModel.onEvent(AlertsEvent.OnDeleteAlert(secondItem))

        coVerify { alertRepository.deleteAlertById(firstItem.id) }
        coVerify { alertScheduler.cancel(firstItem.id) }
    }

    @Test
    fun onToggleAlert_disableAlert_cancelsScheduler() = runTest {
        viewModel.onEvent(
            AlertsEvent.OnToggleAlert(id = 1, isEnabled = false)
        )

        coVerify { alertRepository.updateAlertEnabled(1, false) }
        coVerify { alertScheduler.cancel(1) }
    }

    @Test
    fun onToggleAlert_enableAlert_reschedulesAlarm() = runTest {
        viewModel.onEvent(
            AlertsEvent.OnToggleAlert(id = 1, isEnabled = true)
        )

        coVerify { alertRepository.updateAlertEnabled(1, true) }
        coVerify { alertScheduler.schedule(any()) }
    }

    @Test
    fun onToggleAlert_disableAlert_updatesUiState() = runTest {
        viewModel.onEvent(
            AlertsEvent.OnToggleAlert(id = 1, isEnabled = false)
        )

        val updatedItem = viewModel.uiState.value.alerts.find { it.id == 1 }
        assertNotNull(updatedItem)
        assertFalse(updatedItem!!.isEnabled)
    }
}