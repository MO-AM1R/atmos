package com.example.atmos.di
import android.app.Application
import androidx.room.Room
import com.example.atmos.data.database.AppDatabase
import com.example.atmos.data.database.dao.ForecastDao
import com.example.atmos.data.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase{
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "weather.db"
            ).build()
    }

    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao{
        return appDatabase.weatherDao()
    }

    @Provides
    fun provideForecastDao(appDatabase: AppDatabase): ForecastDao{
        return appDatabase.forecastDao()
    }
}