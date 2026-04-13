package com.example.atmos.ui.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.data.enums.Language
import com.example.atmos.ui.core.components.LiquidGlassContainer
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.LiquidGlassConfig
import com.example.atmos.utils.localizedValue
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun AppearanceSection(
    language: Language,
    onEvent: (SettingsEvent) -> Unit,
    liquidState: LiquidState
) {
    LiquidGlassContainer(
        modifier = Modifier
            .fillMaxWidth(),
        liquidState = liquidState,
        config = LiquidGlassConfig.Card,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UnitRowHeader(
                iconRes = R.drawable.ic_language,
                iconTint = extraColors.violet,
                iconBackground = extraColors.violet.copy(alpha = 0.2f),
                label = stringResource(R.string.settings_language)
            )
            UnitSelector(
                options = Language.entries,
                selected = language,
                label = { it.localizedValue() },
                onSelect = { lang ->
                    onEvent(SettingsEvent.OnLanguageSelected(lang))
                }
            )
        }
    }
}
