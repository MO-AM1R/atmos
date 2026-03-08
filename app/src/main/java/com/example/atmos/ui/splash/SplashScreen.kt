package com.example.atmos.ui.splash
import android.app.Activity
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.R
import com.example.atmos.ui.core.components.LottieResourceAnimation
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.splash.viewmodel.SplashViewModel
import com.example.atmos.ui.theme.BackgroundDark2
import com.example.atmos.ui.theme.Padding
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import com.example.atmos.ui.theme.WeatherViolet
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onFinish: (onboardingSeenBefore: Boolean) -> Unit
) {
    val view = LocalView.current

    var iconRotation by remember { mutableFloatStateOf(-180f) }
    var iconVisible by remember { mutableStateOf(false) }
    var titleVisible by remember { mutableStateOf(false) }
    var subtitleVisible by remember { mutableStateOf(false) }
    var loadingVisible by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "gradientPulse")

    val gradientRadius by infiniteTransition.animateFloat(
        initialValue = 400f,
        targetValue = 500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradientRadius"
    )

    val iconAngle by animateFloatAsState(
        targetValue = iconRotation,
        animationSpec = tween(
            durationMillis = 1000,
            easing = EaseOutBack
        ),
        label = "iconRotation"
    )

    val iconAlpha by animateFloatAsState(
        targetValue = if (iconVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "iconAlpha"
    )

    val iconOffset by animateFloatAsState(
        targetValue = if (iconVisible) 0f else -60f,
        animationSpec = tween(
            durationMillis = 800,
            easing = EaseOutCubic
        ),
        label = "iconOffset"
    )

    val titleAlpha by animateFloatAsState(
        targetValue = if (titleVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 700),
        label = "titleAlpha"
    )

    val titleOffset by animateFloatAsState(
        targetValue = if (titleVisible) 0f else 40f,
        animationSpec = tween(
            durationMillis = 700,
            easing = EaseOutCubic
        ),
        label = "titleOffset"
    )

    val subtitleAlpha by animateFloatAsState(
        targetValue = if (subtitleVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 700),
        label = "subtitleAlpha"
    )

    val subtitleOffset by animateFloatAsState(
        targetValue = if (subtitleVisible) 0f else 40f,
        animationSpec = tween(
            durationMillis = 700,
            easing = EaseOutCubic
        ),
        label = "subtitleOffset"
    )

    val loadingAlpha by animateFloatAsState(
        targetValue = if (loadingVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "loadingAlpha"
    )

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

    val viewModel = hiltViewModel<SplashViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    @Suppress("AssignedValueIsNeverRead")
    LaunchedEffect(Unit) {
        iconVisible = true
        iconRotation = 0f
        delay(600)
        titleVisible = true
        delay(300)
        subtitleVisible = true
        delay(400)
        loadingVisible = true
        delay(200)
        delay(2000L)

        onFinish(state.value.onboardingSeenBefore)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF212E5B), BackgroundDark2),
                    radius = gradientRadius
                )
            )
            .padding(
                horizontal = Padding.screenPadding.first,
                vertical = Padding.screenPadding.second
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.7F),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.Large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ResourceIcon(
                    modifier = Modifier
                        .size(120.dp)
                        .graphicsLayer {
                            rotationZ = iconAngle
                            alpha = iconAlpha
                            translationY = iconOffset
                        },
                    resourceId = R.drawable.cloud_sun,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = titleAlpha
                            translationY = titleOffset
                        },
                    text = stringResource(R.string.app_name),
                    style = WeatherTypography.displayMedium,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    color = WeatherViolet,
                )

                Text(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = subtitleAlpha
                            translationY = subtitleOffset
                        },
                    text = stringResource(R.string.your_personal_weather_companion),
                    style = WeatherTypography.titleLarge,
                    letterSpacing = 1.sp,
                    color = Color(0xFF8A8A9A),
                )
            }

            Box(
                modifier = Modifier
                    .graphicsLayer { alpha = loadingAlpha }
            ) {
                LottieResourceAnimation(
                    Modifier.size(200.dp),
                    R.raw.loading_dots
                )
            }
        }
    }
}