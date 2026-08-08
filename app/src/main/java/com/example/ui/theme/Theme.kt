package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = GeoPrimaryDark,
    onPrimary = GeoOnPrimaryDark,
    secondary = GeoSecondaryDark,
    tertiary = GoldAccent,
    background = GeoBgDark,
    onBackground = GeoTextDark,
    surface = GeoSurfaceDark,
    onSurface = GeoTextDark,
    surfaceVariant = GeoSurfaceVariantDark,
    onSurfaceVariant = GeoTextDark.copy(alpha = 0.8f),
    outline = GeoBorderDark
)

private val LightColorScheme = lightColorScheme(
    primary = GeoPrimaryLight,
    onPrimary = GeoOnPrimaryLight,
    secondary = GeoSecondaryLight,
    tertiary = GoldAccent,
    background = GeoBgLight,
    onBackground = GeoTextLight,
    surface = GeoSurfaceLight,
    onSurface = GeoTextLight,
    surfaceVariant = GeoSurfaceVariantLight,
    onSurfaceVariant = GeoTextLight.copy(alpha = 0.8f),
    outline = GeoBorderLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
