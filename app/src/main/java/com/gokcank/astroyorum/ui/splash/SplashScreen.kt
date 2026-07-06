package com.gokcank.astroyorum.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gokcank.astroyorum.theme.GoldenStardust
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000) // 2 saniye bekle
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.background))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            Text(
                text = "⭐",
                fontSize = 80.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                color = GoldenStardust.copy(alpha = alphaAnim)
            )
            
            Text(
                text = "AstroYorum",
                style = MaterialTheme.typography.displayMedium,
                color = GoldenStardust.copy(alpha = alphaAnim),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "v1.0.2",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alphaAnim),
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Geliştirici: gokcank",
                style = MaterialTheme.typography.bodySmall,
                color = GoldenStardust.copy(alpha = alphaAnim * 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Disclaimer at the bottom
        Text(
            text = "Yasal Uyarı ve Sorumluluk Reddi: Bu uygulama yalnızca eğlence ve kişisel gelişim amaçlıdır. Astroloji ve tarot okumaları kesin yargılar içermez, tıbbi, hukuki veya finansal tavsiye yerine geçemez.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alphaAnim * 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        )
    }
}

