package com.example.atmos.di

import com.example.atmos.data.datasource.local.UserPreferencesLocalDataSource
import com.example.atmos.data.datasource.local.UserPreferencesLocalDataSourceImpl
import com.example.atmos.data.datasource.local.WeatherLocalDataSource
import com.example.atmos.data.datasource.local.WeatherLocalDataSourceImpl
import com.example.atmos.data.datasource.remote.WeatherRemoteDataSource
import com.example.atmos.data.datasource.remote.WeatherRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRemoteDataSource(
        impl: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserPreferencesLocalDataSource(
        impl: UserPreferencesLocalDataSourceImpl
    ): UserPreferencesLocalDataSource

    @Binds
    @Singleton
    abstract fun bindWeatherLocalDataSource(
        impl: WeatherLocalDataSourceImpl
    ): WeatherLocalDataSource
}