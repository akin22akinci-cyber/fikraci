package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AmberSecondary,
    onPrimary = WarmBackgroundDark,
    primaryContainer = AmberPrimary,
    onPrimaryContainer = TextPrimaryDark,
    secondary = CoralAccent,
    onSecondary = WarmBackgroundDark,
    tertiary = LaughYellow,
    onTertiary = WarmBackgroundDark,
    background = WarmBackgroundDark,
    onBackground = TextPrimaryDark,
    surface = WarmSurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = WarmSurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = AmberPrimary,
    onPrimary = WarmSurfaceLight,
    primaryContainer = WarmSurfaceVariantLight,
    onPrimaryContainer = AmberPrimary,
    secondary = CoralAccent,
    onSecondary = WarmSurfaceLight,
    tertiary = AmberTertiary,
    onTertiary = TextPrimaryLight,
    background = WarmBackgroundLight,
    onBackground = TextPrimaryLight,
    surface = WarmSurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = WarmSurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight
)

@Composable
fun FikraciTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep warm comedic branding consistent
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = FikraciTypography,
        content = content
    )
}
