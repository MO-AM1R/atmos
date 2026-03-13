package com.example.atmos.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.atmos.R
import com.example.atmos.domain.model.ForecastDay
import com.example.atmos.domain.model.HourlyForecast
import com.example.atmos.domain.model.groupIntoDays
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.AppConstants.ICONS_BASE_URL


@Composable
fun FiveDayOutlookSection(
    hourlyForecasts: List<HourlyForecast>,
    modifier: Modifier = Modifier
) {
    val dayForecasts = remember(hourlyForecasts) {
        hourlyForecasts.groupIntoDays()
    }

    val colors = MaterialTheme.extraColors

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        Text(
            text = stringResource(R.string._5_day_outlook),
            style = WeatherTypography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary
        )

        dayForecasts.forEach { dayForecast ->
            DayForecastRow(forecastDay = dayForecast)
        }
    }
}


@Composable
fun DayForecastRow(
    forecastDay: ForecastDay,
    modifier: Modifier = Modifier
) {
    val globalMin = -10.0
    val globalMax = 45.0
    val globalRange = globalMax - globalMin

    val startFraction = ((forecastDay.minTemp - globalMin) / globalRange)
        .coerceIn(0.0, 1.0).toFloat()
    val endFraction = ((forecastDay.maxTemp - globalMin) / globalRange)
        .coerceIn(0.0, 1.0).toFloat()

    val colors = MaterialTheme.extraColors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.cardBackground)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.width(80.dp)) {
            Text(
                text = forecastDay.dayName,
                style = WeatherTypography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = forecastDay.dateLabel,
                style = WeatherTypography.labelSmall,
                color = colors.textMuted
            )
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ICONS_BASE_URL + forecastDay.representativeIcon + ".png")
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp),
        )

        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            val totalWidth = maxWidth

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White.copy(alpha = 0.15f))
            )

            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(totalWidth * (endFraction - startFraction))
                    .offset(x = totalWidth * startFraction)
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF00D4FF),
                                Color(0xFF8A6FFF)
                            )
                        )
                    )
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${forecastDay.minTemp.toInt()}°",
                style = WeatherTypography.titleMedium,
                color = colors.textMuted
            )
            Text(
                text = "${forecastDay.maxTemp.toInt()}°",
                style = WeatherTypography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
        }
    }
}