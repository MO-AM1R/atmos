package com.example.atmos.data.receiver
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.atmos.data.workers.AlertScheduler
import com.example.atmos.domain.repository.AlertRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alertRepository: AlertRepository

    @Inject
    lateinit var alertScheduler: AlertScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        scope.launch {
            try {
                val alerts = alertRepository.getActiveAlerts()
                alerts.forEach { alert ->
                    if (alert.startTimeMs > System.currentTimeMillis()) {
                        alertScheduler.schedule(alert)
                    }
                }
            } catch (e: Exception) {
                Log.e("BootReceiver", "Error rescheduling alerts", e)
            } finally {
                pendingResult.finish()
                scope.cancel()
            }
        }
    }
}