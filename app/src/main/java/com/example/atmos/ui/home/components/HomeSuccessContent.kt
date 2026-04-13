package com.example.atmos.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.ui.home.state.HomeUiState
import com.example.atmos.ui.theme.Spacing
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun HomeSuccessContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState(),
    userPreferencesState: UserPreferences? = null,
    liquidState: LiquidState,
) {
    val scrollState = rememberScrollState(1)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
            .padding(bottom = 70.dp)
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        LocationHeader(uiState)

        Spacer(modifier = Modifier.height(20.dp))

        HeroWeatherCard(
            modifier = Modifier,
            weather = uiState.currentWeather,
            temperatureUnit = userPreferencesState?.temperatureUnitOption,
            liquidState = liquidState,
        )

        Spacer(modifier = Modifier.height(20.dp))

        WeatherStatsRow(
            weather = uiState.currentWeather,
            userPreferencesState = userPreferencesState,
            liquidState = liquidState,
        )

        Spacer(modifier = Modifier.height(24.dp))

        TodayForecastSection(
            hourlyForecasts = uiState.forecastDays?.hourlyForecasts ?: emptyList(),
            liquidState = liquidState,
        )

        Spacer(modifier = Modifier.height(Spacing.XLarge))

        FiveDayOutlookSection(
            hourlyForecasts = uiState.forecastDays?.hourlyForecasts ?: emptyList(),
            liquidState = liquidState,
        )

        Spacer(modifier = Modifier.height(100.dp))
    }
}