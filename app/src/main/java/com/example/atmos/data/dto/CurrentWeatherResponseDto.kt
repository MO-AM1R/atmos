package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponseDto(
    @SerializedName("coord")      val coordinate    : com.example.atmos.data.dto.CoordinateDto,
    @SerializedName("weather")    val weatherConditions: List<com.example.atmos.data.dto.WeatherConditionDto>,
    @SerializedName("main")       val temperature   : com.example.atmos.data.dto.TemperatureDto,
    @SerializedName("wind")       val wind          : com.example.atmos.data.dto.WindDto,
    @SerializedName("clouds")     val clouds        : com.example.atmos.data.dto.CloudsDto,
    @SerializedName("visibility") val visibilityMeters: Int?,
    @SerializedName("dt")         val timestampUnix : Long,
    @SerializedName("sys")        val sun           : com.example.atmos.data.dto.SunDto,
    @SerializedName("timezone")   val timezoneOffset: Int,
    @SerializedName("name")       val cityName      : String
){

    override fun toString(): String {
        return "CurrentWeatherResponseDto(coordinate=$coordinate, weatherConditions=$weatherConditions, temperature=$temperature, wind=$wind, clouds=$clouds, visibilityMeters=$visibilityMeters, timestampUnix=$timestampUnix, sun=$sun, timezoneOffset=$timezoneOffset, cityName='$cityName')"
    }
}