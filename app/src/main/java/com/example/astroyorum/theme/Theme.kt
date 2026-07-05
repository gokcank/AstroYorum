package com.example.astroyorum.theme

import androidx.compose.material3.MaterialTheme
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

@Composable
fun AstroYorumTheme(
    content: @Composable () -> Unit,
) {
    androidx.compose.material3.MaterialTheme(
        colorScheme = AstroLightColorScheme,
        typography = Typography,
        content = content
    )
}
