package com.example.atmos.ui.onboarding.components

import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.domain.onboarding.OnboardingItem
import com.example.atmos.ui.components.ResourceImage
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography
import kotlinx.coroutines.delay


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun OnboardingPage(
    modifier: Modifier = Modifier,
    item: OnboardingItem = OnboardingItem(R.drawable.weather, "Title", "Sub Title"),
    currentPage: Int = 0,
    pageIndex  : Int = 0,
) {
    var iconVisible by remember { mutableStateOf(false) }
    var titleVisible by remember { mutableStateOf(false) }
    var subtitleVisible by remember { mutableStateOf(false) }
    var iconRotation by remember { mutableFloatStateOf(-180f) }

    LaunchedEffect(currentPage) {
        if (currentPage != pageIndex) return@LaunchedEffect

        iconVisible = false
        titleVisible = false
        subtitleVisible = false
        iconRotation = -180f

        delay(50)

        iconVisible = true
        iconRotation = 0f

        delay(600)
        titleVisible = true

        delay(300)
        subtitleVisible = true
    }

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

    val iconGraphicsLayer = rememberGraphicsLayer()
    val titleGraphicsLayer = rememberGraphicsLayer()
    val subGraphicsLayer = rememberGraphicsLayer()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Large)
    ) {

        ResourceImage(
            resourceId = item.image,
            modifier = Modifier
                .drawWithContent {
                    iconGraphicsLayer.apply {
                        record { this@drawWithContent.drawContent() }
                        alpha = iconAlpha
                        rotationZ = iconAngle
                        translationY = iconOffset
                    }
                    drawLayer(iconGraphicsLayer)
                }
        )

        Spacer(Modifier.height(18.dp))

        Text(
            text = item.title,
            textAlign = TextAlign.Center,
            style = WeatherTypography.displayMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier
                .drawWithContent {
                    titleGraphicsLayer.apply {
                        record { this@drawWithContent.drawContent() }
                        alpha = titleAlpha
                        translationY = titleOffset
                    }
                    drawLayer(titleGraphicsLayer)
                }
        )

        Text(
            text = item.subtitle,
            textAlign = TextAlign.Center,
            style = WeatherTypography.titleLarge,
            color = Color(0xFF8A8A9A),
            modifier = Modifier
                .drawWithContent {
                    subGraphicsLayer.apply {
                        record { this@drawWithContent.drawContent() }
                        alpha = subtitleAlpha
                        translationY = subtitleOffset
                    }
                    drawLayer(subGraphicsLayer)
                }
        )
    }
}