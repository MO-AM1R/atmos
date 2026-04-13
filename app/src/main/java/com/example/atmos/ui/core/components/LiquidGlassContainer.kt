package com.example.atmos.ui.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.atmos.utils.LiquidGlassConfig
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid


@Composable
fun LiquidGlassContainer(
    modifier: Modifier = Modifier,
    liquidState: LiquidState,
    config: LiquidGlassConfig = LiquidGlassConfig.HomeCard,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.liquid(liquidState) {
            frost = config.frost
            shape = config.shape
            refraction = config.refraction
            curve = config.curve
            edge = config.edge
            dispersion = config.dispersion
            tint = config.tintColor.copy(alpha = config.tintAlpha)
        }
    ) {
        Box(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}
