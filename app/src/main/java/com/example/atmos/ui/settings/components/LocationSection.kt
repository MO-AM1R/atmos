package com.example.atmos.ui.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.ui.core.components.LiquidGlassContainer
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.utils.LiquidGlassConfig
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun LocationSection(
    locationOption: LocationOption,
    storedLocation: String?,
    isLoadingLocationName: Boolean,
    navigateToMap: () -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SpecificLocationToggleRow(
                isSpecificEnabled = locationOption == LocationOption.SPECIFIC_LOCATION,
                onToggled = { isEnabled ->
                    if (isEnabled) {
                        onEvent(SettingsEvent.OnLocationOptionChanged(LocationOption.SPECIFIC_LOCATION))
                    } else {
                        onEvent(SettingsEvent.OnLocationOptionChanged(LocationOption.GPS))
                    }
                }
            )

            AnimatedVisibility(
                visible = locationOption == LocationOption.SPECIFIC_LOCATION
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        thickness = 0.5.dp,
                        color = Color(0xFF4A4565).copy(alpha = 0.4f)
                    )
                    ChooseFromMapRow(
                        storedLocation = storedLocation,
                        isLoadingLocationName = isLoadingLocationName,
                        navigateToMap = navigateToMap
                    )
                }
            }

            AnimatedVisibility(
                visible = locationOption == LocationOption.GPS
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        thickness = 0.5.dp,
                        color = Color(0xFF4A4565).copy(alpha = 0.4f)
                    )
                    GpsAutoRow()
                }
            }
        }
    }
}