package com.example.atmos.ui.basescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.atmos.ui.home.HomeScreen
import com.example.atmos.ui.navigation.components.BottomNavBar
import com.example.atmos.ui.navigation.screens.BottomNavScreens
import com.example.atmos.ui.settings.SettingsScreen

@Composable
fun BaseScreen(
    navigateToMap: () -> Unit = {},
) {
    val navController = rememberNavController()

    val currentDestination by navController
        .currentBackStackEntryAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier.fillMaxWidth(),
            navController = navController,
            startDestination = BottomNavScreens.HomeScreens
        ) {
            composable<BottomNavScreens.HomeScreens> {
                HomeScreen()
            }

            composable<BottomNavScreens.FavoritesScreens> {
                //TODO: Favourites Screen
            }

            composable<BottomNavScreens.AlertsScreens> {
                //TODO: Alerts Screen
            }

            composable<BottomNavScreens.SettingsScreens> { backStackEntry ->
                SettingsScreen(
                    savedStateHandle = backStackEntry.savedStateHandle,
                    navigateToMap = navigateToMap
                )
            }
        }

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