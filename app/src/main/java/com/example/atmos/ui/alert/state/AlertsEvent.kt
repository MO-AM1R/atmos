package com.example.atmos.ui.alert.state

import com.example.atmos.data.enums.AlertType
import com.example.atmos.domain.model.AlertUiItem

sealed class AlertsEvent {
    data class OnAddAlert(
        val startTimeMs: Long,
        val endTimeMs: Long?,
        val type: AlertType
    ) : AlertsEvent()

    data class OnDeleteAlert(val alert: AlertUiItem) : AlertsEvent()
    data class OnUndoDelete(val alert: AlertUiItem) : AlertsEvent()
    data class OnToggleAlert(val id: Int, val isEnabled: Boolean) : AlertsEvent()
    data object OnDismissSnackbar : AlertsEvent()
}