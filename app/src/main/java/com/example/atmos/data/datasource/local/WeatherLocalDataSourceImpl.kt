package com.example.atmos.data.datasource.local

import com.example.atmos.data.database.dao.WeatherDao
import jakarta.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    val weatherDao: WeatherDao
) : WeatherLocalDataSource {

}