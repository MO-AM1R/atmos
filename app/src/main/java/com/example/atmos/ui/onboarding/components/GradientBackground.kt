package com.example.atmos.ui.onboarding.components
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.atmos.ui.theme.BackgroundDark
import com.example.atmos.ui.theme.BackgroundDark2
import com.example.atmos.ui.theme.Padding


@Composable
@Preview()
fun GradientBackground(content: @Composable BoxScope.() -> Unit = {}) {

    val infinite = rememberInfiniteTransition()

    val offsetX by infinite.animateFloat(
        0f,
        1000f,
        infiniteRepeatable(
            tween(12000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    val offsetY by infinite.animateFloat(
        0f,
        800f,
        infiniteRepeatable(
            tween(16000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        BackgroundDark,
                        BackgroundDark,
                        BackgroundDark2,
                        BackgroundDark2
                    ),
                    start = Offset(offsetX, offsetY),
                    end = Offset(offsetX + 600f, offsetY + 1000f)
                )
            )
            .padding(
                horizontal = Padding.screenPadding.first,
                vertical = Padding.screenPadding.second
            ),
        contentAlignment = Alignment.BottomCenter,
        content = content
    )
}