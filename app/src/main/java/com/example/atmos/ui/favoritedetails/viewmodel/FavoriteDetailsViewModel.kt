package com.example.atmos.ui.favoritedetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.ui.favoritedetails.state.FavoriteDetailsEvent
import com.example.atmos.ui.favoritedetails.state.FavoriteDetailsScreenState
import com.example.atmos.ui.favoritedetails.state.FavoriteDetailsUiState
import com.example.atmos.utils.Resource
import com.example.atmos.utils.ReverseGeocodingHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteDetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val reverseGeocoding: ReverseGeocodingHelper,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteDetailsUiState())
    val uiState: StateFlow<FavoriteDetailsUiState> = _uiState.asStateFlow()

    private var weatherJob: Job? = null

    init {
        viewModelScope.launch {
            val prefs = userPreferencesRepository.getUserPreferences().first()
            _uiState.update { it.copy(userPreferences = prefs) }
        }
    }

    fun loadWeatherForPoint(point: StoredPoint) {
        weatherJob?.cancel()

        weatherJob = viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    point = point,
                    screenState = FavoriteDetailsScreenState.Loading
                )
            }

            val prefs = userPreferencesRepository
                .getUserPreferences()
                .first()

            _uiState.update { it.copy(userPreferences = prefs) }

            val lang = prefs.languageOption.apiValue

            fetchWeather(point = point, lang = lang)
        }
    }

    fun onEvent(event: FavoriteDetailsEvent) {
        when (event) {
            is FavoriteDetailsEvent.OnLoad -> {
                loadWeatherForPoint(point = event.point)
            }
        }
    }

    fun setScreenState(state: FavoriteDetailsScreenState) {
        _uiState.update { it.copy(screenState = state) }
    }

    private suspend fun fetchWeather(
        point: StoredPoint,
        lang: String
    ) {
        val locationNameDeferred = viewModelScope.async {
            reverseGeocoding.getLocationName(point)
                ?: "${point.latitude}, ${point.longitude}"
        }

        combine(
            weatherRepository.getWeatherForPoint(
                lat = point.latitude,
                lon = point.longitude,
                lang = lang,
            ),
            weatherRepository.getForecastForPoint(
                lat = point.latitude,
                lon = point.longitude,
                lang = lang,
            )
        ) { weatherResource, forecastResource ->
            Pair(weatherResource, forecastResource)

        }.collect { (weatherResource, forecastResource) ->
            when {
                weatherResource is Resource.Loading ||
                        forecastResource is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                weatherResource is Resource.Error -> {
                    locationNameDeferred.cancel()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            screenState = FavoriteDetailsScreenState.Error(
                                weatherResource.message
                            )
                        )
                    }
                }

                forecastResource is Resource.Error -> {
                    locationNameDeferred.cancel()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            screenState = FavoriteDetailsScreenState.Error(
                                forecastResource.message
                            )
                        )
                    }
                }

                weatherResource is Resource.Success &&
                        forecastResource is Resource.Success -> {

                    val locationName = locationNameDeferred.await()

                    _uiState.update {
                        it.copy(
                            currentWeather = weatherResource.data.copy(
                                cityName = locationName
                            ),
                            forecastDays = forecastResource.data,
                            isLoading = false,
                            isDataLoaded = true,
                            screenState = FavoriteDetailsScreenState.Success
                        )
                    }
                }
            }
        }
    }
}