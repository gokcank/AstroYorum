package com.example.astroyorum.ui.tarot

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astroyorum.ads.BannerAdView
import com.example.astroyorum.data.TarotCard
import com.example.astroyorum.data.TarotDatabase
import com.example.astroyorum.theme.*
import com.example.astroyorum.ui.components.*

@Composable
fun TarotScreen(modifier: Modifier = Modifier) {
    var selectedMode by remember { mutableIntStateOf(0) } // 0=Günlük, 1=3 Kart
    var isRevealed by remember { mutableStateOf(false) }
    var isFlipping by remember { mutableStateOf(false) }

    val dailyCard = remember { TarotDatabase.getDailyCard() }
    val threeCards = remember { TarotDatabase.getThreeCardSpread() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CosmicDeepPurple)
    ) {
        // Başlık
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF1A0A2E), CosmicDeepPurple))
                )
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "🃏 Tarot",
                    style = MaterialTheme.typography.headlineMedium,
                    color = StellarLavender,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Evrenin mesajlarını al",
                    style = MaterialTheme.typography.bodySmall,
                    color = CometGray
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ─── Mod seçici ────────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ModeChip("🌟 Günün Kartı", selectedMode == 0, Modifier.weight(1f)) {
                        selectedMode = 0
                        isRevealed = false
                    }
                    ModeChip("✨ 3 Kart Spreadi", selectedMode == 1, Modifier.weight(1f)) {
                        selectedMode = 1
                        isRevealed = false
                    }
                }
            }

            // ─── Kart çekimi alanı ───────────────────────────────────────
            when (selectedMode) {
                0 -> {
                    item {
                        SingleCardSection(
                            card = dailyCard,
                            isRevealed = isRevealed,
                            onReveal = { isRevealed = true }
                        )
                    }
                }
                1 -> {
                    item {
                        ThreeCardSection(
                            cards = threeCards,
                            isRevealed = isRevealed,
                            onReveal = { isRevealed = true }
                        )
                    }
                }
            }

            // ─── Reklam ─────────────────────────────────────────────────────
            item {
                BannerAdView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            // ─── Major Arcana hakkında bilgi ────────────────────────────────
            item {
                CosmicCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    gradientColors = listOf(NebulaPurple.copy(0.2f), CosmicCard)
                ) {
                    Text(
                        text = "🔮 Tarot Hakkında",
                        style = MaterialTheme.typography.titleMedium,
                        color = StellarLavender,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Tarot destesi 78 karttan oluşur: 22 Büyük Arkana (Major Arcana) ve 56 Küçük Arkana (Minor Arcana). Her kart evrensel arketipleri ve yaşam deneyimlerini temsil eder.\n\nKartlar, içsel bilgeliğinizle bağlantı kurmanıza yardımcı olan aynalardır. Eğlence amaçlı yorumlanmalıdır.",
                        style = MaterialTheme.typography.bodySmall,
                        color = CometGray
                    )
                }
            }
        }
    }
}

@Composable
private fun ModeChip(
    text: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) NebulaPurple.copy(0.4f) else CosmicCard
            )
            .border(
                1.dp,
                if (isSelected) NebulaPurple else CosmicCardLight,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) StellarLavender else CometGray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SingleCardSection(
    card: TarotCard,
    isRevealed: Boolean,
    onReveal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Günün Mesajı",
            style = MaterialTheme.typography.headlineSmall,
            color = StarWhite,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (!isRevealed) "Kartına odaklan ve dokun" else card.name,
            style = MaterialTheme.typography.bodyMedium,
            color = CometGray,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))

        // Kart animasyonlu flip
        AnimatedTarotCard(
            card = card,
            isRevealed = isRevealed,
            onClick = { if (!isRevealed) onReveal() }
        )

        if (isRevealed) {
            Spacer(Modifier.height(20.dp))
            CardDetails(card = card)
        }
    }
}

@Composable
private fun AnimatedTarotCard(
    card: TarotCard,
    isRevealed: Boolean,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isRevealed) 0f else 180f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "cardFlip"
    )
    val scale by animateFloatAsState(
        targetValue = if (isRevealed) 1f else 0.95f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "cardScale"
    )

    Box(
        modifier = Modifier
            .size(width = 180.dp, height = 280.dp)
            .graphicsLayer {
                rotationY = rotation
                scaleX = scale
                scaleY = scale
                cameraDistance = 12f * density
            }
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (rotation < 90f)
                    Brush.verticalGradient(listOf(NebulaPurple.copy(0.5f), AstralViolet.copy(0.3f), CosmicCard))
                else
                    Brush.verticalGradient(listOf(CosmicCard, CosmicMidnight))
            )
            .border(
                2.dp,
                if (isRevealed) GoldenStardust else NebulaPurple,
                RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (rotation < 90f) {
            // Ön yüz - kart gösteriliyor
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = card.emoji, fontSize = 52.sp)
                Text(
                    text = card.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldenStardust,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = card.arcana + " Arkana",
                    style = MaterialTheme.typography.labelSmall,
                    color = CometGray
                )
            }
        } else {
            // Arka yüz - kart kapalı
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "🌟", fontSize = 36.sp)
                Text(
                    text = "Kartına\nOdaklan",
                    style = MaterialTheme.typography.labelLarge,
                    color = NebulaPurple,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CardDetails(card: TarotCard) {
    CosmicCard(
        modifier = Modifier.fillMaxWidth(),
        gradientColors = listOf(NebulaPurple.copy(0.2f), CosmicCard)
    ) {
        Text(
            text = "✅ Düz Anlam",
            style = MaterialTheme.typography.labelLarge,
            color = AquaGlow,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = card.meaningUpright,
            style = MaterialTheme.typography.bodyMedium,
            color = MoonSilver
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "🔄 Ters Anlam",
            style = MaterialTheme.typography.labelLarge,
            color = RoseQuartz,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = card.meaningReversed,
            style = MaterialTheme.typography.bodyMedium,
            color = MoonSilver
        )
        Spacer(Modifier.height(12.dp))
        StarDivider()
        Spacer(Modifier.height(12.dp))
        Text(
            text = "🏷️ Anahtar Kelimeler",
            style = MaterialTheme.typography.labelLarge,
            color = GoldenStardust
        )
        Spacer(Modifier.height(8.dp))
        @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
        androidx.compose.foundation.layout.FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            card.keywords.forEach { kw ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(GoldenStardust.copy(0.15f))
                        .border(1.dp, GoldenStardust.copy(0.4f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = kw,
                        style = MaterialTheme.typography.labelSmall,
                        color = GoldenStardust
                    )
                }
            }
        }
    }
}

@Composable
private fun ThreeCardSection(
    cards: Triple<TarotCard, TarotCard, TarotCard>,
    isRevealed: Boolean,
    onReveal: () -> Unit
) {
    val activity = androidx.compose.ui.platform.LocalContext.current as? android.app.Activity
    var showAdPrompt by remember { mutableStateOf(false) }
    var selectedCardDetails by remember { mutableStateOf<TarotCard?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Geçmiş • Şimdi • Gelecek",
            style = MaterialTheme.typography.headlineSmall,
            color = StarWhite,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Triple(cards.first, "GEÇMİŞ", "⬅️"),
                Triple(cards.second, "ŞİMDİ", "⭕"),
                Triple(cards.third, "GELECEK", "➡️")
            ).forEach { (card, label, arrow) ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = arrow,
                        fontSize = 16.sp
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = GoldenStardust,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    AnimatedMiniCardView(card = card, isRevealed = isRevealed, onClick = {
                        if (!isRevealed) showAdPrompt = true
                        else selectedCardDetails = card
                    })
                }
            }
        }

        if (!isRevealed) {
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { showAdPrompt = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = NebulaPurple,
                    contentColor = StarWhite
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("✨ Kartları Aç (Video İzle)")
            }
        } else {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "👆 Anlamlarını öğrenmek için kartlara dokunun",
                style = MaterialTheme.typography.bodySmall,
                color = AquaGlow,
                textAlign = TextAlign.Center
            )
        }
    }

    if (showAdPrompt && activity != null) {
        AlertDialog(
            onDismissRequest = { showAdPrompt = false },
            title = { Text("Evrenin Mesajı", color = GoldenStardust) },
            text = { Text("Kartların derin anlamını öğrenmek için kısa bir video izleyerek bize destek olur musunuz?", color = StarWhite) },
            containerColor = CosmicCard,
            confirmButton = {
                TextButton(onClick = {
                    showAdPrompt = false
                    com.example.astroyorum.ads.AdManager.showRewardedAd(
                        activity = activity,
                        onUserEarnedReward = { onReveal() },
                        onAdDismissedWithoutReward = { /* Ödül verilmedi */ }
                    )
                }) {
                    Text("Videoyu İzle", color = AquaGlow)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAdPrompt = false }) {
                    Text("Vazgeç", color = CometGray)
                }
            }
        )
    }

    // Seçilen kartın detaylarını gösteren Dialog
    if (selectedCardDetails != null) {
        AlertDialog(
            onDismissRequest = { selectedCardDetails = null },
            confirmButton = {
                TextButton(onClick = { selectedCardDetails = null }) {
                    Text("Kapat", color = AquaGlow)
                }
            },
            containerColor = CosmicMidnight,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = selectedCardDetails!!.emoji, fontSize = 28.sp)
                    Text(text = selectedCardDetails!!.name, color = GoldenStardust)
                }
            },
            text = {
                LazyColumn {
                    item {
                        CardDetails(card = selectedCardDetails!!)
                    }
                }
            }
        )
    }
}

@Composable
private fun AnimatedMiniCardView(card: TarotCard, isRevealed: Boolean, onClick: () -> Unit) {
    val rotation by animateFloatAsState(
        targetValue = if (isRevealed) 0f else 180f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "miniCardFlip"
    )
    val scale by animateFloatAsState(
        targetValue = if (isRevealed) 1f else 0.95f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "miniCardScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .graphicsLayer {
                rotationY = rotation
                scaleX = scale
                scaleY = scale
                cameraDistance = 12f * density
            }
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (rotation < 90f)
                    Brush.verticalGradient(listOf(NebulaPurple.copy(0.4f), CosmicCard))
                else
                    Brush.verticalGradient(listOf(CosmicCard, CosmicMidnight))
            )
            .border(
                1.dp,
                if (isRevealed) GoldenStardust.copy(0.6f) else CosmicCardLight,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (rotation < 90f) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = card.emoji, fontSize = 28.sp)
                Text(
                    text = card.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldenStardust,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    lineHeight = 12.sp,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        } else {
            Text(text = "🌙", fontSize = 28.sp)
        }
    }
}
