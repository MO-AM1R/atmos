package com.example.atmos.ui.home

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.ui.core.components.rememberPermissionHandler
import com.example.atmos.ui.core.viewmodel.LocationPermissionEvent
import com.example.atmos.ui.core.viewmodel.LocationPermissionViewModel
import com.example.atmos.ui.home.components.HomeContent
import com.example.atmos.ui.home.components.HomeDialogs
import com.example.atmos.ui.home.state.HomeEvent
import com.example.atmos.ui.home.state.HomeScreenState
import com.example.atmos.ui.home.state.HomeUIEvents
import com.example.atmos.ui.home.viewmodel.HomeScreenViewModel
import com.example.atmos.utils.hasLocationPermission
import com.example.atmos.utils.openAppSettings
import com.example.atmos.utils.openGpsSettings
import com.example.atmos.utils.requestLocation
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    permissionViewModel: LocationPermissionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState by permissionViewModel.permissionState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val fusedClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    fun startLocationAndLoad(forceUpdate: Boolean = false) {
        scope.launch {
            requestLocation(
                fusedClient = fusedClient,
                onLocationReady = { resolvedLocation ->
                    viewModel.onEvent(
                        HomeEvent.OnLoad(
                            point = StoredPoint(
                                latitude = resolvedLocation.latitude,
                                longitude = resolvedLocation.longitude
                            ),
                            forceUpdate = forceUpdate
                        )
                    )
                },
                onTimeout = {
                    viewModel.onEvent(HomeEvent.OnLocationTimeout)
                }
            )
        }
    }

    fun retryWithLocation(forceUpdate: Boolean = false) {
        when {
            uiState.point != null -> {
                viewModel.onEvent(
                    HomeEvent.OnLoad(
                        point = uiState.point!!,
                        forceUpdate = forceUpdate
                    )
                )
            }

            else -> startLocationAndLoad(forceUpdate = forceUpdate)
        }
    }

    val locationPermissionLauncher = rememberPermissionHandler(
        onPermissionGranted = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionGranted)
        },
        onPermissionDenied = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionDenied)
        },
        onPermanentlyDenied = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionPermanentlyDenied)
        }
    )

    LaunchedEffect(Unit) {
        if (!context.hasLocationPermission()) {
            locationPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    LaunchedEffect(
        permissionState.isPermissionGranted,
        permissionState.isGpsEnabled,
        uiState.isDataLoaded
    ) {
        when {
            !permissionState.isPermissionGranted -> {
                if (!permissionState.showPermissionRationaleDialog &&
                    !permissionState.showPermanentDenyDialog
                ) {
                    viewModel.setScreenState(HomeScreenState.LocationPermission)
                }
            }

            !permissionState.isGpsEnabled -> {
                viewModel.setScreenState(HomeScreenState.GpsDisabled)
            }

            uiState.screenState.shouldTriggerLocationLoad() -> {
                startLocationAndLoad()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.homeUIEvens.collect { event ->
            when (event) {
                HomeUIEvents.TriggerGPSLocation -> {
                    if (permissionState.isGpsEnabled &&
                        context.hasLocationPermission()
                    ) {
                        startLocationAndLoad(forceUpdate = true)
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
        userPreferencesState = uiState.userPreferences,
        uiState = uiState,
        onRetry = { retryWithLocation() },
        onRequestPermission = {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        },
        onOpenSettings = { context.openAppSettings() },
        onOpenGpsSettings = { context.openGpsSettings() }
    )
}