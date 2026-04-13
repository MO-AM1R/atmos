package com.example.atmos.ui.alert.components

import android.app.TimePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.data.enums.AlertType
import com.example.atmos.ui.theme.extraColors
import com.example.atmos.utils.toLocalizedDigits
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlertBottomSheet(
    onDismiss: () -> Unit,
    onSave: (startTimeMs: Long, endTimeMs: Long?, type: AlertType) -> Unit
) {
    val context = LocalContext.current

    var startTimeMs by remember { mutableStateOf<Long?>(null) }
    var endTimeMs by remember { mutableStateOf<Long?>(null) }
    var selectedType by remember { mutableStateOf(AlertType.NOTIFICATION) }

    val startTimeText = startTimeMs?.let {
        SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(Date(it))
    } ?: stringResource(R.string.alert_sheet_time_placeholder)

    val endTimeText = endTimeMs?.let {
        SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(Date(it))
    } ?: stringResource(R.string.alert_sheet_time_placeholder)

    fun showTimePicker(onTimeSelected: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            context,
            { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                onTimeSelected(calendar.timeInMillis)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E1B2E),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White.copy(alpha = 0.2f))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.alert_sheet_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.alert_sheet_start_time),
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
                AlertTimeField(
                    text = startTimeText.toLocalizedDigits(),
                    onClick = { showTimePicker { startTimeMs = it } }
                )
            }

            AnimatedVisibility(
                visible = selectedType == AlertType.NOTIFICATION
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.alert_sheet_end_time),
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    AlertTimeField(
                        text = endTimeText.toLocalizedDigits(),
                        onClick = { showTimePicker { endTimeMs = it } }
                    )
                }
            }

            AlertTypeSelector(
                selectedType = selectedType,
                onSelect = { selectedType = it }
            )

            Button(
                onClick = {
                    val start = startTimeMs ?: return@Button

                    onSave(
                        start,
                        if (selectedType == AlertType.NOTIFICATION) endTimeMs else null,
                        selectedType
                    )
                    onDismiss()
                },
                enabled = startTimeMs != null && when (selectedType) {
                    AlertType.NOTIFICATION -> endTimeMs != null
                            && (startTimeMs ?: 0) < (endTimeMs ?: 0)
                    AlertType.ALARM -> true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = extraColors.violet,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            ) {
                Text(
                    text = stringResource(R.string.alert_sheet_save),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}