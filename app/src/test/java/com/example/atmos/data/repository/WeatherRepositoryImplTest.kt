package com.example.atmos.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.atmos.data.database.entity.CurrentWeatherEntity
import com.example.atmos.data.database.entity.HourlyForecastEntity
import com.example.atmos.data.datasource.local.FakeWeatherLocalDataSourceImpl
import com.example.atmos.data.datasource.remote.FakeWeatherRemoteDataSourceImpl
import com.example.atmos.data.dto.CityDto
import com.example.atmos.data.dto.CloudsDto
import com.example.atmos.data.dto.CoordinateDto
import com.example.atmos.data.dto.CurrentWeatherResponseDto
import com.example.atmos.data.dto.ForecastItemDto
import com.example.atmos.data.dto.ForecastResponseDto
import com.example.atmos.data.dto.SunDto
import com.example.atmos.data.dto.TemperatureDto
import com.example.atmos.data.dto.WeatherConditionDto
import com.example.atmos.data.dto.WindDto
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var fakeLocalDataSource: FakeWeatherLocalDataSourceImpl
    private lateinit var fakeRemoteDataSource: FakeWeatherRemoteDataSourceImpl

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val remoteCurrentWeather = CurrentWeatherResponseDto(
        coordinate = CoordinateDto(latitude = 30.0, longitude = 31.0),
        weatherConditions = listOf(
            WeatherConditionDto(
                conditionId = 800,
                conditionMain = "Clear",
                description = "clear sky",
                iconCode = "01d"
            )
        ),
        temperature = TemperatureDto(
            temperature = 30.0,
            feelsLike = 28.0,
            minimumTemp = 25.0,
            maximumTemp = 35.0,
            pressureHpa = 1013,
            humidityPercent = 40
        ),
        wind = WindDto(speedMetersPerSec = 5.0, directionDegrees = 180),
        clouds = CloudsDto(coveragePercent = 0),
        visibilityMeters = 10000,
        timestampUnix = System.currentTimeMillis(),
        sun = SunDto(
            countryCode = "EG",
            sunriseUnix = System.currentTimeMillis() - 3600,
            sunsetUnix = System.currentTimeMillis() + 3600
        ),
        timezoneOffset = 7200,
        cityName = "Cairo"
    )

    private val remoteHourlyForecasts = ForecastResponseDto(
        statusCode = "200",
        city = CityDto(
            cityId = 1,
            cityName = "Cairo",
            countryCode = "EG",
            coordinate = CoordinateDto(latitude = 30.0, longitude = 31.0),
            timezoneOffset = 7200
        ),
        forecastItems = listOf(
            ForecastItemDto(
                timestampUnix = System.currentTimeMillis() + 3600,
                temperature = TemperatureDto(
                    temperature = 28.0,
                    feelsLike = 27.0,
                    minimumTemp = 24.0,
                    maximumTemp = 32.0,
                    pressureHpa = 1012,
                    humidityPercent = 45
                ),
                weatherConditions = listOf(
                    WeatherConditionDto(
                        conditionId = 800,
                        conditionMain = "Clear",
                        description = "clear sky",
                        iconCode = "01d"
                    )
                ),
                clouds = CloudsDto(coveragePercent = 0),
                wind = WindDto(speedMetersPerSec = 4.0, directionDegrees = 170),
                visibilityMeters = 10000,
                forecastDateText = "2024-01-01 12:00:00"
            )
        )
    )
    private val localCurrentWeather = CurrentWeatherEntity(
        id = 1,
        cityName = "Cairo",
        countryCode = "EG",
        latitude = 30.0,
        longitude = 31.0,
        temperature = 30.0,
        feelsLike = 28.0,
        minimumTemp = 25.0,
        maximumTemp = 35.0,
        pressureHpa = 1013,
        humidityPercent = 40,
        windSpeedRaw = 5.0,
        windDirectionDeg = 180,
        cloudCoverPercent = 0,
        visibilityMeters = 10000,
        weatherConditionId = 800,
        weatherMain = "Clear",
        weatherDescription = "clear sky",
        weatherIconCode = "01d",
        sunriseUnix = System.currentTimeMillis() - 3600,
        sunsetUnix = System.currentTimeMillis() + 3600,
        timezoneOffset = 7200,
        timestampUnix = System.currentTimeMillis(),
        cachedAtUnix = System.currentTimeMillis()
    )

    private val localHourlyForecasts = listOf(
        HourlyForecastEntity(
            timestampUnix = System.currentTimeMillis() + 3600,
            cityName = "Cairo",
            forecastDateText = "2024-01-01 12:00:00",
            temperature = 28.0,
            countryCode = "EG",
            latitude = 30.0,
            longitude = 31.0,
            timezoneOffset = 7200,
            feelsLike = 27.0,
            minimumTemp = 24.0,
            maximumTemp = 32.0,
            pressureHpa = 1012,
            humidityPercent = 45,
            windSpeedRaw = 4.0,
            windDirectionDeg = 170,
            cloudCoverPercent = 0,
            visibilityMeters = 10000,
            weatherConditionId = 800,
            weatherMain = "Clear",
            weatherDescription = "clear sky",
            weatherIconCode = "01d",
            cachedAtUnix = System.currentTimeMillis()
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        fakeLocalDataSource = FakeWeatherLocalDataSourceImpl(
            currentWeather = localCurrentWeather,
            hourlyForecasts = localHourlyForecasts
        )

        fakeRemoteDataSource = FakeWeatherRemoteDataSourceImpl(
            currentWeather = remoteCurrentWeather,
            hourlyForecasts = remoteHourlyForecasts
        )

        weatherRepository = WeatherRepositoryImpl(
            local = fakeLocalDataSource,
            remote = fakeRemoteDataSource
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCurrentWeather_validCacheNoForceUpdate_returnsCachedData() = runTest {
        val result = weatherRepository.getCurrentWeather(
            lat = 30.0,
            lon = 31.0,
            lang = "en",
            forceUpdate = false
        ).first { it is Resource.Success }

        assertEquals(
            localCurrentWeather.cityName,
            (result as Resource.Success).data.cityName
        )
    }

    @Test
    fun getCurrentWeather_forceUpdate_returnsRemoteData() = runTest {
        val result = weatherRepository.getCurrentWeather(
            lat = 30.0,
            lon = 31.0,
            lang = "en",
            forceUpdate = true
        ).first { it is Resource.Success }

        assertEquals(
            remoteCurrentWeather.cityName,
            (result as Resource.Success).data.cityName
        )
    }

    @Test
    fun getCurrentWeather_remoteError_returnsCachedData() = runTest {
        fakeRemoteDataSource.shouldThrowError = true

        val result = weatherRepository.getCurrentWeather(
            lat = 30.0,
            lon = 31.0,
            lang = "en",
            forceUpdate = true
        ).first { it !is Resource.Loading }

        assertTrue(result is Resource.Success)
        if (result is Resource.Success){
            assertNotNull(result.data)
        }
    }

    @Test
    fun getCurrentWeather_cacheError_returnsError() = runTest {
        fakeLocalDataSource.shouldThrowError = true

        val result = weatherRepository.getCurrentWeather(
            lat = 30.0,
            lon = 31.0,
            lang = "en",
            forceUpdate = false
        ).first { it !is Resource.Loading }

        assertTrue(result is Resource.Error)
    }

    @Test
    fun hasCache_weatherAlreadyCached_returnsTrue() = runTest {
        val result = weatherRepository.hasCache()
        assertTrue(result)
    }

    @Test
    fun hasCache_afterClearCache_returnsFalse() = runTest {
        fakeLocalDataSource.clearAllCache()

        val result = weatherRepository.hasCache()
        assertFalse(result)
    }
}