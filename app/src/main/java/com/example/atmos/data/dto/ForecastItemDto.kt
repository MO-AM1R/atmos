package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class ForecastItemDto(
    @SerializedName("dt")         val timestampUnix    : Long,
    @SerializedName("main")       val temperature      : TemperatureDto,
    @SerializedName("weather")    val weatherConditions: List<WeatherConditionDto>,
    @SerializedName("clouds")     val clouds           : CloudsDto,
    @SerializedName("wind")       val wind             : WindDto,
    @SerializedName("visibility") val visibilityMeters : Int?,
    @SerializedName("dt_txt")     val forecastDateText : String
)