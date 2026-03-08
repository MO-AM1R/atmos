package com.example.atmos.data.repository

import com.example.atmos.data.datasource.remote.WeatherRemoteDatSource
import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastResponseDto
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    val remote: WeatherRemoteDatSource
) : WeatherRepository {

    override fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String,
    ): Flow<Resource<CurrentWeatherResponseDto?>> =
        flow {
            emit(Resource.Loading())

            val result = remote.getCurrentWeather(lat, lon, unit, lang)

            if (result.isSuccess) {
                emit(Resource.Success(result.getOrNull()))
            } else {
                result.onFailure { throwable ->
                    emit(Resource.Error("Error: ${throwable.localizedMessage}"))
                }
            }
        }.catch { throwable ->
            emit(Resource.Error("Error: ${throwable.localizedMessage}"))
        }
            .flowOn(Dispatchers.IO)


    override fun getForecast(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String,
    ): Flow<Resource<ForecastResponseDto?>> =
        flow {
            emit(Resource.Loading())

            val result = remote.getForecast(lat, lon, unit, lang)

            if (result.isSuccess) {
                emit(Resource.Success(result.getOrNull()))
            } else {
                result.onFailure { throwable ->
                    emit(Resource.Error("Error: ${throwable.localizedMessage}"))
                }
            }
        }.catch { throwable ->
            emit(Resource.Error("Error: ${throwable.localizedMessage}"))
        }
            .flowOn(Dispatchers.IO)

}