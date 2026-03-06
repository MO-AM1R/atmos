package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class CoordinateDto(
    @SerializedName("lat") val latitude  : Double,
    @SerializedName("lon") val longitude : Double
)