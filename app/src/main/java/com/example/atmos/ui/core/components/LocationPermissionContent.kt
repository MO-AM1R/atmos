package com.example.atmos.ui.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
fun LocationPermissionContent(
    modifier: Modifier = Modifier,
    onRequestPermission: () -> Unit = {},
    onOpenSettings: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxHeight().padding(20.dp),
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
                resourceId = R.drawable.ic_location,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(Spacing.XLarge))

            Text(
                text = "Location Permission Needed",
                style = WeatherTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Text(
                text = stringResource(R.string.atmos_needs_your_location_to_provide_accurate_weather_information_for_your_area),
                style = WeatherTypography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.XXLarge))

            Button(
                onClick = onRequestPermission,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.25f)
                )
            ) {
                Text(
                    text = stringResource(R.string.allow_location),
                    color = Color.White,
                    style = WeatherTypography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            OutlinedButton(
                onClick = onOpenSettings,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
            ) {
                Text(
                    text = stringResource(R.string.open_settings),
                    color = Color.White,
                    style = WeatherTypography.titleMedium
                )
            }
        }
    }
}