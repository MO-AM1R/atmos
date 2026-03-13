package com.example.atmos.ui.settings.state

import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit


data class SettingsUiState(
    val locationOption  : LocationOption = LocationOption.GPS,
    val temperatureUnit : TemperatureUnit = TemperatureUnit.CELSIUS,
    val windUnit        : WindUnit = WindUnit.METERS_PER_SECOND,
    val language        : Language          = Language.ENGLISH
)