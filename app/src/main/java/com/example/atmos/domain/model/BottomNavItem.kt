package com.example.atmos.domain.model

import com.example.atmos.R
import com.example.atmos.ui.navigation.screens.BaseContainerScreens

data class BottomNavItem(
    val screen   : BaseContainerScreens,
    val iconRes  : Int,
    val labelRes : Int
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen   = BaseContainerScreens.HomeScreens,
        iconRes  = R.drawable.ic_home,
        labelRes = R.string.nav_home
    ),
    BottomNavItem(
        screen   = BaseContainerScreens.FavoritesScreens,
        iconRes  = R.drawable.ic_favorite,
        labelRes = R.string.nav_favorites
    ),
    BottomNavItem(
        screen   = BaseContainerScreens.AlertsScreens,
        iconRes  = R.drawable.ic_alerts,
        labelRes = R.string.nav_alerts
    ),
    BottomNavItem(
        screen   = BaseContainerScreens.SettingsScreens,
        iconRes  = R.drawable.ic_settings,
        labelRes = R.string.nav_settings
    ),
)