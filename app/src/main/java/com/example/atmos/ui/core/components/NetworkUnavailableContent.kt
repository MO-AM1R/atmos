package com.example.atmos.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.extraColors


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NetworkUnavailableContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.extraColors.cardBackgroundStrong)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ResourceIcon(
                modifier = Modifier.size(80.dp),
                resourceId = R.drawable.ic_no_wifi,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(Spacing.XLarge))

            Text(
                text = stringResource(R.string.no_internet_connection),
                style = WeatherTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Text(
                text = stringResource(R.string.please_check_your_internet_connection_weather_data_requires_network_access_on_first_launch),
                style = WeatherTypography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}