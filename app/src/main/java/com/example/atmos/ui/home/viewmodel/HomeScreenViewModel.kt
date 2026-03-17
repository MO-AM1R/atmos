package com.example.atmos.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.ui.home.state.HomeEvent
import com.example.atmos.ui.home.state.HomeScreenState
import com.example.atmos.ui.home.state.HomeUIEvents
import com.example.atmos.ui.home.state.HomeUiState
import com.example.atmos.utils.Resource
import com.example.atmos.utils.ReverseGeocodingHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val reverseGeocodingHelper: ReverseGeocodingHelper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _homeEventChannel = Channel<HomeUIEvents>()
    val homeUIEvens = _homeEventChannel.receiveAsFlow()

    private var weatherJob: Job? = null

    init {
        observeOnSettings()
    }

    private fun observeOnSettings() {
        viewModelScope.launch {
            userPreferencesRepository.getUserPreferences().collect { newPrefs ->
                val currentPrefs = _uiState.value.userPreferences

                _uiState.update { it.copy(userPreferences = newPrefs) }

                when {
                    currentPrefs == null -> {
                        handleInitialLoad(newPrefs)
                    }

                    newPrefs.temperatureUnitOption != currentPrefs.temperatureUnitOption ||
                            newPrefs.windUnitOption != currentPrefs.windUnitOption -> {
                    }

                    newPrefs.languageOption != currentPrefs.languageOption -> {
                        val point = resolvePoint(newPrefs)
                        if (point != null) {
                            loadWeather(point = point, forceUpdate = true)
                        } else {
                            _homeEventChannel.send(HomeUIEvents.TriggerGPSLocation)
                        }
                    }

                    newPrefs.locationOption == LocationOption.GPS &&
                            newPrefs.locationOption != currentPrefs.locationOption -> {

                        _uiState.update { it.copy(screenState = HomeScreenState.Loading) }
                        _homeEventChannel.send(HomeUIEvents.TriggerGPSLocation)
                    }

                    newPrefs.locationOption == LocationOption.SPECIFIC_LOCATION &&
                            newPrefs.storedPoint != null &&
                            newPrefs.storedPoint != _uiState.value.point -> {

                        loadWeather(
                            point = newPrefs.storedPoint,
                            forceUpdate = true
                        )
                    }

                    else -> {}
                }
            }
        }
    }


    private suspend fun handleInitialLoad(prefs: UserPreferences) {
        when (prefs.locationOption) {
            LocationOption.SPECIFIC_LOCATION -> {
                val point = prefs.storedPoint
                if (point != null) {
                    loadWeather(point = point, forceUpdate = false)
                } else {
                    _homeEventChannel.send(HomeUIEvents.TriggerGPSLocation)
                }
            }

            LocationOption.GPS -> {
                _homeEventChannel.send(HomeUIEvents.TriggerGPSLocation)
            }
        }
    }

    private fun resolvePoint(prefs: UserPreferences): StoredPoint? {
        return when (prefs.locationOption) {
            LocationOption.SPECIFIC_LOCATION -> prefs.storedPoint
            LocationOption.GPS -> _uiState.value.point
        }
    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLoad -> {
                _uiState.update {
                    it.copy(
                        isDataLoaded = false,
                        screenState = HomeScreenState.Loading
                    )
                }

                loadWeather(
                    point = event.point,
                    forceUpdate = event.forceUpdate
                )
            }
        }
    }

    fun setScreenState(state: HomeScreenState) {
        _uiState.update { it.copy(screenState = state) }
    }

    private fun loadWeather(point: StoredPoint?, forceUpdate: Boolean = false) {
        if (point == null) return
        if (_uiState.value.isDataLoaded && !forceUpdate) return

        weatherJob?.cancel()

        weatherJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    point = point,
                    isDataLoaded = false,
                    screenState = HomeScreenState.Loading
                )
            }

            val lang = _uiState.value.userPreferences
                ?.languageOption
                ?.apiValue
                ?: Language.ENGLISH.apiValue

            val locationNameDeferred = async {
                reverseGeocodingHelper.getLocationName(point)
                    ?: "${point.latitude}, ${point.longitude}"
            }

            combine(
                weatherRepository.getCurrentWeather(
                    lat = point.latitude,
                    lon = point.longitude,
                    lang = lang,
                    forceUpdate = forceUpdate
                ),
                weatherRepository.getForecast(
                    lat = point.latitude,
                    lon = point.longitude,
                    lang = lang,
                    forceUpdate = forceUpdate
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
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                screenState = HomeScreenState.Error(weatherResource.message)
                            )
                        }
                    }

                    forecastResource is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                screenState = HomeScreenState.Error(forecastResource.message)
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
                                screenState = HomeScreenState.Success
                            )
                        }
                    }
                }
            }
        }
    }
}