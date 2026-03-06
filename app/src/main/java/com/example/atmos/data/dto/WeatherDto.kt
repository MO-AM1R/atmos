package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherConditionDto(
    @SerializedName("id")          val conditionId  : Int,
    @SerializedName("main")        val conditionMain: String,
    @SerializedName("description") val description  : String,
    @SerializedName("icon")        val iconCode     : String
)