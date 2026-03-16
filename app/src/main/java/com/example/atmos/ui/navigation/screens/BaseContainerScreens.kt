package com.example.atmos.ui.navigation.screens
import kotlinx.serialization.Serializable


@Serializable
sealed class BaseContainerScreens {
    @Serializable
    object SettingsScreens : BaseContainerScreens()

    @Serializable
    data class FavoriteDetailsScreen(
        val latitude : Double,
        val longitude: Double
    ) : BaseContainerScreens()

    @Serializable
    object MapScreen: BaseContainerScreens()

    @Serializable
    object HomeScreens : BaseContainerScreens()

    @Serializable
    object FavoritesScreens : BaseContainerScreens()

    @Serializable
    object AlertsScreens : BaseContainerScreens()
}