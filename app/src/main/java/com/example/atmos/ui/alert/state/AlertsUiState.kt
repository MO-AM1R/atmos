package com.example.atmos.ui.alert.state

import com.example.atmos.domain.model.AlertUiItem


data class AlertsUiState(
    val alerts: List<AlertUiItem> = emptyList(),
    val state: AlertsState = AlertsState.Loading,
    val snackbarState: AlertSnackbarState = AlertSnackbarState()
)

sealed class AlertsState {
    object Loading : AlertsState()
    object Success : AlertsState()
    data class Error(val message: String) : AlertsState()
}

data class AlertSnackbarState(
    val isVisible: Boolean = false,
    val item: AlertUiItem? = null,
)