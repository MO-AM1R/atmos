package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("id")       val cityId      : Int,
    @SerializedName("name")     val cityName    : String,
    @SerializedName("coord")    val coordinate  : com.example.atmos.data.dto.CoordinateDto,
    @SerializedName("country")  val countryCode : String,
    @SerializedName("timezone") val timezoneOffset: Int
)