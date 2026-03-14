package com.example.atmos.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.R
import com.example.atmos.ui.onboarding.components.GradientBackground
import com.example.atmos.ui.settings.components.AppearanceSection
import com.example.atmos.ui.settings.components.LocationSection
import com.example.atmos.ui.settings.components.SettingsHeader
import com.example.atmos.ui.settings.components.SettingsSectionTitle
import com.example.atmos.ui.settings.components.UnitsSection
import com.example.atmos.ui.settings.state.SettingsNavigationEvent
import com.example.atmos.ui.settings.viewmodel.SettingsViewModel


@Composable
fun SettingsScreen(
    savedStateHandle: SavedStateHandle,
    navigateToMap: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val uiState = settingsViewModel.uiState.collectAsStateWithLifecycle()


    suspend fun observeOnEvents() {
        settingsViewModel.settingNavigationEvents.collect { event ->
            when (event) {
                SettingsNavigationEvent.NavigateToMap -> {
                    navigateToMap()
                }
            }
        }
    }

    suspend fun observeOnSavedStateHandle() {
        //TODO: put the key of the selected point
        savedStateHandle.getStateFlow<String?>("KEY", null)
            .collect { pointJson ->
                //TODO: convert the point

                savedStateHandle.remove<String>("KEY")
            }
    }


    LaunchedEffect(Unit) {
        observeOnEvents()
        observeOnSavedStateHandle()
    }

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
                locationOption = uiState.value.locationOption,
                storedLocation = "",
                onEvent = settingsViewModel::onEvent,
                onChangeClicked = navigateToMap
            )

            Spacer(modifier = Modifier.height(28.dp))

            SettingsSectionTitle(title = stringResource(R.string.settings_section_units))

            Spacer(modifier = Modifier.height(12.dp))

            UnitsSection(
                selectedTemperatureUnit = uiState.value.temperatureUnit,
                selectedWindUnit = uiState.value.windUnit,
                onEvent = settingsViewModel::onEvent,
            )

            Spacer(modifier = Modifier.height(28.dp))

            SettingsSectionTitle(title = stringResource(R.string.settings_section_appearance))

            Spacer(modifier = Modifier.height(12.dp))

            AppearanceSection(
                language = uiState.value.language,
                onEvent = settingsViewModel::onEvent,
            )
        }
    }
}
