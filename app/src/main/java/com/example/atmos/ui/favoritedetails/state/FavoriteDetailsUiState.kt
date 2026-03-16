package com.example.atmos.ui.favoritedetails.state

import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.model.UserPreferences

data class FavoriteDetailsUiState(
    val currentWeather: CurrentWeather? = null,
    val forecastDays: Forecast? = null,
    val point: StoredPoint? = null,
    val userPreferences: UserPreferences? = null,
    val isLoading: Boolean = false,
    val isDataLoaded: Boolean = false,
    val error: String? = null,
    val screenState: FavoriteDetailsScreenState = FavoriteDetailsScreenState.Loading
)

sealed class FavoriteDetailsScreenState {
    object Loading : FavoriteDetailsScreenState()
    object Success : FavoriteDetailsScreenState()
    data class Error(val message: String) : FavoriteDetailsScreenState()
}