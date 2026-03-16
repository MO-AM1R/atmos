package com.example.atmos.di

import com.example.atmos.data.datasource.local.FavoritesLocalDataSource
import com.example.atmos.data.datasource.local.FavoritesLocalDataSourceImpl
import com.example.atmos.data.datasource.local.UserPreferencesDataStore
import com.example.atmos.data.datasource.local.UserPreferencesDataStoreImpl
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
    abstract fun bindUserPreferencesDataStore(
        impl: UserPreferencesDataStoreImpl
    ): UserPreferencesDataStore

    @Binds
    @Singleton
    abstract fun bindWeatherLocalDataSource(
        impl: WeatherLocalDataSourceImpl
    ): WeatherLocalDataSource

    @Binds
    @Singleton
    abstract fun binFavoritesLocalDataSource(
        impl: FavoritesLocalDataSourceImpl
    ): FavoritesLocalDataSource
}