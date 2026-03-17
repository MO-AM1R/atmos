package com.example.atmos.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.domain.model.HourlyForecast
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.toLocalizedDigits
import com.example.atmos.utils.toWeatherIconRes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TodayForecastSection(
    hourlyForecasts: List<HourlyForecast>,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.extraColors

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        Text(
            text = stringResource(R.string.today_s_forecast),
            style = WeatherTypography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            items(
                items = hourlyForecasts,
                key = { it.timestampUnix }
            ) { forecast ->
                HourlyForecastCard(forecast = forecast)
            }
        }
    }
}


@Composable
fun HourlyForecastCard(
    modifier: Modifier = Modifier,
    forecast: HourlyForecast
) {
    val colors = MaterialTheme.extraColors

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(colors.cardBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.XSmall)
    ) {
        Text(
            text = SimpleDateFormat("h a", Locale.getDefault())
                .format(Date(forecast.timestampUnix * 1000)).toLocalizedDigits(),
            style = WeatherTypography.labelLarge,
            color = colors.textMuted
        )

        ResourceIcon(
            resourceId = forecast.weatherIconCode.toWeatherIconRes(),
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp),
        )

        Text(
            text = "${forecast.temperature.toInt()}°".toLocalizedDigits(),
            style = WeatherTypography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary
        )
    }
}