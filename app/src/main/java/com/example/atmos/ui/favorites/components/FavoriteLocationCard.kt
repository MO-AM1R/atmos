package com.example.atmos.ui.favorites.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.domain.model.FavoriteWeatherItem
import com.example.atmos.ui.core.components.LiquidGlassContainer
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.LiquidGlassConfig
import com.example.atmos.utils.formatTemperature
import com.example.atmos.utils.toLocalizedDigits
import com.example.atmos.utils.toWeatherIconRes
import io.github.fletchmckee.liquid.LiquidState


@Composable
fun FavoriteLocationCard(
    item: FavoriteWeatherItem,
    temperatureUnit: TemperatureUnit,
    liquidState: LiquidState
) {
    LiquidGlassContainer(
        modifier = Modifier.fillMaxWidth(),
        liquidState = liquidState,
        config = LiquidGlassConfig.Card,
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ResourceIcon(
                    resourceId = item.weatherIcon.toWeatherIconRes(),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = item.cityName
                            .ifEmpty { "${item.latitude}, ${item.longitude}" }
                            .toLocalizedDigits(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.extraColors.textPrimary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )

                    Text(
                        text = item.weatherMain,
                        fontSize = 14.sp,
                        color = MaterialTheme.extraColors.textMuted,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = item.temperature
                        .formatTemperature(temperatureUnit)
                        .toLocalizedDigits(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.extraColors.textPrimary,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = stringResource(R.string.high) + ":${
                        item.maxTemp.formatTemperature(temperatureUnit)
                            .toLocalizedDigits()
                    } " +
                            stringResource(R.string.low) + ":${
                        item.minTemp.formatTemperature(temperatureUnit)
                            .toLocalizedDigits()
                    }",
                    fontSize = 14.sp,
                    color = MaterialTheme.extraColors.textMuted,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}