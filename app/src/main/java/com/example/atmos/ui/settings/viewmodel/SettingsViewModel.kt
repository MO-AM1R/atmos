package com.example.atmos.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.settings.state.SettingsNavigationEvent
import com.example.atmos.ui.settings.state.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: UserPreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    private val _settingNavigationEvents = Channel<SettingsNavigationEvent>(Channel.BUFFERED)
    val settingNavigationEvents = _settingNavigationEvents.receiveAsFlow()


    private fun sendNavigationEvent(settingsNavigationEvent: SettingsNavigationEvent){
        viewModelScope.launch {
            _settingNavigationEvents.send(settingsNavigationEvent)
        }
    }


    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnGpsToggled -> {
                repo.updateOption(event.locationOption)

                when(event.locationOption){
                    LocationOption.SPECIFIC_LOCATION -> {
                        sendNavigationEvent(SettingsNavigationEvent.NavigateToMap)
                    }
                    LocationOption.GPS -> {}
                }
            }
            is SettingsEvent.OnNavigateToMapScreen -> {
                sendNavigationEvent(SettingsNavigationEvent.NavigateToMap)
            }

            is SettingsEvent.OnLanguageClicked -> repo.updateOption(event.newOption)
            is SettingsEvent.OnTemperatureUnitSelected -> repo.updateOption(event.newOption)
            is SettingsEvent.OnWindUnitSelected -> repo.updateOption(event.newOption)
        }
    }
}