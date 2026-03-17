package com.example.atmos.ui.map.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.ui.map.state.MapScreenEvent
import com.example.atmos.ui.map.state.MapScreenUIState
import com.example.atmos.ui.theme.extraColors
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

@Composable
fun MapSuccessContent(
    modifier: Modifier = Modifier,
    state: MapScreenUIState,
    mapViewportState: MapViewportState,
    onMyLocationClick: () -> Unit,
    onEvent: (MapScreenEvent) -> Unit,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val appBarHeight = screenHeight * 0.15f

        Box(modifier = Modifier.fillMaxSize()) {

            MapView(
                selectedPoint = state.selectedPoint,
                currentLocationPoint = state.myLocation,
                mapViewportState = mapViewportState,
                onPointSelected = { point ->
                    onEvent(MapScreenEvent.OnSelectPoint(point))
                    true
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 40.dp, bottom = 0.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1E1B2E))
                        .size(appBarHeight * 0.4f)
                        .clickable { onEvent(MapScreenEvent.OnBack) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        tint = Color.White.copy(alpha = 0.8f),
                        contentDescription = stringResource(R.string.description),
                        modifier = Modifier
                            .size(appBarHeight * 0.3f)
                            .padding(10.dp)
                    )
                }

                MapSearchBar(
                    modifier = Modifier.weight(1f),
                    mapSearchEngineState = state.mapSearchEngineState,
                    onEvent = onEvent,
                    onResultSelect = { point ->
                        if (point != null) {
                            onEvent(MapScreenEvent.OnSelectPoint(point))
                            mapViewportState.flyTo(
                                cameraOptions = cameraOptions {
                                    center(point)
                                    zoom(15.0)
                                    pitch(45.0)
                                }
                            )
                        }
                    }
                )
            }

            FloatingActionButton(
                onClick = onMyLocationClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 100.dp),
                containerColor = Color(0xFF1E1B2E),
                contentColor = MaterialTheme.extraColors.violet,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "My Location",
                    modifier = Modifier.size(24.dp)
                )
            }

            AnimatedVisibility(
                visible = state.selectedPoint != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Button(
                    onClick = {
                        state.selectedPoint?.let { point ->
                            onEvent(MapScreenEvent.OnSave(point))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6C63FF)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.map_save_location),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}