package com.example.atmos.ui.home.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.ui.home.state.HomeUiState
import com.example.atmos.ui.theme.Spacing

@Composable
fun HomeSuccessContent(
    uiState: HomeUiState,
    scrollState: ScrollState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    userPreferencesState: UserPreferences?
) {
    val heroAlpha by remember {
        derivedStateOf {
            val maxScroll = 600f
            (1f - (scrollState.value / maxScroll)).coerceIn(0f, 1f)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        HeroWeatherCard(
            modifier = Modifier.graphicsLayer { alpha = heroAlpha },
            weather = uiState.currentWeather,
            temperatureUnit = userPreferencesState?.temperatureUnitOption,
            onRefreshClick = onRefresh
        )

        Spacer(modifier = Modifier.height(20.dp))

        WeatherStatsRow(weather = uiState.currentWeather, userPreferencesState = userPreferencesState)

        Spacer(modifier = Modifier.height(24.dp))

        TodayForecastSection(
            hourlyForecasts = uiState.forecastDays?.hourlyForecasts ?: emptyList()
        )

        Spacer(modifier = Modifier.height(Spacing.XLarge))

        FiveDayOutlookSection(
            hourlyForecasts = uiState.forecastDays?.hourlyForecasts ?: emptyList()
        )

        Spacer(modifier = Modifier.height(150.dp))
    }
}