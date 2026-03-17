package com.example.atmos.data.receiver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.atmos.R
import com.example.atmos.data.datasource.local.WeatherLocalDataSource
import com.example.atmos.data.enums.AlertType
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.repository.AlertRepository
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.domain.repository.WeatherRepository
import com.example.atmos.ui.alert.activity.AlarmActivity
import com.example.atmos.utils.AppConstants
import com.example.atmos.utils.Resource
import com.example.atmos.utils.ReverseGeocodingHelper
import com.example.atmos.utils.formatTemperature
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlertBroadcastReceiver : BroadcastReceiver() {

    @Inject lateinit var alertRepository: AlertRepository
    @Inject lateinit var weatherRepository: WeatherRepository
    @Inject lateinit var userPrefsRepo: UserPreferencesRepository
    @Inject lateinit var weatherLocalDataSource: WeatherLocalDataSource
    @Inject lateinit var reverseGeocoding: ReverseGeocodingHelper

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()

        val alertId = intent.getIntExtra(AppConstants.AlertConstants.EXTRA_ALERT_ID, -1)
        val alertType = intent.getStringExtra(AppConstants.AlertConstants.EXTRA_ALERT_TYPE)
        val isEnd = intent.getBooleanExtra(AppConstants.AlertConstants.EXTRA_IS_END, false)

        // ✅ Guard all early returns to always call pendingResult.finish()
        if (alertId == -1 || alertType == null) {
            pendingResult.finish()
            return
        }

        if (isEnd) {
            // ✅ Just mark expired and finish — no heavy work needed
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            scope.launch {
                try {
                    alertRepository.markAlertExpired(alertId)
                } finally {
                    pendingResult.finish()
                    scope.cancel()
                }
            }
            return
        }

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        scope.launch {
            try {
                val prefs = userPrefsRepo.getUserPreferences().firstOrNull()

                val cachedWeather = weatherLocalDataSource.getCachedWeather().firstOrNull()
                    ?: return@launch

                val point = StoredPoint(
                    latitude = cachedWeather.latitude,
                    longitude = cachedWeather.longitude
                )

                val lang = prefs?.languageOption?.apiValue ?: Language.ENGLISH.apiValue

                val weatherResult = weatherRepository.getWeatherForPoint(
                    lat = point.latitude,
                    lon = point.longitude,
                    lang = lang,
                ).first { it !is Resource.Loading }

                val weather = (weatherResult as? Resource.Success)?.data
                    ?: return@launch

                val locationName = reverseGeocoding.getLocationName(point)
                    ?: "${point.latitude}, ${point.longitude}"

                val temp = weather.temperature.formatTemperature(
                    prefs?.temperatureUnitOption ?: TemperatureUnit.CELSIUS
                )

                when (AlertType.valueOf(alertType)) {
                    AlertType.NOTIFICATION -> showNotification(context, locationName, temp, weather.weatherDescription)
                    AlertType.ALARM -> showAlarm(context, locationName, temp, weather.weatherDescription)
                }

                alertRepository.markAlertExpired(alertId)

            } catch (e: Exception) {
                Log.e("AlertReceiver", "Error", e)
            } finally {
                pendingResult.finish()
                scope.cancel()
            }
        }
    }

    private fun showNotification(
        context: Context,
        locationName: String,
        temp: String,
        weatherDescription: String
    ) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.getString(R.string.alert_notification_channel_id),
                context.getString(R.string.alert_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.alert_notification_channel_id)
        )
            .setSmallIcon(R.drawable.ic_alerts)
            .setContentTitle(context.getString(R.string.alert_notification_title))
            .setContentText(
                context.getString(
                    R.string.alert_notification_content,
                    temp,
                    locationName,
                    weatherDescription
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            AppConstants.AlertConstants.NOTIFICATION_ID,
            notification
        )
    }

    @SuppressLint("FullScreenIntentPolicy")
    private fun showAlarm(
        context: Context,
        locationName: String,
        temp: String,
        weatherDescriptor: String
    ) {
        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("location_name", locationName)
            putExtra("temp", temp)
            putExtra("weatherDescription", weatherDescriptor)
        }

        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.alert_notification_channel_id)
        )
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(context.getString(R.string.alert_alarm_title))
            .setContentText("$temp in $locationName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(
            AppConstants.AlertConstants.ALARM_NOTIFICATION_ID,
            notification
        )
    }
}