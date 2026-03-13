package com.example.atmos.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.ui.onboarding.components.GradientBackground
import com.example.atmos.ui.settings.components.AppearanceSection
import com.example.atmos.ui.settings.components.LocationSection
import com.example.atmos.ui.settings.components.SettingsHeader
import com.example.atmos.ui.settings.components.SettingsSectionTitle
import com.example.atmos.ui.settings.components.UnitsSection
import com.example.atmos.ui.settings.state.SettingsUiState


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SettingsScreen(
    uiState: SettingsUiState = SettingsUiState(),
    onGpsToggled: (LocationOption) -> Unit = {},
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
                onToggled = onGpsToggled,
                onChangeClicked = onNavigateToMapScreen,
                storedLocation = uiState.storedLocation
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
