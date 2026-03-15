package com.example.atmos.ui.home

import android.Manifest
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.ui.core.components.rememberPermissionHandler
import com.example.atmos.ui.core.viewmodel.LocationPermissionEvent
import com.example.atmos.ui.core.viewmodel.LocationPermissionViewModel
import com.example.atmos.ui.home.components.HomeContent
import com.example.atmos.ui.home.components.HomeDialogs
import com.example.atmos.ui.home.state.HomeEvent
import com.example.atmos.ui.home.state.HomeScreenState
import com.example.atmos.ui.home.viewmodel.HomeScreenViewModel
import com.example.atmos.utils.hasLocationPermission
import com.example.atmos.utils.openAppSettings
import com.example.atmos.utils.openGpsSettings
import com.example.atmos.utils.requestLocation
import com.google.android.gms.location.LocationServices


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    permissionViewModel: LocationPermissionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState by permissionViewModel.permissionState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val fusedClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val isScrolledPastThreshold by remember {
        derivedStateOf { scrollState.value > 100 }
    }

    val blurRadius by animateDpAsState(
        targetValue = if (isScrolledPastThreshold) 0.dp else 12.dp,
        animationSpec = tween(durationMillis = 400),
        label = "blurRadius"
    )

    fun startLocationAndLoad() {
        requestLocation(
            fusedClient = fusedClient,
            onLocationReady = { resolvedLocation ->
                viewModel.onEvent(HomeEvent.OnLocationUpdated(resolvedLocation))
                viewModel.onEvent(
                    HomeEvent.OnLoad(
                        latitude = resolvedLocation.latitude,
                        longitude = resolvedLocation.longitude
                    )
                )
            }
        )
    }

    fun retryWithLocation(forceUpdate: Boolean = false) {
        uiState.location?.let {
            viewModel.onEvent(
                HomeEvent.OnLoad(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    forceUpdate = forceUpdate
                )
            )
        } ?: startLocationAndLoad()
    }

    val locationPermissionLauncher = rememberPermissionHandler(
        onPermissionGranted = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionGranted)
            if (permissionState.isGpsEnabled) {
                startLocationAndLoad()
            }
        },
        onPermissionDenied = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionDenied)
        },
        onPermanentlyDenied = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionPermanentlyDenied)
        }
    )

    LaunchedEffect(
        permissionState.isPermissionGranted,
        permissionState.isGpsEnabled,
        uiState.isConnected
    ) {
        when {
            !context.hasLocationPermission() -> {
                locationPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            else -> {
                if (permissionState.isGpsEnabled) {
                    if (uiState.isConnected) {
                        if (!uiState.isDataLoaded) {
                            retryWithLocation()
                        }
                    } else {
                        viewModel.setScreenState(HomeScreenState.NetworkUnavailable)
                    }
                }
            }
        }
    }

    HomeDialogs(
        showPermissionRationaleDialog = permissionState.showPermissionRationaleDialog,
        showPermanentDenyDialog = permissionState.showPermanentDenyDialog,
        onRationaleConfirm = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnRationaleConfirmed)
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        },
        onRationaleDismiss = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnRationaleDismissed)
            viewModel.setScreenState(HomeScreenState.LocationPermission)
        },
        onPermanentConfirm = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermanentDenyConfirmed)
            context.openAppSettings()
        },
        onPermanentDismiss = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermanentDenyDismissed)
            viewModel.setScreenState(HomeScreenState.LocationPermission)
        }
    )

    HomeContent(
        isGpsEnabled = permissionState.isGpsEnabled,
        uiState = uiState,
        scrollState = scrollState,
        blurRadius = blurRadius,
        onRetry = { retryWithLocation() },
        onRefresh = { retryWithLocation(true) },
        onRequestPermission = {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        },
        onOpenSettings = { context.openAppSettings() },
        onOpenGpsSettings = { context.openGpsSettings() }
    )
}