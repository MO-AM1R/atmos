package com.example.atmos.data.repository

import com.example.atmos.data.database.entity.FavoriteEntity
import com.example.atmos.data.datasource.local.FavoritesLocalDataSource
import com.example.atmos.domain.model.FavoriteWeatherItem
import com.example.atmos.domain.repository.FavoriteRepository
import com.example.atmos.utils.Resource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val local: FavoritesLocalDataSource
) : FavoriteRepository {
    override fun getAllFavorites(): Flow<Resource<List<FavoriteWeatherItem>>> =
        flow {
            emit(Resource.Loading())

            val list = local.getAllFavorites()
            list.map {
                it.map { entity ->
                    FavoriteWeatherItem(
                        id = entity.id,
                        latitude = entity.latitude,
                        longitude = entity.longitude,
                        isLoading = true
                    )
                }
            }.collect {
                emit(Resource.Success(it))
            }

        }.catch { throwable ->
            emit(Resource.Error("Unexpected error: ${throwable.localizedMessage}"))
        }.flowOn(Dispatchers.IO)


    override suspend fun insertFavorite(lat: Double, lon: Double) {
        local.insertFavorite(FavoriteEntity(latitude = lat, longitude = lon))
    }

    override suspend fun deleteFavorite(entity: FavoriteEntity) =
        local.deleteFavorite(entity)

    override suspend fun getFavoriteByPoint(lat: Double, lon: Double): FavoriteEntity? =
        local.getFavoriteByPoint(lat, lon)
}