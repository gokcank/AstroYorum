package com.gokcank.astroyorum.ui.zodiac

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gokcank.astroyorum.ads.BannerAdView
import com.gokcank.astroyorum.data.ZodiacDatabase
import com.gokcank.astroyorum.data.ZodiacSign
import com.gokcank.astroyorum.theme.*
import com.gokcank.astroyorum.ui.components.*
import androidx.compose.material3.MaterialTheme

@Composable
fun ZodiacScreen(
    initialSignId: Int = 0,
    horoscopeUiState: com.gokcank.astroyorum.ui.main.HoroscopeUiState,
    modifier: Modifier = Modifier
) {
    var selectedSign by remember { mutableStateOf(ZodiacDatabase.getById(initialSignId)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Başlık
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.background))
                )
                .padding(20.dp)
        ) {
            Text(
                text = "⭐ Burçlar",
                style = MaterialTheme.typography.headlineMedium,
                color = GoldenStardust,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // ─── 12 Burç Izgara ─────────────────────────────────────────────
            item {
                val activity = androidx.compose.ui.platform.LocalContext.current as? android.app.Activity
                var clickCount by remember { mutableIntStateOf(0) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val chunkedSigns = ZodiacDatabase.signs.chunked(4)
                    chunkedSigns.forEach { rowSigns ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowSigns.forEach { sign ->
                                ZodiacSignCard(
                                    sign = sign,
                                    isSelected = sign.id == selectedSign.id,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        if (sign.id != selectedSign.id) {
                                            clickCount++
                                            if (clickCount % 5 == 0 && activity != null) {
                                                // Her 5 tıklamada 1 geçiş reklamı göster
                                                com.gokcank.astroyorum.ads.AdManager.showInterstitialAd(activity) {
                                                    selectedSign = sign
                                                }
                                            } else {
                                                selectedSign = sign
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // ─── Seçili Burcun Detayı ─────────────────────────────────────
            item {
                AnimatedContent(
                    targetState = selectedSign,
                    transitionSpec = { fadeIn() + slideInVertically { 50 } togetherWith fadeOut() }
                ) { sign ->
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        // Burç başlık kartı
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            elementColor(sign.element).copy(0.3f),
                                            MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    )
                                )
                                .padding(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(text = sign.symbol, fontSize = 64.sp)
                                Column {
                                    Text(
                                        text = sign.name,
                                        style = MaterialTheme.typography.displaySmall,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${sign.symbol} ${sign.dates}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = sign.elementEmoji, fontSize = 14.sp)
                                        Text(
                                            text = "${sign.element} Elementi",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = elementColor(sign.element)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ─── Tab içeriği ─────────────────────────────────────────────────
            item {
                AnimatedContent(
                    targetState = selectedSign,
                    transitionSpec = { fadeIn() togetherWith fadeOut() }
                ) { sign ->
                    HoroscopeContent(sign = sign, horoscopeUiState = horoscopeUiState)
                }
            }

            // ─── Şans Bilgileri ─────────────────────────────────────────────
            item {
                val currentScores = if (horoscopeUiState is com.gokcank.astroyorum.ui.main.HoroscopeUiState.Success) {
                    horoscopeUiState.data.scores[selectedSign.englishName] ?: com.gokcank.astroyorum.data.ZodiacScores(selectedSign.loveScore, selectedSign.careerScore, selectedSign.healthScore)
                } else {
                    com.gokcank.astroyorum.data.ZodiacScores(selectedSign.loveScore, selectedSign.careerScore, selectedSign.healthScore)
                }

                val luckyNumber = if (currentScores.luckyNumber > 0) currentScores.luckyNumber else selectedSign.luckyNumber
                val luckyStone = currentScores.luckyStone.ifEmpty { selectedSign.luckyStone }
                val luckyColor = currentScores.luckyColor.ifEmpty { selectedSign.luckyColor }

                AstroCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    SectionHeader("🍀 Şans Bilgileri", "")
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        LuckyItem("🔢", "Sayı", luckyNumber.toString())
                        LuckyItem("💎", "Taş", luckyStone)
                        LuckyItem("🎨", "Renk", luckyColor)
                    }
                }
            }

            // ─── Skorlar ─────────────────────────────────────────────────────
            item {
                val currentScores = if (horoscopeUiState is com.gokcank.astroyorum.ui.main.HoroscopeUiState.Success) {
                    horoscopeUiState.data.scores[selectedSign.englishName] ?: com.gokcank.astroyorum.data.ZodiacScores(selectedSign.loveScore, selectedSign.careerScore, selectedSign.healthScore)
                } else {
                    com.gokcank.astroyorum.data.ZodiacScores(selectedSign.loveScore, selectedSign.careerScore, selectedSign.healthScore)
                }

                AstroCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    SectionHeader("📊 Enerji Seviyeleri", "Bugünkü etkiler")
                    Spacer(Modifier.height(16.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ScoreIndicator("❤️ Aşk & İlişki", currentScores.love, "❤️",
                            color = RoseQuartz)
                        ScoreIndicator("💼 Kariyer & İş", currentScores.career, "💼",
                            color = GoldenStardust)
                        ScoreIndicator("🌿 Sağlık & Enerji", currentScores.health, "🌿",
                            color = AquaGlow)
                    }
                }
            }

            // ─── Banner reklam ───────────────────────────────────────────────
            item {
                BannerAdView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun HoroscopeContent(
    sign: ZodiacSign, 
    horoscopeUiState: com.gokcank.astroyorum.ui.main.HoroscopeUiState
) {
    val text = when (horoscopeUiState) {
        is com.gokcank.astroyorum.ui.main.HoroscopeUiState.Loading -> "Yıldızların mesajı alınıyor..."
        is com.gokcank.astroyorum.ui.main.HoroscopeUiState.Success -> 
            horoscopeUiState.data.horoscopes[sign.englishName] ?: "Bugün için özel mesajınız hazırlanıyor."
        is com.gokcank.astroyorum.ui.main.HoroscopeUiState.Error -> horoscopeUiState.message
    }

    AstroCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun LuckyItem(emoji: String, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = emoji, fontSize = 24.sp)
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = GoldenStardust,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun elementColor(element: String) = when (element) {
    "Ateş" -> FireElement
    "Toprak" -> EarthElement
    "Hava" -> AirElement
    "Su" -> WaterElement
    else -> NebulaPurple
}

