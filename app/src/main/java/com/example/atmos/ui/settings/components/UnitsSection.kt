package com.example.atmos.ui.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.ui.core.components.LiquidGlassContainer
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.LiquidGlassConfig
import com.example.atmos.utils.localizedSymbol
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun UnitsSection(
    selectedTemperatureUnit: TemperatureUnit,
    selectedWindUnit: WindUnit,
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
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                UnitRowHeader(
                    iconRes = R.drawable.ic_thermometer,
                    iconTint = Color.Red,
                    iconBackground = Color.Red.copy(alpha = 0.3f),
                    label = stringResource(R.string.settings_temperature)
                )
                UnitSelector(
                    options = TemperatureUnit.entries,
                    selected = selectedTemperatureUnit,
                    label = { it.localizedSymbol() },
                    onSelect = { unit ->
                        onEvent(SettingsEvent.OnTemperatureUnitSelected(unit))
                    }
                )
            }

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                UnitRowHeader(
                    iconRes = R.drawable.ic_wind,
                    iconTint = extraColors.cyan,
                    iconBackground = extraColors.cyan.copy(alpha = 0.3f),
                    label = stringResource(R.string.settings_wind_speed)
                )
                UnitSelector(
                    options = WindUnit.entries,
                    selected = selectedWindUnit,
                    label = { it.localizedSymbol() },
                    onSelect = { unit ->
                        onEvent(SettingsEvent.OnWindUnitSelected(unit))
                    }
                )
            }
        }
    }
}
