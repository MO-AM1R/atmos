package com.example.atmos.ui.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.atmos.R


val InterFont = FontFamily(
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_extra_light, FontWeight.ExtraLight),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semi_bold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
)

val CairoFont = FontFamily(
    Font(R.font.cairo_regular, FontWeight.Normal),
    Font(R.font.cairo_medium, FontWeight.Medium),
    Font(R.font.cairo_light, FontWeight.Light),
    Font(R.font.cairo_extra_light, FontWeight.ExtraLight),
    Font(R.font.cairo_bold, FontWeight.Bold),
    Font(R.font.cairo_extra_bold, FontWeight.ExtraBold),
    Font(R.font.cairo_semi_bold, FontWeight.SemiBold),
)

fun getAppFontFamily(isArabic: Boolean): FontFamily {
    return if (isArabic) CairoFont else InterFont
}

val WeatherTypography = Typography(

    displayLarge = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight.Bold,
        fontSize    = 72.sp,
        lineHeight  = 80.sp,
        letterSpacing = (-2).sp
    ),

    displayMedium = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight.Bold,
        fontSize    = 32.sp,
        lineHeight  = 40.sp,
        letterSpacing = (-1).sp
    ),

    displaySmall = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 24.sp,
        lineHeight  = 32.sp
    ),

    headlineMedium = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 18.sp,
        lineHeight  = 26.sp
    ),

    titleLarge = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 16.sp,
        lineHeight  = 24.sp,
        letterSpacing = 0.sp
    ),

    titleMedium = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight(500),
        fontSize    = 14.sp,
        lineHeight  = 22.sp,
        letterSpacing = 0.1.sp
    ),

    bodyLarge = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight(400),
        fontSize    = 14.sp,
        lineHeight  = 22.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight(400),
        fontSize    = 13.sp,
        lineHeight  = 20.sp,
        letterSpacing = 0.25.sp
    ),

    labelLarge = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight(500),
        fontSize    = 12.sp,
        lineHeight  = 18.sp,
        letterSpacing = 0.5.sp
    ),

    labelSmall = TextStyle(
        fontFamily  = InterFont,
        fontWeight  = FontWeight.Light,
        fontSize    = 11.sp,
        lineHeight  = 16.sp,
        letterSpacing = 0.5.sp
    )
)