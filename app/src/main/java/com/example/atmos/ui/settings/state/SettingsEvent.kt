package com.example.atmos.ui.settings.state

import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit

sealed class SettingsEvent {
    data class OnGpsToggled(val locationOption: LocationOption): SettingsEvent()
    data class OnTemperatureUnitSelected(val newOption: TemperatureUnit): SettingsEvent()
    data class OnWindUnitSelected(val newOption: WindUnit): SettingsEvent()
    data class OnLanguageClicked(val newOption: Language): SettingsEvent()
    object OnNavigateToMapScreen: SettingsEvent()
}