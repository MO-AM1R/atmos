package com.example.atmos.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import com.example.atmos.R
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = White,
    secondary = BackgroundLight2,
    onSecondary = PrimaryLight,
    background = BackgroundLight1,
    onBackground = TextPrimaryLight,
    surface = CardLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = MutedLight,
    onSurfaceVariant = MutedForegroundLight,
    outline = BorderLight,
    outlineVariant = BorderLight,
    error = DestructiveLight,
    onError = White,
    tertiary = WeatherCyan,
    onTertiary = White,
    tertiaryContainer = WeatherViolet,
    onTertiaryContainer = White,
    inverseSurface = TextPrimaryLight,
    inverseOnSurface = BackgroundLight1,
    scrim = Color(0x52000000),
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = Color(0xFF262626),
    secondary = MutedDark,
    onSecondary = PrimaryDark,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = CardDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = MutedDark,
    onSurfaceVariant = MutedForegroundDark,
    outline = BorderDark,
    outlineVariant = BorderDark,
    error = DestructiveDark,
    onError = DestructiveFgDark,
    tertiary = WeatherCyan,
    onTertiary = BackgroundDark,
    tertiaryContainer = WeatherViolet,
    onTertiaryContainer = White,
    inverseSurface = TextPrimaryDark,
    inverseOnSurface = BackgroundDark,
    scrim = Color(0x52000000),
)

data class WeatherExtraColors(
    val cyan: Color,
    val violet: Color,
    val navy: Color,
    val cardBackground: Color,
    val cardBackgroundStrong: Color,
    val cardBorder: Color,
    val cardSurface: Color,
    val inputBackground: Color,
    val switchBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val textHint: Color,
    val sectionTitle: Color,
    val iconTint: Color,
    val tempBarBackground: Color,
    val tempBarFillStart: Color,
    val tempBarFillEnd: Color,
    val activeDot: Color,
    val alertCritical: Color,
    val alertWarning: Color,
    val alertInfo: Color,
    val alertSuccess: Color,
    val themeMode: ThemeMode,
)

val DayExtraColors = WeatherExtraColors(
    cyan = WeatherCyan,
    violet = WeatherViolet,
    navy = WeatherNavy,
    cardBackground = DayCardBackground,
    cardBackgroundStrong = DayCardBackground.copy(alpha = 0.9F),
    cardBorder = DayCardBorder,
    cardSurface = Color(0xCCFFFFFF),
    inputBackground = InputBackgroundLight,
    switchBackground = SwitchBackgroundLight,
    textPrimary = DayTextPrimary,
    textSecondary = DayTextSecondary,
    textMuted = DayTextMuted,
    textHint = DayTextHint,
    sectionTitle = DaySectionTitle,
    iconTint = DayIconTint,
    tempBarBackground = DayTempBarBackground,
    tempBarFillStart = DayTempBarFillStart,
    tempBarFillEnd = DayTempBarFillEnd,
    activeDot = DayActiveDot,
    alertCritical = AlertCritical,
    alertWarning = AlertWarning,
    alertInfo = AlertInfo,
    alertSuccess = AlertSuccess,
    themeMode = ThemeMode.DAY,
)

val NightExtraColors = WeatherExtraColors(
    cyan = WeatherCyan,
    violet = WeatherViolet,
    navy = WeatherNavy,
    cardBackground = NightCardBackground,
    cardBackgroundStrong = NightCardBackgroundStrong,
    cardBorder = NightCardBorder,
    cardSurface = Color(0x1AFFFFFF),
    inputBackground = InputDark,
    switchBackground = Color(0xFF3A3A4A),
    textPrimary = NightTextPrimary,
    textSecondary = NightTextSecondary,
    textMuted = NightTextMuted,
    textHint = NightTextHint,
    sectionTitle = NightSectionTitle,
    iconTint = NightIconTint,
    tempBarBackground = NightTempBarBackground,
    tempBarFillStart = NightTempBarFillStart,
    tempBarFillEnd = NightTempBarFillEnd,
    activeDot = NightActiveDot,
    alertCritical = AlertCritical,
    alertWarning = AlertWarning,
    alertInfo = AlertInfo,
    alertSuccess = AlertSuccess,
    themeMode = ThemeMode.NIGHT,
)

val LocalWeatherExtraColors = staticCompositionLocalOf { DayExtraColors }

@Composable
fun AtmosTheme(
    themeMode: ThemeMode = rememberThemeMode(),
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        ThemeMode.DAY -> LightColorScheme
        ThemeMode.NIGHT -> DarkColorScheme
    }

    val extraColors = when (themeMode) {
        ThemeMode.DAY -> DayExtraColors
        ThemeMode.NIGHT -> NightExtraColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            insetsController.hide(WindowInsetsCompat.Type.statusBars())
            insetsController.hide(WindowInsetsCompat.Type.navigationBars())
            insetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    CompositionLocalProvider(
        LocalWeatherExtraColors provides extraColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = WeatherTypography,
            shapes = WeatherShapes,
            content = content
        )
    }
}

val extraColors: WeatherExtraColors
    @Composable get() = LocalWeatherExtraColors.current

val background: Int
    @Composable get() =
    if (getCurrentThemeMode() == ThemeMode.DAY)
        R.drawable.background_day
    else
        R.drawable.background_night