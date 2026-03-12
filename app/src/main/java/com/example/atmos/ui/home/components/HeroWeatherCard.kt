package com.example.atmos.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun HeroWeatherCard(
    weather: CurrentWeather?,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(24.dp)
    ) {
        IconButton(
            onClick = onRefreshClick,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather?.cityName ?: "--",
                style = WeatherTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = weather?.let {
                    SimpleDateFormat(
                        "EEEE, MMMM d, yyyy",
                        Locale.getDefault()
                    ).format(Date(it.timestampUnix * 1000))
                } ?: "--",
                style = WeatherTypography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(Spacing.XLarge))

            ResourceIcon(
                modifier = Modifier.size(100.dp),
                resourceId = R.drawable.cloud_sun,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(Spacing.Large))

            Text(
                text = weather?.let { "${it.temperature.toInt()}°" } ?: "--°",
                style = WeatherTypography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(Spacing.Small))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = weather?.weatherMain ?: "--",
                    style = WeatherTypography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Text(
                text = weather?.weatherDescription ?: "--",
                style = WeatherTypography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}