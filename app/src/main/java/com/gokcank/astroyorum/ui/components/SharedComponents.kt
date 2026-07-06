package com.gokcank.astroyorum.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gokcank.astroyorum.data.ZodiacSign
import com.gokcank.astroyorum.theme.*
import androidx.compose.material3.MaterialTheme

// ─── Gradient Kart ────────────────────────────────────────────────────────────
@Composable
fun AstroCard(
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.outline),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(GoldenStardust.copy(alpha = 0.4f), NebulaPurple.copy(alpha = 0.2f))
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    }
}

// ─── Burç Kartı (Liste için küçük) ────────────────────────────────────────────
@Composable
fun ZodiacSignCard(
    sign: ZodiacSign,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val borderColor = if (isSelected) GoldenStardust else MaterialTheme.colorScheme.outline
    val bgColors = if (isSelected)
        listOf(GoldenDim.copy(alpha = 0.3f), NebulaPurple.copy(alpha = 0.2f))
    else
        listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(bgColors),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                if (isSelected) GoldenStardust else MaterialTheme.colorScheme.outline,
                RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = sign.symbol,
                fontSize = 32.sp
            )
            Text(
                text = sign.name,
                style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) GoldenStardust else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Text(
                text = sign.elementEmoji,
                fontSize = 12.sp
            )
        }
    }
}

// ─── Skor Göstergesi ─────────────────────────────────────────────────────────
@Composable
fun ScoreIndicator(
    label: String,
    score: Int,
    emoji: String,
    color: Color = GoldenStardust,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = emoji, fontSize = 20.sp)
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        LinearProgressIndicator(
            progress = { score / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = "%$score",
            style = MaterialTheme.typography.labelMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

// ─── Bölüm Başlığı ───────────────────────────────────────────────────────────
@Composable
fun SectionHeader(
    title: String,
    subtitle: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = GoldenStardust,
            fontWeight = FontWeight.Bold
        )
        if (subtitle.isNotEmpty()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ─── Parlayan Yıldız Emojili Dekoratif Ayrıcı ─────────────────────────────────
@Composable
fun StarDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = "  ✦  ",
            color = GoldenStardust,
            fontSize = 12.sp
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outline
        )
    }
}

// ─── Alt Navigasyon Çubuğu ───────────────────────────────────────────────────
data class BottomNavItem(
    val label: String,
    val emoji: String,
    val route: Any
)

