package com.example.atmos.data.dto

import com.google.gson.annotations.SerializedName

data class CloudsDto(
    @SerializedName("all") val coveragePercent: Int
)