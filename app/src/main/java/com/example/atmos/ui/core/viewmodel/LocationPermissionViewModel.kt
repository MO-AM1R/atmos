package com.example.atmos.ui.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.utils.GpsMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class LocationPermissionViewModel @Inject constructor(
    private val gpsMonitor: GpsMonitor
) : ViewModel() {

    private val _permissionState = MutableStateFlow(LocationPermissionState())
    val permissionState: StateFlow<LocationPermissionState> = _permissionState.asStateFlow()

    init {
        observeGpsStatus()
    }

    private fun observeGpsStatus() {
        viewModelScope.launch {
            gpsMonitor.gpsStatus.collect { isEnabled ->
                _permissionState.update {
                    it.copy(isGpsEnabled = isEnabled)
                }
            }
        }
    }

    fun onEvent(event: LocationPermissionEvent) {
        when (event) {
            is LocationPermissionEvent.OnPermissionGranted -> {
                _permissionState.update {
                    it.copy(
                        isPermissionGranted = true,
                        showPermissionRationaleDialog = false,
                        showPermanentDenyDialog = false
                    )
                }
            }

            is LocationPermissionEvent.OnPermissionDenied -> {
                _permissionState.update {
                    it.copy(showPermissionRationaleDialog = true)
                }
            }

            is LocationPermissionEvent.OnPermissionPermanentlyDenied -> {
                _permissionState.update {
                    it.copy(showPermanentDenyDialog = true)
                }
            }

            is LocationPermissionEvent.OnRationaleConfirmed -> {
                _permissionState.update {
                    it.copy(showPermissionRationaleDialog = false)
                }
            }

            is LocationPermissionEvent.OnRationaleDismissed -> {
                _permissionState.update {
                    it.copy(showPermissionRationaleDialog = false)
                }
            }

            is LocationPermissionEvent.OnPermanentDenyConfirmed -> {
                _permissionState.update {
                    it.copy(showPermanentDenyDialog = false)
                }
            }

            is LocationPermissionEvent.OnPermanentDenyDismissed -> {
                _permissionState.update {
                    it.copy(showPermanentDenyDialog = false)
                }
            }
        }
    }
}


data class LocationPermissionState(
    val isPermissionGranted: Boolean = true,
    val isGpsEnabled: Boolean = true,
    val showPermissionRationaleDialog: Boolean = false,
    val showPermanentDenyDialog: Boolean = false,
)

sealed class LocationPermissionEvent {
    object OnPermissionGranted : LocationPermissionEvent()
    object OnPermissionDenied : LocationPermissionEvent()
    object OnPermissionPermanentlyDenied : LocationPermissionEvent()
    object OnRationaleConfirmed : LocationPermissionEvent()
    object OnRationaleDismissed : LocationPermissionEvent()
    object OnPermanentDenyConfirmed : LocationPermissionEvent()
    object OnPermanentDenyDismissed : LocationPermissionEvent()
}