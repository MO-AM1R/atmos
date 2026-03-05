package com.example.atmos.ui.theme
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary              = PrimaryLight,
    onPrimary            = White,
    secondary            = BackgroundLight2,
    onSecondary          = PrimaryLight,
    background           = BackgroundLight1,
    onBackground         = TextPrimaryLight,
    surface              = CardLight,
    onSurface            = TextPrimaryLight,
    surfaceVariant       = MutedLight,
    onSurfaceVariant     = MutedForegroundLight,
    outline              = BorderLight,
    outlineVariant       = BorderLight,
    error                = DestructiveLight,
    onError              = White,
    tertiary             = WeatherCyan,
    onTertiary           = White,
    tertiaryContainer    = WeatherViolet,
    onTertiaryContainer  = White,
    inverseSurface       = TextPrimaryLight,
    inverseOnSurface     = BackgroundLight1,
    scrim                = Color(0x52000000),
)

private val DarkColorScheme = darkColorScheme(
    primary              = PrimaryDark,
    onPrimary            = Color(0xFF262626),
    secondary            = MutedDark,
    onSecondary          = PrimaryDark,
    background           = BackgroundDark,
    onBackground         = TextPrimaryDark,
    surface              = CardDark,
    onSurface            = TextPrimaryDark,
    surfaceVariant       = MutedDark,
    onSurfaceVariant     = MutedForegroundDark,
    outline              = BorderDark,
    outlineVariant       = BorderDark,
    error                = DestructiveDark,
    onError              = DestructiveFgDark,
    tertiary             = WeatherCyan,
    onTertiary           = BackgroundDark,
    tertiaryContainer    = WeatherViolet,
    onTertiaryContainer  = White,
    inverseSurface       = TextPrimaryDark,
    inverseOnSurface     = BackgroundDark,
    scrim                = Color(0x52000000),
)

data class WeatherExtraColors(
    val cyan              : Color,
    val violet            : Color,
    val navy              : Color,
    val cardSurface       : Color,
    val inputBackground   : Color,
    val switchBackground  : Color,
    val textSecondary     : Color,
    val alertCritical     : Color,
    val alertWarning      : Color,
    val alertInfo         : Color,
    val alertSuccess      : Color,
)

val LightExtraColors = WeatherExtraColors(
    cyan             = WeatherCyan,
    violet           = WeatherViolet,
    navy             = WeatherNavy,
    cardSurface      = Color(0xCCFFFFFF),
    inputBackground  = InputBackgroundLight,
    switchBackground = SwitchBackgroundLight,
    textSecondary    = TextSecondaryLight,
    alertCritical    = AlertCritical,
    alertWarning     = AlertWarning,
    alertInfo        = AlertInfo,
    alertSuccess     = AlertSuccess,
)

val DarkExtraColors = WeatherExtraColors(
    cyan             = WeatherCyan,
    violet           = WeatherViolet,
    navy             = WeatherNavy,
    cardSurface      = Color(0x1AFFFFFF),
    inputBackground  = InputDark,
    switchBackground = Color(0xFF3A3A4A),
    textSecondary    = TextSecondaryDark,
    alertCritical    = AlertCritical,
    alertWarning     = AlertWarning,
    alertInfo        = AlertInfo,
    alertSuccess     = AlertSuccess,
)

val LocalWeatherExtraColors = staticCompositionLocalOf { LightExtraColors }

@Composable
fun AtmosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content:   @Composable () -> Unit
) {
    val colorScheme  = if (darkTheme) DarkColorScheme  else LightColorScheme
    val extraColors  = if (darkTheme) DarkExtraColors  else LightExtraColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalWeatherExtraColors provides extraColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = WeatherTypography,
            shapes      = WeatherShapes,
            content     = content
        )
    }
}


val MaterialTheme.extraColors: WeatherExtraColors
    @Composable get() = LocalWeatherExtraColors.current