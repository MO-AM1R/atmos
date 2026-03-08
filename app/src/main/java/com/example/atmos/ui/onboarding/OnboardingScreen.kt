package com.example.atmos.ui.onboarding
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.atmos.R
import com.example.atmos.domain.onboarding.model.OnboardingItem
import com.example.atmos.ui.onboarding.components.GradientBackground
import com.example.atmos.ui.onboarding.components.OnboardingBottomSection
import com.example.atmos.ui.onboarding.components.OnboardingPager
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

    val pagerState = rememberPagerState { onboardingPages.size }
    val buttonLabel: MutableState<String?> = rememberSaveable { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            buttonLabel.value =
                if (pagerState.currentPage < 3)
                    stringResource(R.string.next)
                else
                    stringResource(R.string.get_started)

            OnboardingPager(
                pagerState = pagerState,
                pages = onboardingPages
            )

            OnboardingBottomSection(
                pagerState = pagerState,
                label = buttonLabel.value ?: stringResource(R.string.next),
                onNextClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1,
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        )
                    }
                }
            )
        }
    }
}