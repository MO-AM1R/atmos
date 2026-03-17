package com.example.atmos.ui.settings.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.domain.model.HourlyForecast
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.settings.state.SettingsNavigationEvent
import com.example.atmos.utils.Resource
import com.example.atmos.utils.ReverseGeocodingHelper
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var settingsRepository: UserPreferencesRepository
    private lateinit var reverseGeocoding: ReverseGeocodingHelper

    private val dummyUserPreferences: UserPreferences = UserPreferences(
        storedPoint = StoredPoint(latitude = 120.23, longitude = 233.11)
    )
    private val dummyStoredLocationName = "Location"
    private val dummyStoredPoint = StoredPoint(latitude = 6.23, longitude = 2.11)

    private val dummyCurrentWeather = CurrentWeather(
        cityName = "Cairo",
        countryCode = "EG",
        latitude = 30.0606,
        longitude = 31.2497,
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
        sunriseUnix = 1700000000L,
        sunsetUnix = 1700043200L,
        timezoneOffset = 7200,
        timestampUnix = 1700020000L
    )

    private val dummyForecast = Forecast(
        cityName = "Cairo",
        countryCode = "EG",
        latitude = 30.0606,
        longitude = 31.2497,
        timezoneOffset = 7200,
        hourlyForecasts = listOf(
            HourlyForecast(
                forecastDateText = "2024-01-01 12:00:00",
                timestampUnix = 1700020000L,
                temperature = 28.0,
                feelsLike = 27.0,
                minimumTemp = 24.0,
                maximumTemp = 32.0,
                pressureHpa = 1012,
                humidityPercent = 45,
                windSpeedRaw = 4.0,
                windDirectionDeg = 170,
                cloudCoverPercent = 10,
                visibilityMeters = 9000,
                weatherConditionId = 801,
                weatherMain = "Clouds",
                weatherDescription = "few clouds",
                weatherIconCode = "02d"
            )
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        weatherRepository = mockk()
        settingsRepository = mockk()
        reverseGeocoding = mockk()

        mockkSettingsRepository()
        mockkWeatherRepository()
        mockkReverseGeocoding()

        settingsViewModel = SettingsViewModel(
            weatherRepository = weatherRepository,
            settingsRepository = settingsRepository,
            reverseGeocoding = reverseGeocoding,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    private fun mockkReverseGeocoding() {
        coEvery {
            reverseGeocoding.getLocationName(any())
        } returns dummyStoredLocationName
    }

    private fun mockkWeatherRepository() {
        every {
            weatherRepository.getCurrentWeather(
                lat = any(),
                lon = any(),
                lang = any(),
                forceUpdate = any()
            )
        } returns flowOf(Resource.Success(dummyCurrentWeather))

        every {
            weatherRepository.getForecast(
                lat = any(),
                lon = any(),
                lang = any(),
                forceUpdate = any()
            )
        } returns flowOf(Resource.Success(dummyForecast))
    }

    private fun mockkSettingsRepository() {
        every {
            settingsRepository.getUserPreferences()
        } returns flowOf(dummyUserPreferences)

        coEvery {
            settingsRepository.getStoredLocationName()
        } returns dummyStoredLocationName

        coEvery { settingsRepository.saveStoredPoint(any()) } just Runs
        coEvery { settingsRepository.saveStoredLocationName(any()) } just Runs
        coEvery { settingsRepository.saveLocationOption(any()) } just Runs
        coEvery { settingsRepository.saveTemperatureUnit(any()) } just Runs
        coEvery { settingsRepository.saveWindUnit(any()) } just Runs
        coEvery { settingsRepository.saveLanguage(any()) } just Runs
    }


    @Test
    fun navigateToMap_triggered_emitsNavigateToMapEvent() = runTest {
        settingsViewModel.settingNavigationEvents.test {
            settingsViewModel.navigateToMap()
            assertEquals(SettingsNavigationEvent.NavigateToMap, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun onEvent_onLocationPointSelected_savesPointToRepository() = runTest {
        settingsViewModel.onEvent(SettingsEvent.OnLocationPointSelected(dummyStoredPoint))

        coVerify { settingsRepository.saveStoredPoint(dummyStoredPoint) }
        coVerify { settingsRepository.saveLocationOption(LocationOption.SPECIFIC_LOCATION) }
    }

    @Test
    fun onEvent_onLocationPointSelected_updatesLocationNameInUiState() = runTest {
        settingsViewModel.onEvent(SettingsEvent.OnLocationPointSelected(dummyStoredPoint))

        assertEquals(
            dummyStoredLocationName,
            settingsViewModel.uiState.value.storedLocationName
        )
        assertFalse(settingsViewModel.uiState.value.isLoadingLocationName)
    }

    @Test
    fun onEvent_onLocationPointSelected_callsReverseGeocoding() = runTest {
        settingsViewModel.onEvent(SettingsEvent.OnLocationPointSelected(dummyStoredPoint))

        coVerify { reverseGeocoding.getLocationName(dummyStoredPoint) }
    }

    @Test
    fun onEvent_onTemperatureUnitSelected_savesUnitToRepository() = runTest {
        settingsViewModel.onEvent(SettingsEvent.OnTemperatureUnitSelected(TemperatureUnit.FAHRENHEIT))

        coVerify { settingsRepository.saveTemperatureUnit(TemperatureUnit.FAHRENHEIT) }
    }

    @Test
    fun onEvent_onLanguageSelected_savesLanguageToRepository() = runTest {
        settingsViewModel.onEvent(SettingsEvent.OnLanguageSelected(Language.ARABIC))

        coVerify { settingsRepository.saveLanguage(Language.ARABIC) }
    }
}