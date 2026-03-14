package com.example.atmos.ui.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.ui.theme.extraColors


@Composable
fun GpsAutoRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_gps),
            contentDescription = null,
            tint = MaterialTheme.extraColors.textPrimary,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = stringResource(R.string.settings_gps_auto_detecting),
            fontSize = 13.sp,
            color = MaterialTheme.extraColors.textMuted
        )
    }
}
