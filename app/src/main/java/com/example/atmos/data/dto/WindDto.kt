package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class WindDto(
    @SerializedName("speed") val speedMetersPerSec: Double,
    @SerializedName("deg")   val directionDegrees : Int
)