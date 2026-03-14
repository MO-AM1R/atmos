package com.example.atmos.ui.settings.state

import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.model.StoredPoint


data class SettingsUiState(
    val locationOption: LocationOption = LocationOption.GPS,
    val storedPoint: StoredPoint? = null,
    val storedLocationName: String? = null,
    val isLoadingLocationName: Boolean = false,
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val windUnit: WindUnit = WindUnit.METERS_PER_SECOND,
    val language: Language = Language.ENGLISH,
    val isRefreshing: Boolean = false
)