package com.example.atmos.ui.components
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun LottieResourceAnimation(
    modifier: Modifier = Modifier,
    resourceId: Int
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resourceId))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    DisposableEffect(resourceId) {
        onDispose {
            LottieCompositionFactory.clearCache(null)
        }
    }

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize()
    )
}