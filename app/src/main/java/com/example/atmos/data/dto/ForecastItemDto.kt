package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class ForecastItemDto(
    @SerializedName("dt")         val timestampUnix    : Long,
    @SerializedName("main")       val temperature      : com.example.atmos.data.dto.TemperatureDto,
    @SerializedName("weather")    val weatherConditions: List<com.example.atmos.data.dto.WeatherConditionDto>,
    @SerializedName("clouds")     val clouds           : com.example.atmos.data.dto.CloudsDto,
    @SerializedName("wind")       val wind             : com.example.atmos.data.dto.WindDto,
    @SerializedName("visibility") val visibilityMeters : Int?,
    @SerializedName("dt_txt")     val forecastDateText : String
)