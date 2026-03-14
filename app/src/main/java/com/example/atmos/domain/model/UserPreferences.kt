package com.example.atmos.domain.model

import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit

data class UserPreferences(
    val locationOption: LocationOption = LocationOption.GPS,
    val temperatureUnitOption: TemperatureUnit = TemperatureUnit.KELVIN,
    val languageOption: Language = Language.ENGLISH,
    val windUnitOption: WindUnit = WindUnit.METERS_PER_SECOND,
    val storedPoint: StoredPoint?
)