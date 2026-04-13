package com.example.atmos.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class LiquidGlassConfig(
    val frost: Dp = 14.dp,
    val shape: Shape = RoundedCornerShape(20.dp),
    val refraction: Float = 0.30f,
    val curve: Float = 0.25f,
    val edge: Float = 0.02f,
    val dispersion: Float = 0.08f,
    val tintColor: Color = Color.Transparent,
    val tintAlpha: Float = 0.10f
) {
    companion object {
        val Card = LiquidGlassConfig(
            frost = 14.dp,
            shape = RoundedCornerShape(24.dp),
            refraction = 0.3f,
            curve = 0.3f,
            edge = 0.02f,
            dispersion = 0.08f,
            tintAlpha = 0.10f
        )

        val LightAreaCard = LiquidGlassConfig(
            frost = 14.dp,
            shape = RoundedCornerShape(24.dp),
            refraction = 0.2f,
            curve = 0.3f,
            edge = 0.01f,
            dispersion = 0.08f,
            tintAlpha = 0.10f
        )
    }
}