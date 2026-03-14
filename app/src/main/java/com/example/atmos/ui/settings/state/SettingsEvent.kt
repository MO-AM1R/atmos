package com.example.atmos.ui.settings.state

import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.model.StoredPoint

sealed class SettingsEvent {
    data class OnLocationPointSelected(val storedPoint: StoredPoint): SettingsEvent()
    data class OnTemperatureUnitSelected(val newOption: TemperatureUnit): SettingsEvent()
    data class OnWindUnitSelected(val newOption: WindUnit): SettingsEvent()
    data class OnLanguageSelected(val newOption: Language): SettingsEvent()
    data class OnLocationOptionChanged(val newOption: LocationOption): SettingsEvent()
    object OnNavigateToMapScreen: SettingsEvent()
}