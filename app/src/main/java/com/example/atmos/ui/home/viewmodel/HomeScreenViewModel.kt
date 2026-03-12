package com.example.atmos.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.utils.NetworkMonitor
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.ui.home.state.HomeEvent
import com.example.atmos.ui.home.state.HomeScreenState
import com.example.atmos.ui.home.state.HomeUiState
import com.example.atmos.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            networkMonitor.networkStatus.collect { isConnected ->
                _uiState.update { it.copy(isConnected = isConnected) }
                if (!isConnected) {
                    _uiState.update {
                        it.copy(screenState = HomeScreenState.NetworkUnavailable)
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLoad -> {
                loadWeather(
                    latitude = event.latitude,
                    longitude = event.longitude
                )
            }

            is HomeEvent.OnLocationUpdated -> {
                _uiState.update { it.copy(location = event.location) }
            }
        }
    }

    fun setScreenState(state: HomeScreenState) {
        _uiState.update { it.copy(screenState = state) }
    }

    private fun loadWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _uiState.update {
                Log.d("TAG", "Loading")
                it.copy(
                    isLoading = true,
                    error = null,
                    screenState = HomeScreenState.Loading
                )
            }
            Log.d("TAG", "${_uiState.value.screenState is HomeScreenState.Loading}")

            combine(
                weatherRepository.getCurrentWeather(latitude, longitude),
                weatherRepository.getForecast(latitude, longitude)
            ) { currentWeatherResource, forecastResource ->
                Pair(currentWeatherResource, forecastResource)
            }.collect { (currentWeatherResource, forecastResource) ->
                when {
                    currentWeatherResource is Resource.Loading
                            || forecastResource is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    currentWeatherResource is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                screenState = HomeScreenState.Error(
                                    currentWeatherResource.message
                                )
                            )
                        }
                    }

                    forecastResource is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                screenState = HomeScreenState.Error(
                                    forecastResource.message
                                )
                            )
                        }
                    }

                    currentWeatherResource is Resource.Success
                            && forecastResource is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                currentWeather = currentWeatherResource.data,
                                forecastDays = forecastResource.data,
                                isLoading = false,
                                screenState = HomeScreenState.Success
                            )
                        }
                    }
                }
            }
        }
    }
}