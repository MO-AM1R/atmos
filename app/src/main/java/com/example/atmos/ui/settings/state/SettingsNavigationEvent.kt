package com.example.atmos.ui.settings.state

sealed class SettingsNavigationEvent {
    object NavigateToMap: SettingsNavigationEvent()
    object RestartActivity: SettingsNavigationEvent()
}