package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class TemperatureDto(
    @SerializedName("temp")       val temperature    : Double,
    @SerializedName("feels_like") val feelsLike      : Double,
    @SerializedName("temp_min")   val minimumTemp    : Double,
    @SerializedName("temp_max")   val maximumTemp    : Double,
    @SerializedName("pressure")   val pressureHpa    : Int,
    @SerializedName("humidity")   val humidityPercent: Int
)