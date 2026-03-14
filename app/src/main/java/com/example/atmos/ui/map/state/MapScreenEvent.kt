package com.example.atmos.ui.map.state

import com.mapbox.geojson.Point

sealed class MapScreenEvent {
    object OnLoadingCurrentLocation: MapScreenEvent()
    object ClearSearch: MapScreenEvent()
    object OnBack: MapScreenEvent()
    data class OnSelectPoint(val point: Point): MapScreenEvent()
    data class OnLoadedCurrentLocation(val point: Point?): MapScreenEvent()
    data class OnSave(val point: Point): MapScreenEvent()
    data class OnSelectSearchItem(val name: String): MapScreenEvent()
    data class OnQueryChanged(val newQuery: String): MapScreenEvent()
}