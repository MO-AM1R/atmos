package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    @SerializedName("cod")  val statusCode   : String,
    @SerializedName("city") val city         : com.example.atmos.data.dto.CityDto,
    @SerializedName("list") val forecastItems: List<com.example.atmos.data.dto.ForecastItemDto>
)