package com.example.atmos.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.Calendar

enum class ThemeMode {
    DAY,
    NIGHT
}


fun getCurrentThemeMode(): ThemeMode {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return if (hour in 6..19) ThemeMode.DAY else ThemeMode.NIGHT
}


@Composable
fun rememberThemeMode(): ThemeMode {
    var themeMode by remember { mutableStateOf(getCurrentThemeMode()) }

    LaunchedEffect(Unit) {
        while (true) {
            themeMode = getCurrentThemeMode()
            kotlinx.coroutines.delay(60_000L)
        }
    }

    return themeMode
}