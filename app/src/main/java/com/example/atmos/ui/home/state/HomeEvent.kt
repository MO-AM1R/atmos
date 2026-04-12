package com.example.atmos.ui.home.state

import com.example.atmos.domain.model.StoredPoint

sealed class HomeEvent {
    data class OnLoad(
        val point: StoredPoint,
        val forceUpdate: Boolean = false
    ) : HomeEvent()

    object OnLocationTimeout : HomeEvent()
}

sealed class HomeUIEvents {
    object TriggerGPSLocation : HomeUIEvents()
}