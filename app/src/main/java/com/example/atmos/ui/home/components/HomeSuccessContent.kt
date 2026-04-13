package com.example.atmos.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.home.state.HomeUiState
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.White
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.toLocalizedCountryNameWithFlag
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun HomeSuccessContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState(),
    onRefresh: () -> Unit = {},
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

        //TODO: extract to separated file
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ResourceIcon(
                    modifier = Modifier.size(18.dp),
                    resourceId = R.drawable.ic_location_point,
                    color = White,
                )

                Text(
                    text = uiState.currentWeather?.cityName ?: "--",
                    style = WeatherTypography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extraColors.textPrimary
                )
            }

            Text(
                text = uiState.forecastDays?.cityName?.plus(", ${uiState.forecastDays.countryCode.toLocalizedCountryNameWithFlag()}")
                    ?: "--",
                style = WeatherTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extraColors.textPrimary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        HeroWeatherCard(
            modifier = Modifier,
            weather = uiState.currentWeather,
            temperatureUnit = userPreferencesState?.temperatureUnitOption,
            onRefreshClick = onRefresh,
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