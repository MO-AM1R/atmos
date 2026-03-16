package com.example.atmos.ui.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.atmos.R
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.domain.model.FavoriteWeatherItem
import com.example.atmos.ui.theme.SettingsSectionBackground
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.AppConstants.ICONS_BASE_URL
import com.example.atmos.utils.formatTemperature
import kotlin.text.ifEmpty

@Composable
fun FavoriteLocationCard(
    item: FavoriteWeatherItem,
    temperatureUnit: TemperatureUnit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SettingsSectionBackground.copy(alpha = 0.3f))
            .border(
                width = 1.dp,
                color = MaterialTheme.extraColors.cardBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(ICONS_BASE_URL + item.weatherIcon + ".png")
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),) {
                Text(
                    text = item.cityName.ifEmpty { "${item.latitude}, ${item.longitude}" },
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.extraColors.textPrimary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = item.weatherMain,
                    fontSize = 12.sp,
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
                text = item.temperature.formatTemperature(temperatureUnit),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.extraColors.textPrimary,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "H:${item.maxTemp.formatTemperature(temperatureUnit)} " +
                        "L:${item.minTemp.formatTemperature(temperatureUnit)}",
                fontSize = 12.sp,
                color = MaterialTheme.extraColors.textMuted,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}