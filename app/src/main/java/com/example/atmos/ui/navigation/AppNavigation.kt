package com.example.atmos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.atmos.ui.home.HomeScreen
import com.example.atmos.ui.map.MapScreen
import com.example.atmos.ui.onboarding.OnboardingScreen
import com.example.atmos.ui.settings.SettingsScreen
import com.example.atmos.ui.splash.SplashScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screens = Screens.SplashScreen
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screens.SplashScreen> {
            SplashScreen(
                onFinish = { onboardingSeenBefore ->
                    var target: Screens = Screens.OnboardingScreen
                    if (onboardingSeenBefore) {
                        target = Screens.HomeScreen
                    }

                    navController.navigate(target) {
                        popUpTo<Screens.SplashScreen> { inclusive = true }
                    }
                }
            )
        }

        composable<Screens.OnboardingScreen> {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screens.HomeScreen) {
                        popUpTo<Screens.SplashScreen> { inclusive = true }
                    }
                }
            )
        }

        composable<Screens.HomeScreen> {
            HomeScreen()
        }

        composable<Screens.MapScreen> {
            MapScreen(modifier = modifier, navController = navController)
        }

        composable<Screens.SettingsScreen> { backStackEntry ->
            SettingsScreen(
                savedStateHandle = backStackEntry.savedStateHandle,
                navigateToMap = {
                    navController.navigate(Screens.MapScreen)
                },
            )
        }
    }
}