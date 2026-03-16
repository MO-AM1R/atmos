package com.example.atmos.ui.alert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.data.workers.AlertScheduler
import com.example.atmos.domain.model.Alert
import com.example.atmos.domain.model.AlertUiItem
import com.example.atmos.domain.model.toUiItem
import com.example.atmos.domain.repository.AlertRepository
import com.example.atmos.ui.alert.state.AlertSnackbarState
import com.example.atmos.ui.alert.state.AlertsEvent
import com.example.atmos.ui.alert.state.AlertsState
import com.example.atmos.ui.alert.state.AlertsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val alertRepository: AlertRepository,
    private val alertScheduler: AlertScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlertsUiState())
    val uiState: StateFlow<AlertsUiState> = _uiState.asStateFlow()

    private var pendingDeleteItem: AlertUiItem? = null
    private var deleteJob: Job? = null

    init {
        observeAlerts()
    }

    private fun observeAlerts() {
        viewModelScope.launch {
            alertRepository.getAllAlerts().collect { alerts ->
                val filtered = alerts.filter { it.id != pendingDeleteItem?.id }

                _uiState.update {
                    it.copy(
                        alerts = filtered.map { alert -> alert.toUiItem() },
                        state = AlertsState.Success
                    )
                }
            }
        }
    }

    fun onEvent(event: AlertsEvent) {
        when (event) {
            is AlertsEvent.OnAddAlert -> onAddAlert(event)
            is AlertsEvent.OnDeleteAlert -> onDeleteAlert(event.alert)
            is AlertsEvent.OnUndoDelete -> onUndoDelete(event.alert)
            is AlertsEvent.OnToggleAlert -> onToggleAlert(event.id, event.isEnabled)
            AlertsEvent.OnDismissSnackbar -> dismissSnackbar()
        }
    }

    private fun onAddAlert(event: AlertsEvent.OnAddAlert) {
        viewModelScope.launch {
            val alert = Alert(
                id = 0,
                startTimeMs = event.startTimeMs,
                endTimeMs = event.endTimeMs,
                type = event.type,
                isEnabled = true,
                isExpired = false
            )

            val id = alertRepository.insertAlert(alert)
            alertScheduler.schedule(alert.copy(id = id.toInt()))
        }
    }

    private fun onDeleteAlert(item: AlertUiItem) {
        deleteJob?.cancel()
        pendingDeleteItem?.let { previousItem ->
            viewModelScope.launch {
                alertScheduler.cancel(previousItem.id)
                alertRepository.deleteAlertById(previousItem.id)
            }
            pendingDeleteItem = null
        }

        _uiState.update { state ->
            state.copy(
                alerts = state.alerts.filter { it.id != item.id },
                snackbarState = AlertSnackbarState(isVisible = true, item = item)
            )
        }

        pendingDeleteItem = item

        deleteJob = viewModelScope.launch {
            delay(4000L)
            alertScheduler.cancel(item.id)
            alertRepository.deleteAlertById(item.id)
            pendingDeleteItem = null
            dismissSnackbar()
        }
    }

    private fun onUndoDelete(item: AlertUiItem) {
        deleteJob?.cancel()
        pendingDeleteItem = null

        _uiState.update { state ->
            state.copy(
                alerts = (state.alerts + item).sortedBy { it.startTimeMs },
                snackbarState = AlertSnackbarState()
            )
        }
    }

    private fun onToggleAlert(id: Int, isEnabled: Boolean) {
        viewModelScope.launch {
            alertRepository.updateAlertEnabled(id, isEnabled)

            _uiState.update { state ->
                state.copy(
                    alerts = state.alerts.map { item ->
                        if (item.id == id) item.copy(isEnabled = isEnabled) else item
                    }
                )
            }

            if (!isEnabled) {
                alertScheduler.cancel(id)
            } else {
                val alert = alertRepository.getAlertById(id)
                alert?.let { alertScheduler.schedule(it) }
            }
        }
    }

    private fun dismissSnackbar() {
        _uiState.update { it.copy(snackbarState = AlertSnackbarState()) }
    }
}