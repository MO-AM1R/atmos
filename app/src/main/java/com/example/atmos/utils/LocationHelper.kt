package com.example.atmos.utils

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority


@SuppressLint("MissingPermission")
fun requestLocation(
    fusedClient: FusedLocationProviderClient,
    onLocationReady: (Location) -> Unit
) {
    fusedClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener {
        it?.let(onLocationReady)
    }
}