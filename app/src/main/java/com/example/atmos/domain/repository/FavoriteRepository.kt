package com.example.atmos.domain.repository

import com.example.atmos.data.database.entity.FavoriteEntity
import com.example.atmos.domain.model.FavoriteWeatherItem
import com.example.atmos.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(): Flow<Resource<List<FavoriteWeatherItem>>>
    suspend fun insertFavorite(lat: Double, lon: Double)
    suspend fun deleteFavorite(entity: FavoriteEntity)
    suspend fun getFavoriteByPoint(lat: Double, lon: Double): FavoriteEntity?
}