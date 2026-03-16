package com.example.atmos.ui.home.state

import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.model.UserPreferences


data class HomeUiState(
    val currentWeather: CurrentWeather? = null,
    val forecastDays: Forecast? = null,
    val point: StoredPoint? = null,
    val userPreferences: UserPreferences? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isConnected: Boolean = true,
    val isDataLoaded: Boolean = false,
    val error: String? = null,
    val screenState: HomeScreenState = HomeScreenState.Loading
)


sealed class HomeScreenState {
    object Loading : HomeScreenState()
    object LocationPermission : HomeScreenState()
    object NetworkUnavailable : HomeScreenState()
    object Success : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}