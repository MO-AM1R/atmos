package com.example.atmos.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.settings.state.SettingsNavigationEvent
import com.example.atmos.ui.settings.state.SettingsUiState
import com.example.atmos.utils.Resource
import com.example.atmos.utils.ReverseGeocodingHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: UserPreferencesRepository,
    private val reverseGeocoding: ReverseGeocodingHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _refreshState = MutableStateFlow<Resource<Unit>>(Resource.Success(Unit))

    private val _settingNavigationEvents = Channel<SettingsNavigationEvent>(Channel.BUFFERED)
    val settingNavigationEvents = _settingNavigationEvents.receiveAsFlow()


    init {
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            settingsRepository.getUserPreferences().collect { prefs ->
                _uiState.update {
                    it.copy(
                        locationOption = prefs.locationOption,
                        storedPoint = prefs.storedPoint,
                        temperatureUnit = prefs.temperatureUnitOption,
                        windUnit = prefs.windUnitOption,
                        language = prefs.languageOption
                    )
                }

                prefs.storedPoint?.let {
                    val cachedName = settingsRepository.getStoredLocationName()
                    _uiState.update { state ->
                        state.copy(storedLocationName = cachedName)
                    }
                }
            }
        }
    }

    fun navigateToMap() {
        viewModelScope.launch {
            _settingNavigationEvents.send(SettingsNavigationEvent.NavigateToMap)
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnLocationPointSelected -> onLocationSelected(event.storedPoint)
            is SettingsEvent.OnTemperatureUnitSelected -> onTemperatureUnitSelected(event.newOption)
            is SettingsEvent.OnWindUnitSelected -> onWindUnitSelected(event.newOption)
            is SettingsEvent.OnLanguageSelected -> onLanguageSelected(event.newOption)
            is SettingsEvent.OnLocationOptionChanged -> onLocationOptionChanged(event.newOption)
            SettingsEvent.OnNavigateToMapScreen -> navigateToMap()
        }
    }

    private fun onLocationSelected(point: StoredPoint) {
        viewModelScope.launch {

            settingsRepository.saveStoredPoint(point)
            settingsRepository.saveLocationOption(LocationOption.SPECIFIC_LOCATION)

            _uiState.update {
                it.copy(
                    locationOption = LocationOption.SPECIFIC_LOCATION,
                    storedPoint = point,
                    isLoadingLocationName = true
                )
            }

            val locationName = reverseGeocoding.getLocationName(point)
                ?: "${point.latitude}, ${point.longitude}"

            settingsRepository.saveStoredLocationName(locationName)

            _uiState.update {
                it.copy(
                    storedLocationName = locationName,
                    isLoadingLocationName = false
                )
            }

            refreshWeather(
                lat = point.latitude,
                lon = point.longitude,
                lang = _uiState.value.language.apiValue
            )
        }
    }

    private fun onTemperatureUnitSelected(unit: TemperatureUnit) {
        viewModelScope.launch {
            settingsRepository.saveTemperatureUnit(unit)
            _uiState.update { it.copy(temperatureUnit = unit) }
        }
    }

    private fun onWindUnitSelected(unit: WindUnit) {
        viewModelScope.launch {
            settingsRepository.saveWindUnit(unit)
            _uiState.update { it.copy(windUnit = unit) }
        }
    }

    private fun onLanguageSelected(language: Language) {
        viewModelScope.launch {
            settingsRepository.saveLanguage(language)
            _uiState.update { it.copy(language = language) }
            refreshWeatherWithCurrentSettings()
        }
    }

    private fun onLocationOptionChanged(option: LocationOption) {
        viewModelScope.launch {
            settingsRepository.saveLocationOption(option)
            _uiState.update { it.copy(locationOption = option) }
        }
    }

    private fun refreshWeatherWithCurrentSettings() {
        val state = _uiState.value
        val point = state.storedPoint ?: return

        viewModelScope.launch {
            refreshWeather(
                lat = point.latitude,
                lon = point.longitude,
                lang = state.language.apiValue
            )
        }
    }

    private suspend fun refreshWeather(
        lat: Double,
        lon: Double,
        lang: String
    ) {
        _uiState.update { it.copy(isRefreshing = true) }

        combine(
            weatherRepository.getCurrentWeather(
                lat = lat,
                lon = lon,
                lang = lang,
                forceUpdate = true
            ),
            weatherRepository.getForecast(lat = lat, lon = lon, lang = lang, forceUpdate = true)
        ) { weatherResult, forecastResult ->
            when {
                weatherResult is Resource.Loading ||
                        forecastResult is Resource.Loading -> Resource.Loading()

                weatherResult is Resource.Error -> Resource.Error(weatherResult.message)
                forecastResult is Resource.Error -> Resource.Error(forecastResult.message)
                else -> Resource.Success(Unit)
            }
        }.collect { result ->
            _refreshState.value = result
            if (result !is Resource.Loading) {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
}