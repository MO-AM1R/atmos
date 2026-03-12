package com.example.atmos.ui.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.extraColors


@Composable
fun HomeLoadingContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(Spacing.Large)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(32.dp))
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Small)) {
            repeat(4) {
                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(90.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(20.dp))
        )
    }
}

@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.extraColors

    val shimmerColors = listOf(
        colors.cardBackgroundStrong.copy(alpha = 0.3F),
        colors.cardBackgroundStrong.copy(alpha = 0.15F),
        colors.cardBackgroundStrong.copy(alpha = 0.3F),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")

    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = stringResource(R.string.shimmertranslate)
    )


    Box(
        modifier = modifier.background(
            brush = Brush.horizontalGradient(
                colors = shimmerColors,
                startX = translateAnim - 1000f,
                endX = translateAnim
            )
        )
    )
}