package com.example.atmos.ui.basescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.atmos.ui.favorites.FavoritesScreen
import com.example.atmos.ui.home.HomeScreen
import com.example.atmos.ui.map.MapScreen
import com.example.atmos.ui.navigation.components.BottomNavBar
import com.example.atmos.ui.navigation.screens.BaseContainerScreens
import com.example.atmos.ui.settings.SettingsScreen

@SuppressLint("RestrictedApi")
@Composable
fun BaseScreen() {
    val navController = rememberNavController()

    val currentDestination by navController
        .currentBackStackEntryAsState()

    val showBottomBar = currentDestination?.destination
        ?.hasRoute(BaseContainerScreens.MapScreen::class) == false


    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier.fillMaxWidth(),
            navController = navController,
            startDestination = BaseContainerScreens.HomeScreens
        ) {
            composable<BaseContainerScreens.HomeScreens> {
                HomeScreen()
            }

            composable<BaseContainerScreens.FavoritesScreens> { backStackEntry ->
                FavoritesScreen(
                    savedStateHandle = backStackEntry.savedStateHandle,
                    onNavigateToMap = {
                        navController.navigate(BaseContainerScreens.MapScreen)
                    }
                )
            }

            composable<BaseContainerScreens.AlertsScreens> {
                //TODO: Alerts Screen
            }

            composable<BaseContainerScreens.SettingsScreens> { backStackEntry ->
                SettingsScreen(
                    savedStateHandle = backStackEntry.savedStateHandle,
                    navigateToMap = {
                        navController.navigate(BaseContainerScreens.MapScreen)
                    }
                )
            }

            composable<BaseContainerScreens.MapScreen> {
                MapScreen(navController = navController)
            }
        }

        if (showBottomBar) {
            BottomNavBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                currentDestination = currentDestination?.destination,
                onSelectScreen = { screen ->
                    navController.navigate(screen) {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}