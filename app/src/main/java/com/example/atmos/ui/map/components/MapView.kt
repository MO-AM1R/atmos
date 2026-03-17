package com.example.atmos.ui.map.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.atmos.R
import com.example.atmos.utils.AppConstants.MapConstants
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage

@Composable
fun MapView(
    selectedPoint: Point?,
    currentLocationPoint: Point?,
    mapViewportState: MapViewportState,
    onPointSelected: (Point) -> Boolean
) {
    val selectedMarker = rememberIconImage(
        key = R.drawable.ic_location_point,
        painter = painterResource(R.drawable.ic_location_point)
    )

    MapboxMap(
        modifier = Modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
        scaleBar = {},
        compass = {},
        logo = {},
        attribution = {},
        onMapLongClickListener = onPointSelected
    ) {
        selectedPoint?.let { point ->
            PointAnnotation(point = point) {
                iconImage = selectedMarker
                iconSize = MapConstants.MARKER_SIZE
                iconAnchor =
                    com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor.BOTTOM
            }
        }

        currentLocationPoint?.let { point ->
            CircleAnnotation(point = point) {
                circleRadius = 10.0
                circleColor = Color(0xFF2196F3)
                circleStrokeWidth = 3.0
                circleStrokeColor = Color.White
                circleOpacity = 0.9
            }
        }
    }
}