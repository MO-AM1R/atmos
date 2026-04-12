package com.example.atmos.utils

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.mapbox.geojson.Point
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
suspend fun requestLocation(
    fusedClient: FusedLocationProviderClient,
    timeoutMillis: Long = 10_000L,
    onLocationReady: (Location) -> Unit,
    onTimeout: () -> Unit = {}
) {
    val cancellationTokenSource = CancellationTokenSource()

    try {
        withTimeout(timeoutMillis) {
            val location = suspendCancellableCoroutine { continuation ->
                fusedClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                ).addOnSuccessListener { location ->
                    continuation.resume(location)
                }.addOnFailureListener { _ ->
                    continuation.resume(null)
                }

                continuation.invokeOnCancellation {
                    cancellationTokenSource.cancel()
                }
            }

            if (location != null) {
                onLocationReady(location)
            } else {
                onTimeout()
            }
        }
    } catch (_: TimeoutCancellationException) {
        cancellationTokenSource.cancel()
        onTimeout()
    }
}


@SuppressLint("MissingPermission")
suspend fun getCurrentLocation(
    context: Context,
    timeoutMillis: Long = 10_000L
): Point? {
    val client = LocationServices.getFusedLocationProviderClient(context)
    val cancellationTokenSource = CancellationTokenSource()

    return try {
        withTimeout(timeoutMillis) {
            suspendCancellableCoroutine { continuation ->
                client.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                ).addOnSuccessListener { location ->
                    if (location != null) {
                        continuation.resume(
                            Point.fromLngLat(location.longitude, location.latitude)
                        )
                    } else {
                        continuation.resume(null)
                    }
                }.addOnFailureListener {
                    continuation.resume(null)
                }

                continuation.invokeOnCancellation {
                    cancellationTokenSource.cancel()
                }
            }
        }
    } catch (_: TimeoutCancellationException) {
        cancellationTokenSource.cancel()
        null
    }
}