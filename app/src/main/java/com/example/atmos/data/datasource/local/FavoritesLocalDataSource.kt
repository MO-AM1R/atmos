package com.example.atmos.data.datasource.local

import com.example.atmos.data.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    suspend fun insertFavorite(entity: FavoriteEntity)
    suspend fun deleteFavorite(entity: FavoriteEntity)
    suspend fun getFavoriteByPoint(lat: Double, lon: Double): FavoriteEntity?
}