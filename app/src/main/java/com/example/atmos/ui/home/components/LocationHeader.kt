package com.example.atmos.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.domain.model.CurrentWeather
import com.example.atmos.domain.model.Forecast
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.White
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.toFlagEmoji
import com.example.atmos.utils.toLocalizedCountryName

@Composable
fun LocationHeader(
    currentWeather: CurrentWeather?,
    forecastDays: Forecast?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
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
                    text = currentWeather?.cityName ?: "--",
                    style = WeatherTypography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extraColors.textPrimary
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = forecastDays?.countryCode?.toFlagEmoji() ?: "--",
                    style = WeatherTypography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extraColors.textPrimary
                )

                Text(
                    text = forecastDays?.cityName
                        ?.plus(", ${forecastDays.countryCode.toLocalizedCountryName()}")
                        ?: "--",
                    style = WeatherTypography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extraColors.textPrimary
                )
            }
        }
    }
}