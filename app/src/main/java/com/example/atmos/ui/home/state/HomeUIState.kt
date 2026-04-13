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
    val isRefreshing: Boolean = false,
    val isConnected: Boolean = true,
    val isDataLoaded: Boolean = false,
    val screenState: HomeScreenState = HomeScreenState.Initial
) {
    val isLoading: Boolean
        get() = screenState is HomeScreenState.Loading

}


sealed class HomeScreenState {
    object Initial : HomeScreenState()
    object Loading : HomeScreenState()
    object LocationPermission : HomeScreenState()
    object GpsDisabled : HomeScreenState()
    object NetworkUnavailable : HomeScreenState()
    object Success : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()

    val isDataRelated: Boolean
        get() = when (this) {
            is Loading, is Success, is Error, is NetworkUnavailable -> true
            is Initial, is LocationPermission, is GpsDisabled -> false
        }

    fun shouldTriggerLocationLoad(): Boolean {
        return when (this) {
            is Initial,
            is GpsDisabled,
            is LocationPermission -> true

            is Loading,
            is Success,
            is NetworkUnavailable,
            is Error -> false
        }
    }
}