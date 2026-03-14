package com.example.atmos.ui.navigation
import kotlinx.serialization.Serializable


@Serializable
sealed class Screens {
    @Serializable
    object SplashScreen: Screens()

    @Serializable
    object HomeScreen: Screens()

    @Serializable
    object OnboardingScreen: Screens()

    @Serializable
    object SettingsScreen: Screens()
}