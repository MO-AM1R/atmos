package com.example.atmos.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.ui.core.components.LiquidGlassContainer
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.formatWindSpeed
import com.example.atmos.utils.toLocalizedDigits
import io.github.fletchmckee.liquid.LiquidState


@Composable
fun WeatherStatsRow(
    weather: CurrentWeather?,
    modifier: Modifier = Modifier,
    userPreferencesState: UserPreferences?,
    liquidState: LiquidState,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_humidity,
            label = stringResource(R.string.humidity),
            value = "${weather?.humidityPercent ?: "--"}%".toLocalizedDigits(),
            liquidState = liquidState,
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_wind,
            label = stringResource(R.string.wind_speed),
            value = weather?.windSpeedRaw?.formatWindSpeed(
                userPreferencesState?.windUnitOption ?: WindUnit.METERS_PER_SECOND
            )?.toLocalizedDigits() ?: "--",
            liquidState = liquidState,
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_pressure,
            label = stringResource(R.string.pressure),
            value = "${weather?.pressureHpa?.toString()?.toLocalizedDigits() ?: "--"} " + stringResource(R.string.pressure_unit),
            liquidState = liquidState,
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_clouds,
            label = stringResource(R.string.clouds),
            value = "${weather?.cloudCoverPercent ?: "--"}%".toLocalizedDigits(),
            liquidState = liquidState,
        )
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    icon: Int,
    label: String,
    value: String,
    liquidState: LiquidState,
) {
    val colors = MaterialTheme.extraColors

    LiquidGlassContainer(
        modifier = modifier.fillMaxWidth(),
        liquidState = liquidState,
    ) {
        Column(
            modifier = modifier
                .padding(vertical = 12.dp, horizontal = 8.dp)
                .size(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            ResourceIcon(
                modifier = Modifier.size(24.dp),
                resourceId = icon,
                color = colors.textPrimary
            )
            Text(
                text = label,
                style = WeatherTypography.labelSmall,
                color = colors.textMuted,
                textAlign = TextAlign.Center
            )
            Text(
                text = value,
                style = WeatherTypography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}