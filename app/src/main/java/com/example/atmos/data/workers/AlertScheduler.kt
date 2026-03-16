package com.example.atmos.data.workers

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import com.example.atmos.data.receiver.AlertBroadcastReceiver
import com.example.atmos.domain.model.Alert
import com.example.atmos.utils.AppConstants.AlertConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AlertScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun schedule(alert: Alert) {
        val intent = Intent(context, AlertBroadcastReceiver::class.java).apply {
            putExtra(AlertConstants.EXTRA_ALERT_ID, alert.id)
            putExtra(AlertConstants.EXTRA_ALERT_TYPE, alert.type.name)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alert.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alert.endTimeMs?.let { endTime ->
            val endIntent = Intent(context, AlertBroadcastReceiver::class.java).apply {
                putExtra(AlertConstants.EXTRA_ALERT_ID, alert.id)
                putExtra(AlertConstants.EXTRA_ALERT_TYPE, alert.type.name)
                putExtra(AlertConstants.EXTRA_IS_END, true)
            }
            val endPendingIntent = PendingIntent.getBroadcast(
                context,
                alert.id + 10000,
                endIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                endTime,
                endPendingIntent
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alert.startTimeMs,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alert.startTimeMs,
                pendingIntent
            )
        }
    }

    fun cancel(alertId: Int) {
        val intent = Intent(context, AlertBroadcastReceiver::class.java)

        PendingIntent.getBroadcast(
            context,
            alertId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        ).let { alarmManager.cancel(it) }

        PendingIntent.getBroadcast(
            context,
            alertId + 10000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        ).let { alarmManager.cancel(it) }
    }
}