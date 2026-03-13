package com.example.atmos.ui.settings.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.atmos.ui.theme.extraColors

@Composable
fun SettingsSectionTitle(title: String) {
    val colors = MaterialTheme.extraColors

    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = colors.textPrimary
    )
}
