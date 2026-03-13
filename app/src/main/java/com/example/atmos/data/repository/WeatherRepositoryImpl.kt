package com.example.atmos.data.repository
import com.example.atmos.data.datasource.local.WeatherLocalDataSource
import com.example.atmos.data.datasource.remote.WeatherRemoteDataSource
import com.example.atmos.data.mappers.toDomain
import com.example.atmos.data.mappers.toEntity
import com.example.atmos.data.mappers.toEntityList
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val remote: WeatherRemoteDataSource,
    private val local: WeatherLocalDataSource
) : WeatherRepository {

    override fun getCurrentWeather(
        lat: Double,
        lon: Double,
        forceUpdate: Boolean,
        unit: String,
        lang: String,
    ): Flow<Resource<CurrentWeather>> = flow {
        emit(Resource.Loading())

        if (!forceUpdate){
            val cachedWeather = local.getCachedWeather().first()
            val isCacheValid = local.isCacheValid().first()

            if (isCacheValid && cachedWeather != null) {
                emit(Resource.Success(cachedWeather.toDomain()))
                return@flow
            }
        }

        val result = remote.getCurrentWeather(lat, lon, unit, lang)

        if (result.isSuccess) {
            val dto = result.getOrNull()

            if (dto != null) {
                local.clearAllCache()
                local.cacheCurrentWeather(dto.toEntity())

                emit(Resource.Success(dto.toDomain()))
            } else {
                emit(Resource.Error("No data available"))
            }

        } else {
            val cachedWeather = local.getCachedWeather().first()

            if (cachedWeather != null) {
                emit(Resource.Success(cachedWeather.toDomain()))
            } else {
                result.onFailure { throwable ->
                    emit(Resource.Error("Error: ${throwable.localizedMessage}"))
                }
            }
        }

    }.catch { throwable ->
        emit(Resource.Error("Unexpected error: ${throwable.localizedMessage}"))
    }.flowOn(Dispatchers.IO)

    override fun getForecast(
        lat: Double,
        lon: Double,
        forceUpdate: Boolean,
        unit: String,
        lang: String,
    ): Flow<Resource<Forecast>> = flow {
        emit(Resource.Loading())

        if (!forceUpdate){
            val cachedForecasts = local.getCachedForecasts().first()
            val isCacheValid = local.isCacheValid().first()

            if (isCacheValid && cachedForecasts.isNotEmpty()) {
                emit(Resource.Success(cachedForecasts.toDomain()))
                return@flow
            }
        }

        val result = remote.getForecast(lat, lon, unit, lang)
        if (result.isSuccess) {
            val dto = result.getOrNull()

            if (dto != null) {
                local.cacheForecast(dto.toEntityList())

                emit(Resource.Success(dto.toDomain()))
            } else {
                emit(Resource.Error("No forecast data available"))
            }

        } else {
            val cachedForecasts = local.getCachedForecasts().first()

            if (cachedForecasts.isNotEmpty()) {
                emit(Resource.Success(cachedForecasts.toDomain()))
            } else {
                result.onFailure { throwable ->
                    emit(Resource.Error("Error: ${throwable.localizedMessage}"))
                }
            }
        }

    }.catch { throwable ->
        emit(Resource.Error("Unexpected error: ${throwable.localizedMessage}"))
    }.flowOn(Dispatchers.IO)

    override suspend fun hasCache(): Boolean {
        return local.getCachedTime().first() != null
    }
}