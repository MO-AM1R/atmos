package com.example.atmos.ui.map

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.atmos.ui.core.components.GpsDisabledContent
import com.example.atmos.ui.core.components.LocationPermissionContent
import com.example.atmos.ui.core.components.NetworkUnavailableContent
import com.example.atmos.ui.core.components.rememberPermissionHandler
import com.example.atmos.ui.core.viewmodel.LocationPermissionEvent
import com.example.atmos.ui.core.viewmodel.LocationPermissionViewModel
import com.example.atmos.ui.core.viewmodel.NetworkViewModel
import com.example.atmos.ui.map.components.MapErrorContent
import com.example.atmos.ui.map.components.MapLoadingOverlay
import com.example.atmos.ui.map.components.MapSuccessContent
import com.example.atmos.ui.map.state.MapNavigationEvent
import com.example.atmos.ui.map.state.MapScreenEvent
import com.example.atmos.ui.map.state.MapScreenState
import com.example.atmos.ui.map.viewmodel.MapScreenViewModel
import com.example.atmos.utils.AppConstants.MapConstants
import com.example.atmos.utils.getCurrentLocation
import com.example.atmos.utils.hasLocationPermission
import com.example.atmos.utils.openAppSettings
import com.example.atmos.utils.openGpsSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mapbox.geojson.Point
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.animation.MapAnimationOptions


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    permissionViewModel: LocationPermissionViewModel = hiltViewModel(),
    networkViewModel: NetworkViewModel = hiltViewModel(),
    mapScreenViewModel: MapScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val permissionState by permissionViewModel.permissionState.collectAsStateWithLifecycle()
    val networkState by networkViewModel.isConnected.collectAsStateWithLifecycle()
    val mapState by mapScreenViewModel.mapState.collectAsStateWithLifecycle()


    suspend fun observeOnNavigation() {
        mapScreenViewModel.mapNavigationEvent.collect { event ->
            when (event) {
                MapNavigationEvent.GoBack -> {
                    navController.popBackStack()
                }

                is MapNavigationEvent.SaveAndGoBack -> {
                    navController.previousBackStackEntry
                        ?.savedStateHandle?.set(
                            MapConstants.SELECTED_POINT_KEY,
                            event.point.toJson()
                        )

                    navController.popBackStack()
                }
            }
        }
    }

    val mapViewportState = rememberMapViewportState {}

    fun flyToLocation(point: Point) {
        mapViewportState.flyTo(
            cameraOptions = cameraOptions {
                center(point)
                zoom(mapState.initialZoom)
                pitch(mapState.initialPitch)
                bearing(mapState.initialBearing)
            },
            animationOptions = MapAnimationOptions.mapAnimationOptions { duration(1000) }
        )
    }

    fun fetchAndFlyToMyLocation() {
        getCurrentLocation(context) { point ->
            point?.let {
                mapScreenViewModel.onEvent(MapScreenEvent.OnLoadedCurrentLocation(it))
                flyToLocation(it)
            }
        }
    }

    fun moveToMyLocation() {
        val cached = mapState.myLocation
        if (cached != null) {
            flyToLocation(cached)
        } else {
            fetchAndFlyToMyLocation()
        }
    }

    val locationPermissionLauncher = rememberPermissionHandler(
        onPermissionGranted = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionGranted)
            if (permissionState.isGpsEnabled) {
                moveToMyLocation()
            }
        },
        onPermissionDenied = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionDenied)
        },
        onPermanentlyDenied = {
            permissionViewModel.onEvent(LocationPermissionEvent.OnPermissionPermanentlyDenied)
        }
    )

    LaunchedEffect(permissionState.isPermissionGranted, networkState) {
        when {
            !context.hasLocationPermission() -> {
                locationPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }

            !networkState -> {
                mapScreenViewModel.setScreenState(MapScreenState.NetworkUnavailable)
            }

            permissionState.isGpsEnabled -> {
                moveToMyLocation()
            }
        }
    }

    LaunchedEffect(mapState.myLocation) {
        mapState.myLocation?.let { point ->
            flyToLocation(point)
        }
    }

    LaunchedEffect(Unit) {
        observeOnNavigation()
    }


    if (permissionState.isGpsEnabled) {
        when (mapState.screenState) {
            is MapScreenState.Error -> MapErrorContent(
                onRetry = { moveToMyLocation() }
            )

            MapScreenState.LocationPermission -> LocationPermissionContent(
                onRequestPermission = {
                    locationPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                },
                onOpenSettings = { context.openAppSettings() },
            )

            MapScreenState.NetworkUnavailable -> NetworkUnavailableContent()

            MapScreenState.Loading,
            MapScreenState.Success ->
                Box(modifier = modifier.fillMaxSize()) {
                    MapSuccessContent(
                        state = mapState,
                        mapViewportState = mapViewportState,
                        onMyLocationClick = { moveToMyLocation() },
                        onEvent = mapScreenViewModel::onEvent
                    )

                    MapLoadingOverlay(
                        isVisible = mapState.screenState == MapScreenState.Loading
                    )
                }
        }
    } else {
        GpsDisabledContent(
            onOpenGpsSettings = { context.openGpsSettings() }
        )
    }
}







