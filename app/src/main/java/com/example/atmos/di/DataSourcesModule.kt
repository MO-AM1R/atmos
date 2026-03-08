package com.example.atmos.di

import com.example.atmos.data.datasource.local.UserPreferencesLocalDataSource
import com.example.atmos.data.datasource.local.UserPreferencesLocalDataSourceImpl
import com.example.atmos.data.datasource.remote.WeatherRemoteDatSource
import com.example.atmos.data.datasource.remote.WeatherRemoteDatSourceImpl
import com.example.atmos.domain.userpreferences.UserPreferencesRepository
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
        impl: WeatherRemoteDatSourceImpl
    ): WeatherRemoteDatSource

    @Binds
    @Singleton
    abstract fun bindUserPreferencesLocalDataSource(
        impl: UserPreferencesLocalDataSourceImpl
    ): UserPreferencesLocalDataSource
}