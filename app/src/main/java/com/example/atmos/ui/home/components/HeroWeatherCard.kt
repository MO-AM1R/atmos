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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.atmos.R
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.AppConstants.ICONS_BASE_URL
import com.example.atmos.utils.formatTemperature
import com.example.atmos.utils.toLocalizedDigits
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun HeroWeatherCard(
    weather: CurrentWeather?,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier,
    temperatureUnit: TemperatureUnit?
) {
    val colors = MaterialTheme.extraColors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(colors.cardBackgroundStrong)
            .border(1.dp, colors.cardBorder, RoundedCornerShape(32.dp))
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
                color = colors.textPrimary
            )

            Text(
                text = weather?.let {
                    SimpleDateFormat(
                        "EEEE, MMMM d, yyyy",
                        Locale.getDefault()
                    ).format(Date(it.timestampUnix * 1000)).toLocalizedDigits()
                } ?: "--",
                style = WeatherTypography.bodyMedium,
                color = colors.textMuted
            )

            Spacer(modifier = Modifier.height(Spacing.XLarge))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(ICONS_BASE_URL + weather?.weatherIconCode + ".png")
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp),
            )

            Spacer(modifier = Modifier.height(Spacing.Large))

            Text(
                text = weather?.temperature?.formatTemperature(
                    temperatureUnit ?: TemperatureUnit.CELSIUS
                )?.toLocalizedDigits()
                    ?: "--°",
                style = WeatherTypography.displayLarge,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.height(Spacing.Small))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = weather?.weatherDescription ?: "--",
                    style = WeatherTypography.bodyMedium,
                    color = colors.textPrimary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))
        }
    }
}