package com.example.atmos.ui.onboarding

import android.app.Activity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.atmos.R
import com.example.atmos.domain.onboarding.OnboardingItem
import com.example.atmos.ui.onboarding.components.OnboardingBottomSection
import com.example.atmos.ui.onboarding.components.OnboardingPager
import com.example.atmos.ui.theme.BackgroundDark
import com.example.atmos.ui.theme.BackgroundDark2
import com.example.atmos.ui.theme.Padding
import kotlinx.coroutines.launch


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun OnboardingScreen() {
    val onboardingPages = listOf(
        OnboardingItem(
            image = R.drawable.cloudy_suny_weather_icon,
            title = stringResource(R.string.know_your_weather),
            subtitle = stringResource(R.string.real_time_forecasts_at_your_fingertips)
        ),
        OnboardingItem(
            image = R.drawable.location_icon,
            title = stringResource(R.string.track_any_location),
            subtitle = stringResource(R.string.search_pin_and_save_your_favorite_cities_worldwide)
        ),
        OnboardingItem(
            image = R.drawable.bell,
            title = stringResource(R.string.never_miss_an_alert),
            subtitle = stringResource(R.string.set_smart_alerts_for_rain_wind_snow_and_extreme_temps)
        ),
        OnboardingItem(
            image = R.drawable.weather,
            title = stringResource(R.string.you_re_all_set),
            subtitle = stringResource(R.string.choose_your_preferences_and_start_exploring)
        ),
    )

    LocalContext.current

    val pagerState = rememberPagerState { onboardingPages.size }
    val buttonLabel: MutableState<String?> = rememberSaveable { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    suspend fun navigateToNextPage() {
        if (pagerState.currentPage < pagerState.pageCount - 1) {
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                animationSpec = tween(
                    durationMillis = 600,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "gradient")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 12000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetX"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 16000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    val view = LocalView.current
    DisposableEffect(Unit) {
        val window = (view.context as Activity).window
        val insetsController = WindowCompat.getInsetsController(window, view)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        insetsController.hide(WindowInsetsCompat.Type.statusBars())
        insetsController.hide(WindowInsetsCompat.Type.navigationBars())
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        onDispose {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            insetsController.show(WindowInsetsCompat.Type.statusBars())
            insetsController.show(WindowInsetsCompat.Type.navigationBars())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        BackgroundDark,
                        BackgroundDark,
                        BackgroundDark2,
                        BackgroundDark2,
                    ),
                    start = Offset(offsetX, offsetY),
                    end = Offset(offsetX + 600f, offsetY + 1000f)
                )
            )
            .padding(
                horizontal = Padding.screenPadding.first,
                vertical = Padding.screenPadding.second
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        buttonLabel.value =
            if (pagerState.currentPage < 3)
                stringResource(R.string.next)
            else
                stringResource(R.string.get_started)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OnboardingPager(
                pagerState = pagerState,
                pages = onboardingPages
            )

            OnboardingBottomSection(
                pagerState = pagerState,
                label = buttonLabel.value ?: stringResource(R.string.next),
                onNextClick = {
                    scope.launch {
                        navigateToNextPage()
                    }
                }
            )
        }
    }
}