package com.example.atmos.ui.home.state

import android.location.Location

sealed class HomeEvent {
    data class OnLoad(
        val latitude : Double,
        val longitude: Double,
        val forceUpdate: Boolean = false
    ) : HomeEvent()

    data class OnLocationUpdated(
        val location: Location
    ) : HomeEvent()
}