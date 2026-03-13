package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    @SerializedName("cod")  val statusCode   : String,
    @SerializedName("city") val city         : CityDto,
    @SerializedName("list") val forecastItems: List<ForecastItemDto>
)