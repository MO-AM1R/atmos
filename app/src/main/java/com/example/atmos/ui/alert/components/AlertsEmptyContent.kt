package com.example.atmos.ui.alert.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.ui.theme.extraColors

@Composable
fun AlertsEmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_alerts),
                contentDescription = null,
                tint = extraColors.textMuted,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = stringResource(R.string.alerts_empty_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = extraColors.textPrimary
            )
            Text(
                text = stringResource(R.string.alerts_empty_subtitle),
                fontSize = 14.sp,
                color = extraColors.textMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}