package com.example.atmos
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.atmos.ui.navigation.AppNavigation
import com.example.atmos.ui.navigation.screens.Screens
import com.example.atmos.ui.theme.AtmosTheme
import com.example.atmos.utils.LocalizationHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var splashSeen = mutableStateOf(false)

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalizationHelper.applyWithoutDI(base))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashSeen.value = LocalizationHelper.hasSplashBeenSeen(this)

        setContent {
            AtmosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        startScreen = if (splashSeen.value) Screens.BaseScreen else Screens.SplashScreen
                    )
                }
            }
        }
    }
}