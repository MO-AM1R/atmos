package com.example.atmos.ui.map.state
import com.example.atmos.utils.AppConstants.MapConstants
import com.mapbox.geojson.Point


data class MapScreenUIState(
    val isLoading: Boolean = true,
    val myLocation: Point? = null,
    val selectedPoint: Point? = null,
    val error: String? = null,
    val initialZoom: Double = MapConstants.DEFAULT_ZOOM,
    val initialPitch: Double = 45.0,
    val initialBearing: Double = 0.0,
    val screenState: MapScreenState = MapScreenState.Loading,
    val mapSearchEngineState: MapSearchEngineState = MapSearchEngineState(),
)


sealed class MapScreenState{
    object Loading : MapScreenState()
    object LocationPermission : MapScreenState()
    object NetworkUnavailable : MapScreenState()
    object Success : MapScreenState()
    data class Error(val message: String) : MapScreenState()
}