package com.example.atmos.ui.favorites.state

import com.example.atmos.domain.model.FavoriteWeatherItem
import com.example.atmos.domain.model.UserPreferences

data class FavoritesUiState(
    val favorites: List<FavoriteWeatherItem> = emptyList(),
    val userPreferences: UserPreferences? = null,
    val state: FavoritesState = FavoritesState.Loading,
    val snackbarState: SnackbarState = SnackbarState()
)


sealed class FavoritesState {
    object Loading : FavoritesState()
    data class Error(val error: String) : FavoritesState()
    object Success : FavoritesState()
}

data class SnackbarState(
    val isVisible: Boolean = false,
    val item: FavoriteWeatherItem? = null
)