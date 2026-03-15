package com.example.atmos.domain.model

import com.example.atmos.R
import com.example.atmos.ui.navigation.screens.BottomNavScreens

data class BottomNavItem(
    val screen   : BottomNavScreens,
    val iconRes  : Int,
    val labelRes : Int
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen   = BottomNavScreens.HomeScreens,
        iconRes  = R.drawable.ic_home,
        labelRes = R.string.nav_home
    ),
    BottomNavItem(
        screen   = BottomNavScreens.FavoritesScreens,
        iconRes  = R.drawable.ic_favorite,
        labelRes = R.string.nav_favorites
    ),
    BottomNavItem(
        screen   = BottomNavScreens.AlertsScreens,
        iconRes  = R.drawable.ic_alerts,
        labelRes = R.string.nav_alerts
    ),
    BottomNavItem(
        screen   = BottomNavScreens.SettingsScreens,
        iconRes  = R.drawable.ic_settings,
        labelRes = R.string.nav_settings
    ),
)