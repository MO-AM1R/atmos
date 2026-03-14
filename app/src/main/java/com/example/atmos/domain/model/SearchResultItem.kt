package com.example.atmos.domain.model

import com.mapbox.geojson.Point

data class SearchResultItem(
    val name: String,
    val address: String?,
    val point: Point
)