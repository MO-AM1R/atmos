package com.example.atmos.ui.home.state

import android.location.Location
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast


data class HomeUiState(
    val currentWeather: CurrentWeather? = null,
    val forecastDays: Forecast? = null,
    val location: Location? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isConnected: Boolean = true,
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