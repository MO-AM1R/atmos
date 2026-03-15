package com.example.atmos.ui.navigation.screens
import kotlinx.serialization.Serializable


@Serializable
sealed class BottomNavScreens {
    @Serializable
    object SettingsScreens : BottomNavScreens()

    @Serializable
    object HomeScreens : BottomNavScreens()

    @Serializable
    object FavoritesScreens : BottomNavScreens()

    @Serializable
    object AlertsScreens : BottomNavScreens()
}