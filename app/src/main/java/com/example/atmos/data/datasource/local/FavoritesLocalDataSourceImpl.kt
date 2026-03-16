package com.example.atmos.data.datasource.local

import android.util.Log
import com.example.atmos.data.database.dao.FavoriteDao
import com.example.atmos.data.database.entity.FavoriteEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class FavoritesLocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoritesLocalDataSource {
    override fun getAllFavorites(): Flow<List<FavoriteEntity>> =
        dao.getAllFavorites()

    override suspend fun insertFavorite(entity: FavoriteEntity) {
        dao.insertFavorite(entity)
    }

    override suspend fun deleteFavorite(entity: FavoriteEntity) {
        Log.d("TAG", "entity -> ${entity.longitude}, ${entity.latitude}, ${entity.id}")
        dao.deleteFavorite(entity)
    }

    override suspend fun getFavoriteByPoint(lat: Double, lon: Double): FavoriteEntity? =
        dao.getFavoriteByPoint(lat, lon)
}