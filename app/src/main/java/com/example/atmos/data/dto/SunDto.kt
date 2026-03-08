package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class SunDto(
    @SerializedName("country") val countryCode  : String?,
    @SerializedName("sunrise") val sunriseUnix  : Long?,
    @SerializedName("sunset")  val sunsetUnix   : Long?
)