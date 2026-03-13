package com.example.atmos.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.ui.onboarding.components.GradientBackground
import com.example.atmos.ui.settings.state.SettingsUiState
import com.example.atmos.ui.theme.BackgroundDark2
import com.example.atmos.ui.theme.SettingsSectionBackground
import com.example.atmos.ui.theme.extraColors


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SettingsScreen(
    uiState: SettingsUiState = SettingsUiState(),
    onGpsToggled: (Boolean) -> Unit = {},
    onTemperatureUnitSelected: (TemperatureUnit) -> Unit = {},
    onWindUnitSelected: (WindUnit) -> Unit = {},
    onLanguageClicked: () -> Unit = {},
    onNavigateToMapScreen: () -> Unit = {},
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            SettingsHeader()

            Spacer(modifier = Modifier.height(24.dp))

            SettingsSectionTitle(title = stringResource(R.string.settings_section_location))

            Spacer(modifier = Modifier.height(12.dp))

            LocationSection(
                locationOption = uiState.locationOption,
                onGpsToggled = onGpsToggled,
                onChooseMapClicked = onNavigateToMapScreen
            )

            Spacer(modifier = Modifier.height(28.dp))

            SettingsSectionTitle(title = stringResource(R.string.settings_section_units))

            Spacer(modifier = Modifier.height(12.dp))

            UnitsSection(
                selectedTemperatureUnit = uiState.temperatureUnit,
                selectedWindUnit = uiState.windUnit,
                onTemperatureUnitSelected = onTemperatureUnitSelected,
                onWindUnitSelected = onWindUnitSelected
            )

            Spacer(modifier = Modifier.height(28.dp))

            SettingsSectionTitle(title = stringResource(R.string.settings_section_appearance))

            Spacer(modifier = Modifier.height(12.dp))

            AppearanceSection(
                language = uiState.language,
                onLanguageClicked = onLanguageClicked
            )
        }
    }
}

@Composable
private fun SettingsHeader() {
    val colors = MaterialTheme.extraColors

    Column {
        Text(
            text = stringResource(R.string.settings_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.settings_subtitle),
            fontSize = 14.sp,
            color = colors.textMuted
        )
    }
}

@Composable
private fun SettingsSectionTitle(title: String) {
    val colors = MaterialTheme.extraColors

    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = colors.textPrimary
    )
}

@Composable
private fun LocationSection(
    locationOption: LocationOption,
    onGpsToggled: (Boolean) -> Unit,
    onChooseMapClicked: () -> Unit
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
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        GpsLocationRow(
            isEnabled = locationOption == LocationOption.GPS,
            onToggled = onGpsToggled
        )

        Spacer(Modifier.height(12.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 12.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )

        Spacer(Modifier.height(12.dp))

        ChooseFromMapRow(
            onClick = onChooseMapClicked
        )
    }
}

@Composable
private fun GpsLocationRow(
    isEnabled: Boolean,
    onToggled: (Boolean) -> Unit
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
                    painter = painterResource(R.drawable.ic_gps),
                    contentDescription = null,
                    tint = MaterialTheme.extraColors.textPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.settings_gps_title),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.extraColors.textPrimary
                )
                Text(
                    text = stringResource(R.string.settings_gps_subtitle),
                    fontSize = 12.sp,
                    color = MaterialTheme.extraColors.textMuted
                )
            }
        }

        // TODO: State = isEnabled (true → LocationOption.GPS, false → LocationOption.SPECIFIC_LOCATION)
        // TODO: Request location permission when toggled to true
        Switch(
            checked = isEnabled,
            onCheckedChange = onToggled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}

@Composable
private fun ChooseFromMapRow(
    onClick: () -> Unit
) {
    // TODO: Navigate to Map Screen on click
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
                text = stringResource(R.string.settings_choose_map),
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

@Composable
private fun UnitsSection(
    selectedTemperatureUnit: TemperatureUnit,
    selectedWindUnit: WindUnit,
    onTemperatureUnitSelected: (TemperatureUnit) -> Unit,
    onWindUnitSelected: (WindUnit) -> Unit
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
            // TODO: State = selectedTemperatureUnit
            UnitSelector(
                options = TemperatureUnit.entries,
                selected = selectedTemperatureUnit,
                label = { it.symbol },
                onSelect = onTemperatureUnitSelected
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
            // TODO: State = selectedWindUnit
            UnitSelector(
                options = WindUnit.entries,
                selected = selectedWindUnit,
                label = { it.symbol },
                onSelect = onWindUnitSelected
            )
        }
    }
}

@Composable
private fun UnitRowHeader(
    iconRes: Int,
    iconTint: Color,
    iconBackground: Color,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
        }

        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.extraColors.textPrimary
        )
    }
}


@Composable
private fun <T> UnitSelector(
    options: List<T>,
    selected: T,
    label: (T) -> String,
    onSelect: (T) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(SettingsSectionBackground.copy(alpha = 0.3f))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        // TODO: State = isSelected → primary color, else transparent
                        if (isSelected) MaterialTheme.extraColors.violet
                        else Color.Transparent
                    )
                    .clickable { onSelect(option) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label(option),
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    // TODO: State = isSelected → White, else onSurfaceVariant
                    color = if (isSelected) Color.White
                    else MaterialTheme.extraColors.textMuted
                )
            }
        }
    }
}

@Composable
private fun AppearanceSection(
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

@Composable
private fun LanguageRow(
    language: Language,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
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
                    .background(MaterialTheme.extraColors.violet.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_language),
                    contentDescription = null,
                    tint = MaterialTheme.extraColors.violet,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.settings_language),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.extraColors.textPrimary
                )
                Text(
                    text = language.localeCode,
                    fontSize = 12.sp,
                    color = MaterialTheme.extraColors.textMuted
                )
            }
        }

        // TODO: State = current Language (language.localeCode as label)
        // TODO: On click → Show language picker bottom sheet
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(SettingsSectionBackground.copy(alpha = 0.3f))
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = language.localeCode,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.extraColors.textPrimary
            )
        }
    }
}