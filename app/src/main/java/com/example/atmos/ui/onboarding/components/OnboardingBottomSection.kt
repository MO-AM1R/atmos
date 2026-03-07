package com.example.atmos.ui.onboarding.components
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.atmos.ui.components.MyPagerIndicator
import com.example.atmos.ui.theme.AppShapes
import com.example.atmos.ui.theme.WeatherTypography
import kotlinx.coroutines.delay


@Composable
fun OnboardingBottomSection(
    pagerState: PagerState,
    onNextClick: () -> Unit
) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        visible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(700),
        label = ""
    )

    val offset by animateFloatAsState(
        targetValue = if (visible) 0f else 80f,
        animationSpec = tween(700, easing = EaseOutCubic),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.graphicsLayer {
            this.alpha = alpha
            translationY = offset
        }
    ) {

        MyPagerIndicator(
            pagerState = pagerState,
            pageCount = pagerState.pageCount,
            dotColor = Color(0xFF4D4F70),
            activeDotColor = Color(0xFF0AD3FF),
            activeDotSize = 48.dp
        )

        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF0AD3FF), Color(0xFF8971FF))
                    ),
                    shape = AppShapes.Button
                ),
            contentPadding = PaddingValues(vertical = 15.dp)
        ) {
            Text(
                text = "Next",
                style = WeatherTypography.headlineMedium,
                color = Color.White
            )
        }
    }
}