package com.example.atmos.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.ui.favorites.components.FavoriteCustomSnackbar
import com.example.atmos.ui.favorites.components.FavoriteHeader
import com.example.atmos.ui.favorites.components.FavoriteLocationItem
import com.example.atmos.ui.favorites.components.FavoriteLocationShimmer
import com.example.atmos.ui.favorites.components.FavoritesEmptyContent
import com.example.atmos.ui.favorites.state.FavoritesEvent
import com.example.atmos.ui.favorites.state.FavoritesState
import com.example.atmos.ui.favorites.viewmodel.FavoritesViewModel
import com.example.atmos.ui.onboarding.components.GradientBackground
import com.example.atmos.utils.AppConstants
import com.mapbox.geojson.Point

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
    onNavigateToMap: () -> Unit,
    savedStateHandle: SavedStateHandle,
    onNavigateToFavDetails: (Double, Double) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        savedStateHandle
            .getStateFlow<String?>(AppConstants.MapConstants.SELECTED_POINT_KEY, null)
            .collect { json ->
                json?.let {
                    val point = Point.fromJson(it)
                    viewModel.onEvent(
                        FavoritesEvent.OnAddFavorite(
                            lat = point.latitude(),
                            lon = point.longitude()
                        )
                    )
                    savedStateHandle.remove<String>(AppConstants.MapConstants.SELECTED_POINT_KEY)
                }
            }
    }

    Box(modifier = modifier.fillMaxSize()) {
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp, bottom = 110.dp)
            ) {
                FavoriteHeader(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onNavigateToMap = onNavigateToMap
                )

                Spacer(modifier = Modifier.height(24.dp))

                when (uiState.state) {
                    is FavoritesState.Error -> FavoritesEmptyContent()

                    FavoritesState.Loading -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(count = 5) {
                                FavoriteLocationShimmer()
                            }
                        }
                    }

                    FavoritesState.Success -> {
                        if (uiState.favorites.isEmpty()) {
                            FavoritesEmptyContent()
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                items(
                                    items = uiState.favorites,
                                    key = { it.id }
                                ) { item ->
                                    FavoriteLocationItem(
                                        item = item,
                                        temperatureUnit = uiState.userPreferences
                                            ?.temperatureUnitOption
                                            ?: TemperatureUnit.CELSIUS,
                                        onDelete = {
                                            viewModel.onEvent(
                                                FavoritesEvent.OnDeleteFavorite(item)
                                            )
                                        },
                                        onClick = onNavigateToFavDetails
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        FavoriteCustomSnackbar(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 120.dp),
            snackbarState = uiState.snackbarState,
            onUndo = { item ->
                viewModel.onEvent(FavoritesEvent.OnUndoDelete(item))
            },
            onDismiss = {
                viewModel.onEvent(FavoritesEvent.OnDismissSnackbar)
            },
        )
    }
}