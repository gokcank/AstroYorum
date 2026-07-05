package com.example.astroyorum.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astroyorum.ads.BannerAdView
import com.example.astroyorum.data.*
import com.example.astroyorum.theme.*
import com.example.astroyorum.ui.components.*
import androidx.compose.material3.MaterialTheme

@Composable
fun HomeScreen(
    userProfile: UserProfile,
    horoscopeUiState: com.example.astroyorum.ui.main.HoroscopeUiState,
    onNavigateToZodiac: () -> Unit,
    onNavigateToTarot: () -> Unit,
    onNavigateToMoon: () -> Unit,
    onNavigateToBirthChart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sign = ZodiacDatabase.getById(userProfile.zodiacSignId)
    val dailyCard = remember { TarotDatabase.getDailyCard() }
    val today = java.util.Calendar.getInstance()
    val dayNames = listOf("Pazar", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi")
    val monthNames = listOf("Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran",
        "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ─── Banner Reklam (üst) ─────────────────────────────────────────
            item {
                BannerAdView(modifier = Modifier.fillMaxWidth())
            }

            // ─── Üst Başlık ─────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.background)
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Column {
                        Text(
                            text = "${dayNames[today.get(java.util.Calendar.DAY_OF_WEEK) - 1]}, " +
                                "${today.get(java.util.Calendar.DAY_OF_MONTH)} " +
                                "${monthNames[today.get(java.util.Calendar.MONTH)]}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Merhaba, ${userProfile.name.ifEmpty { "Yolcu" }} ✨",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // ─── Günlük Burç Yorumu ─────────────────────────────────────────
            item {
                AstroCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    gradientColors = listOf(
                        GoldenDim.copy(alpha = 0.3f),
                        NebulaPurple.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = sign.emoji, fontSize = 40.sp)
                        Column {
                            Text(
                                text = "Günlük Yorumun",
                                style = MaterialTheme.typography.labelMedium,
                                color = GoldenStardust
                            )
                            Text(
                                text = sign.name,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = sign.dates,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = sign.elementEmoji, fontSize = 20.sp)
                            Text(
                                text = sign.element,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    StarDivider()
                    Spacer(Modifier.height(16.dp))

                    val dailyText = when (horoscopeUiState) {
                        is com.example.astroyorum.ui.main.HoroscopeUiState.Loading -> "Yıldızların mesajı alınıyor..."
                        is com.example.astroyorum.ui.main.HoroscopeUiState.Success -> 
                            horoscopeUiState.data.horoscopes[sign.englishName] ?: "Bugün için özel mesajınız hazırlanıyor."
                        is com.example.astroyorum.ui.main.HoroscopeUiState.Error -> horoscopeUiState.message
                    }

                    Text(
                        text = dailyText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp,
                        maxLines = 4,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(16.dp))

                    val currentScores = if (horoscopeUiState is com.example.astroyorum.ui.main.HoroscopeUiState.Success) {
                        horoscopeUiState.data.scores[sign.englishName] ?: ZodiacScores(sign.loveScore, sign.careerScore, sign.healthScore)
                    } else {
                        ZodiacScores(sign.loveScore, sign.careerScore, sign.healthScore)
                    }

                    // Bugünün skorları
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ScoreIndicator("Aşk", currentScores.love, "❤️",
                            color = RoseQuartz, modifier = Modifier.weight(1f))
                        ScoreIndicator("Kariyer", currentScores.career, "💼",
                            color = GoldenStardust, modifier = Modifier.weight(1f))
                        ScoreIndicator("Sağlık", currentScores.health, "🌿",
                            color = AquaGlow, modifier = Modifier.weight(1f))
                    }

                    Spacer(Modifier.height(12.dp))

                    // Şanslı bilgiler
                    val luckyNumber = if (currentScores.luckyNumber > 0) currentScores.luckyNumber else sign.luckyNumber
                    val luckyStone = currentScores.luckyStone.ifEmpty { sign.luckyStone }
                    val luckyColor = currentScores.luckyColor.ifEmpty { sign.luckyColor }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        LuckyChip("🔢 $luckyNumber", "Sayı")
                        LuckyChip("💎 $luckyStone", "Taş")
                        LuckyChip("🎨 $luckyColor", "Renk")
                    }

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = onNavigateToZodiac,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldenStardust.copy(0.15f),
                            contentColor = GoldenStardust
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Burcunun Detaylarını Gör →")
                    }
                }
            }

            // ─── Hızlı Erişim Butonları ─────────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeader("Keşfet", "Astrolojinin derinliklerine dal")
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickAccessCard(
                            emoji = "🔯", title = "Doğum\nHaritası",
                            gradient = listOf(NebulaPurple.copy(0.4f), AstralViolet.copy(0.2f)),
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToBirthChart
                        )
                        QuickAccessCard(
                            emoji = "💕", title = "Uyum\nAnalizi",
                            gradient = listOf(RoseQuartz.copy(0.3f), NebulaPurple.copy(0.2f)),
                            modifier = Modifier.weight(1f),
                            onClick = {}
                        )
                        QuickAccessCard(
                            emoji = "🌙", title = "Ay\nTakvimi",
                            gradient = listOf(AquaGlow.copy(0.3f), MaterialTheme.colorScheme.surfaceVariant),
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToMoon
                        )
                    }
                }
            }

            // ─── Günün Ay Fazı ────────────────────────────────────────────────
            item {
                val moonPhase = if (horoscopeUiState is com.example.astroyorum.ui.main.HoroscopeUiState.Success) {
                    horoscopeUiState.data.moonPhase ?: todayMoonPhase()
                } else {
                    todayMoonPhase()
                }

                AstroCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    gradientColors = listOf(
                        Color(0xFF0D1B2A), AquaGlow.copy(0.1f)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = moonPhase.phaseEmoji, fontSize = 48.sp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Bugün: ${moonPhase.phaseName}",
                                style = MaterialTheme.typography.titleMedium,
                                color = AquaGlow,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = moonPhase.zodiacSign + " burcunda",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = moonPhase.energy,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = moonPhase.ritual,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }

            // ─── Günün Tarot Kartı ────────────────────────────────────────────
            item {
                AstroCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    gradientColors = listOf(
                        Color(0xFF1A0A2E), NebulaPurple.copy(0.3f)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Kart görseli (dekoratif çerçeveli)
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    Brush.verticalGradient(
                                        listOf(NebulaPurple.copy(0.5f), MaterialTheme.colorScheme.surfaceVariant)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = dailyCard.emoji, fontSize = 32.sp)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Günün Kartı",
                                style = MaterialTheme.typography.labelMedium,
                                color = AstroLavender
                            )
                            Text(
                                text = dailyCard.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = dailyCard.arcana + " Arkana",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = dailyCard.meaningUpright,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onNavigateToTarot,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AstroLavender
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, NebulaPurple),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Tarot Falına Bak →")
                    }
                }
            }
        }
    }
}

@Composable
private fun LuckyChip(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background.copy(0.5f))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.labelSmall,
                color = GoldenStardust
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun QuickAccessCard(
    emoji: String,
    title: String,
    gradient: List<Color>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.verticalGradient(gradient))
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = emoji, fontSize = 28.sp)
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

