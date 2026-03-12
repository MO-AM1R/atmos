package com.example.atmos.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography


@Composable
fun WeatherStatsRow(
    weather: CurrentWeather?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_humidity,
            label = stringResource(R.string.humidity),
            value = "${weather?.humidityPercent ?: "--"}%"
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_wind,
            label = stringResource(R.string.wind_speed),
            value = "${weather?.windSpeedRaw ?: "--"} m/s"
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_pressure,
            label = stringResource(R.string.pressure),
            value = "${weather?.pressureHpa ?: "--"} mb"
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_clouds,
            label = stringResource(R.string.clouds),
            value = "${weather?.cloudCoverPercent ?: "--"}%"
        )
    }
}

@Composable
fun StatCard(
    icon: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.XSmall)
    ) {
        ResourceIcon(
            modifier = Modifier.size(24.dp),
            resourceId = icon,
            color = Color(0xFF00D4FF)
        )
        Text(
            text = label,
            style = WeatherTypography.labelSmall,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
        Text(
            text = value,
            style = WeatherTypography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}