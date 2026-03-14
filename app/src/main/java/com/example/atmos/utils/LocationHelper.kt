package com.example.atmos.utils

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.mapbox.geojson.Point

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


@SuppressLint("MissingPermission")
fun getCurrentLocation(
    context : Context,
    onResult: (Point?) -> Unit
) {
    val client = LocationServices.getFusedLocationProviderClient(context)
    client.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    ).addOnSuccessListener { location ->
        if (location != null) {
            onResult(Point.fromLngLat(location.longitude, location.latitude))
        } else {
            onResult(null)
        }
    }.addOnFailureListener {
        onResult(null)
    }
}