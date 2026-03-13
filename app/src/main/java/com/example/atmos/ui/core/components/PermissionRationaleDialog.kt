package com.example.atmos.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.ui.theme.WeatherTypography

@Composable
fun PermissionRationaleDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF1A1A2E)),
        icon = {
            ResourceIcon(
                modifier = Modifier.size(40.dp),
                resourceId = R.drawable.ic_location,
                color = Color(0xFF00D4FF)
            )
        },
        title = {
            Text(
                text = stringResource(R.string.location_permission),
                style = WeatherTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = stringResource(R.string.atmos_needs_location_access_to_show_accurate_weather_for_your_area_please_allow_location_permission_to_continue),
                style = WeatherTypography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00D4FF)
                )
            ) {
                Text(
                    text = "Allow",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.not_now),
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        },
        containerColor = Color(0xFF1A1A2E)
    )
}