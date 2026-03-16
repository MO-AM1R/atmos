package com.example.atmos.ui.favorites.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.domain.model.FavoriteWeatherItem
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.repository.FavoriteRepository
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.ui.favorites.state.FavoritesEvent
import com.example.atmos.ui.favorites.state.FavoritesState
import com.example.atmos.ui.favorites.state.FavoritesUiState
import com.example.atmos.ui.favorites.state.SnackbarState
import com.example.atmos.utils.Resource
import com.example.atmos.utils.ReverseGeocodingHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val weatherRepository: WeatherRepository,
    private val reverseGeocoding: ReverseGeocodingHelper,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    private var pendingDeleteItem: FavoriteWeatherItem? = null
    private var deleteJob: Job? = null

    init {
        observeFavorites()
        observeOnSettings()

    }

    private fun observeOnSettings() {
        viewModelScope.launch {
            userPreferencesRepository.getUserPreferences().collect { newPrefs ->
                val currentPrefs = _uiState.value.userPreferences

                _uiState.update { it.copy(userPreferences = newPrefs) }

                when {
                    currentPrefs == null -> {}

                    newPrefs.temperatureUnitOption != currentPrefs.temperatureUnitOption ||
                            newPrefs.windUnitOption != currentPrefs.windUnitOption -> {
                    }

                    newPrefs.languageOption != currentPrefs.languageOption -> {
                        _uiState.value.favorites.forEach { entity ->
                            launch {
                                fetchWeatherForFavorite(entity)
                            }.invokeOnCompletion { _uiState.update { it.copy(state = FavoritesState.Success) } }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoriteRepository.getAllFavorites().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(favorites = resource.data) }

                        _uiState.value.favorites.forEach { entity ->
                            launch {
                                fetchWeatherForFavorite(entity)
                            }.invokeOnCompletion { _uiState.update { it.copy(state = FavoritesState.Success) } }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(state = FavoritesState.Error(resource.message)) }
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(state = FavoritesState.Loading) }
                    }
                }
            }
        }
    }

    private suspend fun fetchWeatherForFavorite(favoriteWeatherItem: FavoriteWeatherItem) {
        try {
            val prefs = userPreferencesRepository.getUserPreferences().first()
            val lang = prefs.languageOption.apiValue

            val weatherDeferred = viewModelScope.async {
                weatherRepository.getWeatherForPoint(
                    lat = favoriteWeatherItem.latitude,
                    lon = favoriteWeatherItem.longitude,
                    lang = lang,
                ).first { it !is Resource.Loading }
            }

            val nameDeferred = viewModelScope.async {
                reverseGeocoding.getLocationName(
                    StoredPoint(
                        latitude = favoriteWeatherItem.latitude,
                        longitude = favoriteWeatherItem.longitude
                    )
                ) ?: "${favoriteWeatherItem.latitude}, ${favoriteWeatherItem.longitude}"
            }

            val weatherResult = weatherDeferred.await()
            val cityName = nameDeferred.await()

            val updatedItem = when (weatherResult) {
                is Resource.Success -> FavoriteWeatherItem(
                    id = favoriteWeatherItem.id,
                    latitude = favoriteWeatherItem.latitude,
                    longitude = favoriteWeatherItem.longitude,
                    cityName = cityName,
                    weatherMain = weatherResult.data.weatherMain,
                    weatherIcon = weatherResult.data.weatherIconCode,
                    temperature = weatherResult.data.temperature,
                    minTemp = weatherResult.data.minimumTemp,
                    maxTemp = weatherResult.data.maximumTemp,
                    isLoading = false
                )

                is Resource.Error -> FavoriteWeatherItem(
                    id = favoriteWeatherItem.id,
                    latitude = favoriteWeatherItem.latitude,
                    longitude = favoriteWeatherItem.longitude,
                    isLoading = false,
                    error = weatherResult.message
                )

                else -> return
            }

            _uiState.update { state ->
                state.copy(
                    favorites = state.favorites.map { item ->
                        if (favoriteWeatherItem.id == item.id) updatedItem else item
                    },
                )
            }

        } catch (e: Exception) {
            _uiState.update { state ->
                Log.d("TAG", "Error ${e.message}")
                state.copy(
                    favorites = state.favorites.map { item ->
                        if (favoriteWeatherItem.id == item.id)
                            item.copy(isLoading = false, error = e.message)
                        else item
                    },
                    state = FavoritesState.Error(e.message ?: "Error")
                )
            }
        }
    }


    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.OnAddFavorite -> onAddFavorite(event.lat, event.lon)
            is FavoritesEvent.OnDeleteFavorite -> onDeleteFavorite(event.item)
            is FavoritesEvent.OnUndoDelete -> onUndoDelete(event.item)
            FavoritesEvent.OnDismissSnackbar -> _uiState.update { it.copy(snackbarState = SnackbarState()) }
        }
    }


    private fun onAddFavorite(lat: Double, lon: Double) {
        viewModelScope.launch {
            favoriteRepository.insertFavorite(lat = lat, lon = lon)
        }
    }

    private fun onDeleteFavorite(item: FavoriteWeatherItem) {
        _uiState.update { state ->
            state.copy(
                favorites = state.favorites.filter { it.id != item.id },
                snackbarState = SnackbarState(isVisible = true, item = item)
            )
        }

        pendingDeleteItem = item

        deleteJob?.cancel()
        deleteJob = viewModelScope.launch {
            delay(4000L)

            val entity = favoriteRepository.getFavoriteByPoint(
                lat = item.latitude,
                lon = item.longitude
            )
            entity?.let { favoriteRepository.deleteFavorite(it) }
            pendingDeleteItem = null

            _uiState.update { it.copy(snackbarState = SnackbarState()) }
        }
    }

    private fun onUndoDelete(item: FavoriteWeatherItem) {
        deleteJob?.cancel()
        pendingDeleteItem = null

        _uiState.update { state ->
            state.copy(
                favorites = (state.favorites + item.copy()).sortedBy { it.id },
                snackbarState = SnackbarState()
            )
        }
    }
}