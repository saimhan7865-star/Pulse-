package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PulseNeonPink,
    onPrimary = Color.White,
    primaryContainer = PulseNeonPink.copy(alpha = 0.2f),
    onPrimaryContainer = Color.White,
    secondary = PulseCyan,
    onSecondary = Color.Black,
    secondaryContainer = PulseCyan.copy(alpha = 0.2f),
    onSecondaryContainer = PulseCyan,
    tertiary = PulseFacebookBlue,
    onTertiary = Color.White,
    background = PulseDarkBg,
    onBackground = PulseDarkTextPrimary,
    surface = PulseDarkSurface,
    onSurface = PulseDarkTextPrimary,
    surfaceVariant = PulseDarkSurfaceVariant,
    onSurfaceVariant = PulseDarkTextSecondary,
    outline = PulseDarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = PulseYouTubeRed,
    onPrimary = Color.White,
    primaryContainer = PulseYouTubeRed.copy(alpha = 0.15f),
    onPrimaryContainer = PulseYouTubeRed,
    secondary = PulseFacebookBlue,
    onSecondary = Color.White,
    secondaryContainer = PulseFacebookBlue.copy(alpha = 0.15f),
    onSecondaryContainer = PulseFacebookBlue,
    tertiary = PulseNeonPink,
    onTertiary = Color.White,
    background = PulseLightBg,
    onBackground = PulseLightTextPrimary,
    surface = PulseLightSurface,
    onSurface = PulseLightTextPrimary,
    surfaceVariant = PulseLightSurfaceVariant,
    onSurfaceVariant = PulseLightTextSecondary,
    outline = PulseLightBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use our handcrafted modern aesthetic by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme // Modern social apps look best in sleek dark mode
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
