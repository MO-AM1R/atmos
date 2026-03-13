package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponseDto(
    @SerializedName("coord")      val coordinate    : CoordinateDto,
    @SerializedName("weather")    val weatherConditions: List<WeatherConditionDto>,
    @SerializedName("main")       val temperature   : TemperatureDto,
    @SerializedName("wind")       val wind          : WindDto,
    @SerializedName("clouds")     val clouds        : CloudsDto,
    @SerializedName("visibility") val visibilityMeters: Int?,
    @SerializedName("dt")         val timestampUnix : Long,
    @SerializedName("sys")        val sun           : SunDto,
    @SerializedName("timezone")   val timezoneOffset: Int,
    @SerializedName("name")       val cityName      : String
){

    override fun toString(): String {
        return "CurrentWeatherResponseDto(coordinate=$coordinate, weatherConditions=$weatherConditions, temperature=$temperature, wind=$wind, clouds=$clouds, visibilityMeters=$visibilityMeters, timestampUnix=$timestampUnix, sun=$sun, timezoneOffset=$timezoneOffset, cityName='$cityName')"
    }
}