package com.example.astroyorum.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Kozmik karanlık tema - her zaman dark (dinamik renk kapalı)
private val CosmicDarkColorScheme = darkColorScheme(
    primary = GoldenStardust,
    onPrimary = CosmicDeepPurple,
    primaryContainer = GoldenDim,
    onPrimaryContainer = GoldenStardust,

    secondary = NebulaPurple,
    onSecondary = StarWhite,
    secondaryContainer = CosmicCard,
    onSecondaryContainer = StellarLavender,

    tertiary = AquaGlow,
    onTertiary = CosmicDeepPurple,
    tertiaryContainer = CosmicCardLight,
    onTertiaryContainer = AquaGlow,

    background = CosmicDeepPurple,
    onBackground = MoonSilver,

    surface = CosmicMidnight,
    onSurface = MoonSilver,
    surfaceVariant = CosmicCard,
    onSurfaceVariant = CometGray,

    outline = CosmicCardLight,
    outlineVariant = CosmicCard,

    error = Color(0xFFFF6B6B),
    onError = CosmicDeepPurple,
)

@Composable
fun AstroYorumTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = CosmicDarkColorScheme,
        typography = Typography,
        content = content
    )
}
