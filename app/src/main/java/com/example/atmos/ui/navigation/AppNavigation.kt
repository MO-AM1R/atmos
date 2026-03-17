package com.example.atmos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atmos.ui.basescreen.BaseScreen
import com.example.atmos.ui.navigation.screens.Screens
import com.example.atmos.ui.onboarding.OnboardingScreen
import com.example.atmos.ui.splash.SplashScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startScreen: Screens = Screens.SplashScreen
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startScreen
    ) {
        composable<Screens.SplashScreen> {
            SplashScreen(
                onFinish = { onboardingSeenBefore ->
                    var target: Screens = Screens.OnboardingScreen
                    if (onboardingSeenBefore) {
                        target = Screens.BaseScreen
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
                    navController.navigate(Screens.BaseScreen) {
                        popUpTo<Screens.SplashScreen> { inclusive = true }
                    }
                }
            )
        }

        composable<Screens.BaseScreen> {
            BaseScreen()
        }
    }
}