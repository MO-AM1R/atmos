package com.example.atmos.ui.favorites.state

import com.example.atmos.domain.model.FavoriteWeatherItem

sealed class FavoritesEvent {
    data class OnAddFavorite(val lat: Double, val lon: Double) : FavoritesEvent()
    data class OnDeleteFavorite(val item: FavoriteWeatherItem) : FavoritesEvent()
    data class OnUndoDelete(val item: FavoriteWeatherItem) : FavoritesEvent()
    data object OnDismissSnackbar: FavoritesEvent()
}
