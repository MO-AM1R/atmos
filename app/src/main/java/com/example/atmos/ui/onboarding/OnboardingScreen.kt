package com.example.atmos.ui.onboarding
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.atmos.R
import com.example.atmos.domain.onboarding.model.OnboardingItem
import com.example.atmos.ui.onboarding.components.GradientBackground
import com.example.atmos.ui.onboarding.components.OnboardingBottomSection
import com.example.atmos.ui.onboarding.components.OnboardingPager
import com.example.atmos.ui.onboarding.state.OnboardingEvent
import com.example.atmos.ui.onboarding.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun OnboardingScreen(
    onFinish: () -> Unit = {}
) {
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
    val scope = rememberCoroutineScope()
    val onboardingViewModel = hiltViewModel<OnboardingViewModel>()
    val onEvent = onboardingViewModel::onEvent

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val buttonLabel =
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
                label = buttonLabel,
                onNextClick = {
                    scope.launch {
                        if (pagerState.currentPage == 3){
                            onEvent(OnboardingEvent.OnSeeOnboarding)
                            onFinish()
                        }

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