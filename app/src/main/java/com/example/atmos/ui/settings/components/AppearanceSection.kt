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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.data.enums.Language
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.theme.SettingsSectionBackground
import com.example.atmos.ui.theme.extraColors

@Composable
fun AppearanceSection(
    language: Language,
    onEvent: (SettingsEvent) -> Unit
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
        UnitRowHeader(
            iconRes = R.drawable.ic_language,
            iconTint = MaterialTheme.extraColors.violet,
            iconBackground = MaterialTheme.extraColors.violet.copy(alpha = 0.2f),
            label = stringResource(R.string.settings_language)
        )
        UnitSelector(
            options = Language.entries,
            selected = language,
            label = { it.value },
            onSelect = { lang ->
                onEvent(SettingsEvent.OnLanguageSelected(lang))
            }
        )
    }
}
