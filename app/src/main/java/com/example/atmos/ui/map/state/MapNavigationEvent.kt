package com.example.atmos.ui.map.state

import com.mapbox.geojson.Point

sealed class MapNavigationEvent {
    data class SaveAndGoBack(val point: Point) : MapNavigationEvent()
    data object GoBack                         : MapNavigationEvent()
}