package com.example.atmos.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id       : Int    = 0,
    val latitude : Double,
    val longitude: Double
)