package com.example.atmos.ui.settings.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.unit.dp
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.ui.settings.state.SettingsEvent
import com.example.atmos.ui.theme.SettingsSectionBackground
import com.example.atmos.ui.theme.extraColors

@Composable
fun LocationSection(
    locationOption: LocationOption,
    storedLocation: String?,
    onChangeClicked: () -> Unit,
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
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SpecificLocationToggleRow(
            isSpecificEnabled = locationOption == LocationOption.SPECIFIC_LOCATION,
            onToggled = { isEnabled ->
                if (isEnabled) {
                    onEvent(SettingsEvent.OnGpsToggled(LocationOption.SPECIFIC_LOCATION))
                } else {
                    onEvent(SettingsEvent.OnGpsToggled(LocationOption.GPS))
                }
            }
        )

        AnimatedVisibility(
            visible = locationOption == LocationOption.SPECIFIC_LOCATION
        ) {
            Column {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFF4A4565).copy(alpha = 0.4f)
                )
                ChooseFromMapRow(
                    storedLocation = storedLocation,
                    onChangeClicked = onChangeClicked
                )
            }
        }

        AnimatedVisibility(
            visible = locationOption == LocationOption.GPS
        ) {
            Column {
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
