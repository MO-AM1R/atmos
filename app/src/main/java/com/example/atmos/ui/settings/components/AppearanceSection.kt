package com.example.atmos.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.atmos.data.enums.Language
import com.example.atmos.ui.theme.SettingsSectionBackground
import com.example.atmos.ui.theme.extraColors

@Composable
fun AppearanceSection(
    language: Language,
    onLanguageClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SettingsSectionBackground.copy(alpha = 0.3f))
            .border(
                width = 1.dp,
                color = MaterialTheme.extraColors.cardBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(4.dp)
    ) {
        LanguageRow(
            language = language,
            onClick = onLanguageClicked
        )
    }
}
