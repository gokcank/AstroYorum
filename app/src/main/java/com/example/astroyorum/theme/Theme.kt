package com.example.astroyorum.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Holografik aydınlık tema
private val AstroLightColorScheme = lightColorScheme(
    primary = GoldenStardust,
    onPrimary = AstroBackground,
    primaryContainer = GoldenDim,
    onPrimaryContainer = GoldenStardust,

    secondary = NebulaPurple,
    onSecondary = AstroDark,
    secondaryContainer = AstroCard,
    onSecondaryContainer = AstroLavender,

    tertiary = AquaGlow,
    onTertiary = AstroBackground,
    tertiaryContainer = AstroCardLight,
    onTertiaryContainer = AquaGlow,

    background = AstroBackground,
    onBackground = AstroText,

    surface = AstroSurface,
    onSurface = AstroText,
    surfaceVariant = AstroCard,
    onSurfaceVariant = AstroTextSecondary,

    outline = AstroCardLight,
    outlineVariant = AstroCard,

    error = Color(0xFFFF6B6B),
    onError = AstroBackground,
)

private val AstroDarkColorScheme = darkColorScheme(
    primary = GoldenStardust,
    onPrimary = AstroDarkBackground,
    primaryContainer = GoldenDim,
    onPrimaryContainer = GoldenStardust,

    secondary = NebulaPurple,
    onSecondary = AstroDarkText,
    secondaryContainer = AstroDarkCard,
    onSecondaryContainer = AstroLavender,

    tertiary = AquaGlow,
    onTertiary = AstroDarkBackground,
    tertiaryContainer = AstroDarkCardLight,
    onTertiaryContainer = AquaGlow,

    background = AstroDarkBackground,
    onBackground = AstroDarkText,

    surface = AstroDarkSurface,
    onSurface = AstroDarkText,
    surfaceVariant = AstroDarkCard,
    onSurfaceVariant = AstroDarkTextSecondary,

    outline = AstroDarkCardLight,
    outlineVariant = AstroDarkCard,

    error = Color(0xFFFF6B6B),
    onError = AstroDarkBackground,
)

@Composable
fun AstroYorumTheme(
    themePref: Int = 0, // 0 = System, 1 = Light, 2 = Dark
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val useDarkTheme = when (themePref) {
        1 -> false
        2 -> true
        else -> darkTheme
    }

    val colorScheme = if (useDarkTheme) {
        AstroDarkColorScheme
    } else {
        AstroLightColorScheme
    }

    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
