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
fun PermanentDenyDialog(
    onOpenSettings: () -> Unit,
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
                color = Color(0xFFFF6B6B)
            )
        },
        title = {
            Text(
                text = stringResource(R.string.permission_denied),
                style = WeatherTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = stringResource(R.string.location_permission_was_permanently_denied_please_open_app_settings_and_enable_location_permission_manually),
                style = WeatherTypography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = onOpenSettings,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00D4FF)
                )
            ) {
                Text(
                    text = "Open Settings",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        },
        containerColor = Color(0xFF1A1A2E)
    )
}