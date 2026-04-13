package com.example.atmos.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.ui.core.components.LiquidGlassContainer
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.formatTemperature
import com.example.atmos.utils.nullableToWeatherIconRes
import com.example.atmos.utils.toLocalizedDigits
import io.github.fletchmckee.liquid.LiquidState


@Composable
fun HeroWeatherCard(
    modifier: Modifier = Modifier,
    weather: CurrentWeather?,
    temperatureUnit: TemperatureUnit? = TemperatureUnit.CELSIUS,
    liquidState: LiquidState
) {
    val colors = extraColors

    LiquidGlassContainer(
        liquidState= liquidState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 32.dp, horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ResourceIcon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(76.dp),
                resourceId = weather?.weatherIconCode
                    .nullableToWeatherIconRes(),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = weather?.temperature?.formatTemperature(
                        temperatureUnit ?: TemperatureUnit.CELSIUS
                    )?.toLocalizedDigits()
                        ?: "--°",
                    style = WeatherTypography.displayMedium.copy(
                        fontSize = 50.sp
                    ),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    color = colors.textPrimary
                )

                Text(
                    text = weather?.weatherDescription ?: "--",
                    style = WeatherTypography.bodyMedium,
                    color = colors.textPrimary,
                )

                Text(
                    text = stringResource(
                        R.string.feels_like,
                        weather?.feelsLike?.toString()
                            ?.toLocalizedDigits() + "°"
                    ),
                    style = WeatherTypography.bodyMedium,
                    color = colors.textPrimary,
                )
            }

            Column(
                modifier = Modifier.align(Alignment.Bottom),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "↗ " + weather?.maximumTemp?.toString()
                        ?.toLocalizedDigits() + "°",
                    style = WeatherTypography.bodyMedium,
                    color = colors.textPrimary,
                )

                Text(
                    text = "↓ " + weather?.minimumTemp?.toString()
                        ?.toLocalizedDigits() + "°",
                    style = WeatherTypography.bodyMedium,
                    color = colors.textPrimary,
                )
            }
        }
    }
}