package com.example.atmos.ui.core.viewmodel

import androidx.lifecycle.ViewModel
import com.example.atmos.utils.NotificationPermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationPermissionViewModel @Inject constructor(
    private val notificationPermissionManager: NotificationPermissionManager
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationPermissionState())
    val state: StateFlow<NotificationPermissionState> = _state.asStateFlow()

    private val _openAddAlertSheet = MutableStateFlow(false)
    val openAddAlertSheet: StateFlow<Boolean> = _openAddAlertSheet.asStateFlow()

    fun onEvent(event: NotificationPermissionEvent) {
        when (event) {
            NotificationPermissionEvent.OnAddAlertClicked -> {
                if (notificationPermissionManager.isGranted()) {
                    // Permission already granted — open sheet directly
                    _openAddAlertSheet.update { true }
                } else {
                    // Need to request permission
                    _state.update {
                        it.copy(
                            isPermissionGranted = false,
                            triggerPermissionRequest = true
                        )
                    }
                }
            }

            NotificationPermissionEvent.OnPermissionRequestConsumed -> {
                _state.update { it.copy(triggerPermissionRequest = false) }
            }

            NotificationPermissionEvent.OnPermissionGranted -> {
                _state.update {
                    it.copy(
                        isPermissionGranted = true,
                        showPermissionRationaleDialog = false,
                        showPermanentDenyDialog = false
                    )
                }
                _openAddAlertSheet.update { true }
            }

            NotificationPermissionEvent.OnPermissionDenied -> {
                _state.update {
                    it.copy(
                        isPermissionGranted = false,
                        showPermissionRationaleDialog = true
                    )
                }
            }

            NotificationPermissionEvent.OnPermissionPermanentlyDenied -> {
                _state.update {
                    it.copy(
                        isPermissionGranted = false,
                        showPermanentDenyDialog = true
                    )
                }
            }

            NotificationPermissionEvent.OnRationaleConfirmed -> {
                _state.update {
                    it.copy(
                        showPermissionRationaleDialog = false,
                        triggerPermissionRequest = true
                    )
                }
            }

            NotificationPermissionEvent.OnRationaleDismissed -> {
                _state.update { it.copy(showPermissionRationaleDialog = false) }
            }

            NotificationPermissionEvent.OnPermanentDenyConfirmed -> {
                _state.update { it.copy(showPermanentDenyDialog = false) }
            }

            NotificationPermissionEvent.OnPermanentDenyDismissed -> {
                _state.update { it.copy(showPermanentDenyDialog = false) }
            }
        }
    }

    fun onAlertSheetConsumed() {
        _openAddAlertSheet.update { false }
    }
}


data class NotificationPermissionState(
    val isPermissionGranted: Boolean = true,
    val showPermissionRationaleDialog: Boolean = false,
    val showPermanentDenyDialog: Boolean = false,
    val triggerPermissionRequest: Boolean = false
)

sealed class NotificationPermissionEvent {
    object OnAddAlertClicked : NotificationPermissionEvent()
    object OnPermissionGranted : NotificationPermissionEvent()
    object OnPermissionDenied : NotificationPermissionEvent()
    object OnPermissionPermanentlyDenied : NotificationPermissionEvent()
    object OnRationaleConfirmed : NotificationPermissionEvent()
    object OnRationaleDismissed : NotificationPermissionEvent()
    object OnPermanentDenyConfirmed : NotificationPermissionEvent()
    object OnPermanentDenyDismissed : NotificationPermissionEvent()
    object OnPermissionRequestConsumed : NotificationPermissionEvent()
}