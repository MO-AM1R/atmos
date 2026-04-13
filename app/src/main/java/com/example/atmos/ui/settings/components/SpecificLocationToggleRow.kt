package com.example.atmos.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.ui.theme.BackgroundDark2
import com.example.atmos.ui.theme.White
import com.example.atmos.ui.theme.extraColors

@Composable
fun SpecificLocationToggleRow(
    isSpecificEnabled: Boolean,
    onToggled: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BackgroundDark2),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        if (isSpecificEnabled) R.drawable.ic_pin
                        else R.drawable.ic_gps
                    ),
                    contentDescription = null,
                    tint = extraColors.textPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.settings_specific_location),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = extraColors.textPrimary
                )
                Text(
                    text = stringResource(
                        if (isSpecificEnabled) R.string.settings_specific_location_subtitle
                        else R.string.settings_gps_auto_subtitle
                    ),
                    fontSize = 12.sp,
                    color = extraColors.textMuted
                )
            }
        }

        Switch(
            checked = isSpecificEnabled,
            onCheckedChange = onToggled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = White,
                checkedTrackColor = BackgroundDark2,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                checkedBorderColor = Color.Transparent,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}
