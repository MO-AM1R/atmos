package com.example.atmos.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.theme.SettingsSectionBackground
import com.example.atmos.ui.theme.extraColors

@Composable
fun UnitsSection(
    selectedTemperatureUnit: TemperatureUnit,
    selectedWindUnit: WindUnit,
    onEvent: (SettingsEvent) -> Unit,
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
            .padding(16.dp),
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
                label = { it.symbol },
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
                iconTint = MaterialTheme.extraColors.cyan,
                iconBackground = MaterialTheme.extraColors.cyan.copy(alpha = 0.3f),
                label = stringResource(R.string.settings_wind_speed)
            )
            UnitSelector(
                options = WindUnit.entries,
                selected = selectedWindUnit,
                label = { it.symbol },
                onSelect = { unit ->
                    onEvent(SettingsEvent.OnWindUnitSelected(unit))
                }
            )
        }
    }
}
