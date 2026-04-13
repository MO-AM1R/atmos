package com.example.atmos.ui.alert.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.data.enums.AlertType
import com.example.atmos.domain.model.AlertUiItem
import com.example.atmos.ui.core.components.LiquidGlassContainer
import com.example.atmos.ui.theme.BackgroundDark2
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.LiquidGlassConfig
import com.example.atmos.utils.toLocalizedDigits
import io.github.fletchmckee.liquid.LiquidState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AlertItemCard(
    item: AlertUiItem,
    onToggle: (Boolean) -> Unit,
    liquidState: LiquidState
) {
    val timeFormatter = remember { SimpleDateFormat("hh:mm aa", Locale.getDefault()) }

    LiquidGlassContainer(
        modifier = Modifier
            .fillMaxWidth(),
        liquidState = liquidState,
        config = LiquidGlassConfig.Card
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(
                            if (item.type == AlertType.NOTIFICATION) R.drawable.ic_alerts
                            else R.drawable.ic_alarm
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.extraColors.textPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = stringResource(
                            if (item.type == AlertType.NOTIFICATION)
                                R.string.alert_type_notification
                            else
                                R.string.alert_type_alarm
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (item.isExpired)
                            MaterialTheme.extraColors.textMuted
                        else
                            MaterialTheme.extraColors.textPrimary
                    )

                    Text(
                        text = buildString {
                            append(
                                stringResource(
                                    R.string.alert_start_time,
                                    timeFormatter.format(Date(item.startTimeMs)).toLocalizedDigits()
                                )
                            )
                            item.endTimeMs?.let { endTime ->
                                append(" • ")
                                append(
                                    stringResource(
                                        R.string.alert_end_time,
                                        timeFormatter.format(Date(endTime)).toLocalizedDigits()
                                    )
                                )
                            }
                        },
                        fontSize = 14.sp,
                        color = MaterialTheme.extraColors.textMuted
                    )

                    if (item.isExpired) {
                        Text(
                            text = stringResource(R.string.alert_expired),
                            fontSize = 13.sp,
                            color = Color(0xFFE05A5A)
                        )
                    }
                }
            }

            if (!item.isExpired) {
                Switch(
                    checked = item.isEnabled,
                    onCheckedChange = { if (!item.isExpired) onToggle(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = BackgroundDark2,
                        checkedBorderColor = Color.Transparent,

                        uncheckedBorderColor = Color.Transparent,
                        uncheckedThumbColor = MaterialTheme.extraColors.textPrimary,
                        uncheckedTrackColor = MaterialTheme.extraColors.textMuted,
                        uncheckedIconColor = MaterialTheme.extraColors.textPrimary,
                    )
                )
            }
        }
    }
}