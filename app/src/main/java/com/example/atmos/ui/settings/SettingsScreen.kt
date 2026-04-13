package com.example.atmos.ui.settings

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.MainActivity
import com.example.atmos.R
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.ui.onboarding.components.GradientBackground
import com.example.atmos.ui.settings.components.AppearanceSection
import com.example.atmos.ui.settings.components.LocationSection
import com.example.atmos.ui.settings.components.SettingsHeader
import com.example.atmos.ui.settings.components.SettingsSectionTitle
import com.example.atmos.ui.settings.components.UnitsSection
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.settings.state.SettingsNavigationEvent
import com.example.atmos.ui.settings.viewmodel.SettingsViewModel
import com.example.atmos.utils.AppConstants
import com.mapbox.geojson.Point
import io.github.fletchmckee.liquid.rememberLiquidState


@Composable
fun SettingsScreen(
    savedStateHandle: SavedStateHandle,
    navigateToMap: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val uiState = settingsViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    suspend fun observeOnEvents() {
        settingsViewModel.settingNavigationEvents.collect { event ->
            when (event) {
                SettingsNavigationEvent.NavigateToMap -> {
                    navigateToMap()
                }

                SettingsNavigationEvent.RestartActivity -> {
                    context.startActivity(
                        Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    )
                }
            }
        }
    }

    suspend fun observeOnSavedStateHandle() {
        savedStateHandle.getStateFlow<String?>(AppConstants.MapConstants.SELECTED_POINT_KEY, null)
            .collect { pointJson ->
                pointJson?.let {
                    val point: Point = Point.fromJson(it)

                    settingsViewModel.onEvent(
                        SettingsEvent.OnLocationPointSelected(
                            StoredPoint(
                                latitude = point.latitude(),
                                longitude = point.longitude()
                            )
                        )
                    )
                }
                savedStateHandle.remove<String>(AppConstants.MapConstants.SELECTED_POINT_KEY)
            }
    }


    LaunchedEffect(Unit) {
        observeOnEvents()
    }

    LaunchedEffect(Unit) {
        observeOnSavedStateHandle()
    }

    val liquidState = rememberLiquidState()

    GradientBackground(
        liquidState = liquidState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, bottom = 120.dp)
        ) {

            SettingsHeader()

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                SettingsSectionTitle(title = stringResource(R.string.settings_section_location))

                Spacer(modifier = Modifier.height(12.dp))

                LocationSection(
                    locationOption = uiState.value.locationOption,
                    storedLocation = uiState.value.storedLocationName,
                    isLoadingLocationName = uiState.value.isLoadingLocationName,
                    onEvent = settingsViewModel::onEvent,
                    navigateToMap = navigateToMap,
                    liquidState = liquidState,
                )

                Spacer(modifier = Modifier.height(28.dp))

                SettingsSectionTitle(title = stringResource(R.string.settings_section_units))

                Spacer(modifier = Modifier.height(12.dp))

                UnitsSection(
                    selectedTemperatureUnit = uiState.value.temperatureUnit,
                    selectedWindUnit = uiState.value.windUnit,
                    onEvent = settingsViewModel::onEvent,
                    liquidState = liquidState,
                )

                Spacer(modifier = Modifier.height(28.dp))

                SettingsSectionTitle(title = stringResource(R.string.settings_section_appearance))

                Spacer(modifier = Modifier.height(12.dp))

                AppearanceSection(
                    language = uiState.value.language,
                    onEvent = settingsViewModel::onEvent,
                    liquidState = liquidState,
                )
            }
        }

    }
}
