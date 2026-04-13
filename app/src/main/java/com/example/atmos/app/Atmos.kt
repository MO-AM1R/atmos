package com.example.atmos.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.compose.foundation.ComposeFoundationFlags
import androidx.compose.foundation.ExperimentalFoundationApi
import com.example.atmos.R
import com.example.atmos.utils.AppConstants
import com.example.atmos.utils.LocalizationHelper
import com.mapbox.common.MapboxOptions
import dagger.hilt.android.HiltAndroidApp
import kotlin.jvm.java

@HiltAndroidApp
class Atmos : Application(){
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalizationHelper.applyWithoutDI(base))
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate() {
        super.onCreate()
        ComposeFoundationFlags.isPausableCompositionInPrefetchEnabled = true
        MapboxOptions.accessToken = AppConstants.MAP_PUBLIC_API_KEY
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            val channel = NotificationChannel(
                getString(R.string.alert_notification_channel_id),
                getString(R.string.alert_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Weather alerts"
                enableLights(true)
                enableVibration(true)
                setSound(alarmUri, audioAttributes)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}