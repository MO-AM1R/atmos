package com.example.atmos.di
import com.example.atmos.data.repository.FavoriteRepositoryImpl
import com.example.atmos.data.repository.UserPreferencesRepositoryImpl
import com.example.atmos.data.repository.WeatherRepositoryImpl
import com.example.atmos.domain.repository.FavoriteRepository
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository


    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository
}