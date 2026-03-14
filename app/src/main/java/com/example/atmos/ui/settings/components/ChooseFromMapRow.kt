package com.example.atmos.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.ui.theme.extraColors


@Composable
fun ChooseFromMapRow(
    storedLocation: String?,
    onChangeClicked: () -> Unit  // TODO: Navigate to map screen
) {
    // TODO: Navigate to Map Screen on click
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChangeClicked() }
            .clip(RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.extraColors.cardBorder.copy(alpha = 0.1f))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.extraColors.textMuted,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = storedLocation ?: stringResource(R.string.settings_choose_map),
                fontSize = 15.sp,
                color = MaterialTheme.extraColors.textPrimary
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.extraColors.textPrimary
        )
    }
}