package com.example.atmos.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.atmos.ui.home.HomeScreen
import com.example.atmos.ui.onboarding.OnboardingScreen
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
    ){
        composable<Screens.SplashScreen> {
            SplashScreen(
                onFinish = { onboardingSeenBefore ->
                    var target: Screens = Screens.OnboardingScreen
                    if (onboardingSeenBefore){
                        target = Screens.HomeScreen
                    }

                    navController.navigate(target){
                        popUpTo<Screens.SplashScreen> { inclusive = true }
                    }
                }
            )
        }

        composable<Screens.OnboardingScreen> {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screens.HomeScreen){
                        popUpTo<Screens.SplashScreen> { inclusive = true }
                    }
                }
            )
        }

        composable<Screens.HomeScreen> {
            HomeScreen(modifier = modifier)
        }
    }
}