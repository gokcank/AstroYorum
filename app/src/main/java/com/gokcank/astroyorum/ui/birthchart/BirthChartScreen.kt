package com.gokcank.astroyorum.ui.birthchart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gokcank.astroyorum.data.BirthChartData
import com.gokcank.astroyorum.data.UserProfile
import com.gokcank.astroyorum.data.ZodiacDatabase
import com.gokcank.astroyorum.data.calculateZodiacId
import com.gokcank.astroyorum.theme.*
import com.gokcank.astroyorum.ui.components.*
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.material3.MaterialTheme

@Composable
fun BirthChartScreen(
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    // Basit doğum haritası hesaplama (gerçek uygulamada Swiss Ephemeris kullanılır)
    val chartData = remember(userProfile) { calculateSimpleChart(userProfile) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.background)))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "🔯 Doğum Haritası",
                        style = MaterialTheme.typography.headlineMedium,
                        color = AstroLavender,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${userProfile.name} · ${userProfile.birthDay}/${userProfile.birthMonth}/${userProfile.birthYear}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // ─── Natal Chart SVG/Canvas ──────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                NatalChartCanvas(chartData = chartData)
            }
        }

        // ─── Üçlü Burç Özeti ─────────────────────────────────────────────
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf(
                    Triple("☀️", "Güneş", chartData.sunSign),
                    Triple("🌙", "Ay", chartData.moonSign),
                    Triple("⬆️", "Yükselen", chartData.risingSign)
                ).forEach { (emoji, label, signName) ->
                    AstroCard(
                        modifier = Modifier.weight(1f),
                        gradientColors = listOf(NebulaPurple.copy(0.2f), MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = emoji, fontSize = 24.sp)
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = ZodiacDatabase.signs.find { it.name == signName }?.emoji ?: "⭐",
                                fontSize = 20.sp
                            )
                            Text(
                                text = signName,
                                style = MaterialTheme.typography.labelMedium,
                                color = GoldenStardust,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // ─── Gezegen Konumları ───────────────────────────────────────────
        item {
            AstroCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SectionHeader("🪐 Gezegen Konumları", "Doğum anındaki pozisyonlar")
                Spacer(Modifier.height(12.dp))

                val planets = listOf(
                    Triple("☀️", "Güneş", chartData.sunSign),
                    Triple("🌙", "Ay", chartData.moonSign),
                    Triple("⬆️", "Yükselen", chartData.risingSign),
                    Triple("☿", "Merkür", chartData.mercury),
                    Triple("♀", "Venüs", chartData.venus),
                    Triple("♂", "Mars", chartData.mars),
                    Triple("♃", "Jüpiter", chartData.jupiter),
                    Triple("♄", "Satürn", chartData.saturn),
                    Triple("♅", "Uranüs", chartData.uranus),
                    Triple("♆", "Neptün", chartData.neptune),
                    Triple("♇", "Plüton", chartData.pluto)
                )

                planets.forEach { (symbol, name, sign) ->
                    val zodiac = ZodiacDatabase.signs.find { it.name == sign }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = symbol,
                            fontSize = 16.sp,
                            modifier = Modifier.width(28.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = zodiac?.emoji ?: "⭐", fontSize = 14.sp)
                            Text(
                                text = sign,
                                style = MaterialTheme.typography.labelLarge,
                                color = GoldenStardust,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    if (symbol != "♇") {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(0.4f))
                    }
                }
            }
        }

        // ─── Bilgi notu ───────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp)
            ) {
                Text(
                    text = "ℹ️ Bu harita basitleştirilmiş bir hesaplamadır. Kesin sonuçlar için doğum saati ve şehir bilgisi gereklidir. Eğlence amaçlıdır.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun NatalChartCanvas(chartData: BirthChartData) {
    val zodiacEmojis = ZodiacDatabase.signs.map { it.symbol }
    val chartSize = 280.dp

    Box(
        modifier = Modifier
            .size(chartSize)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    listOf(NebulaPurple.copy(0.2f), MaterialTheme.colorScheme.background, Color(0xFF0A0515))
                )
            )
            .border(2.dp, GoldenStardust.copy(0.4f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawNatalChart(zodiacEmojis)
        }

        // Merkez
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "🌟", fontSize = 24.sp)
            Text(
                text = "Doğum\nHaritan",
                style = MaterialTheme.typography.labelSmall,
                color = GoldenStardust.copy(0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun DrawScope.drawNatalChart(signs: List<String>) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val outerRadius = size.minDimension / 2f * 0.9f
    val innerRadius = size.minDimension / 2f * 0.55f
    val midRadius = (outerRadius + innerRadius) / 2f

    // 12 burç dilimlerini çiz
    val sectorAngle = 360f / 12f
    for (i in 0 until 12) {
        val startAngle = i * sectorAngle - 90f
        // Dış daire yayı
        drawArc(
            color = GoldenStardust.copy(alpha = 0.15f),
            startAngle = startAngle,
            sweepAngle = sectorAngle - 1f,
            useCenter = false,
            topLeft = Offset(center.x - outerRadius, center.y - outerRadius),
            size = Size(outerRadius * 2, outerRadius * 2),
            style = Stroke(width = 2.dp.toPx())
        )
        // Bölücü çizgiler
        val lineAngle = Math.toRadians(startAngle.toDouble())
        drawLine(
            color = GoldenStardust.copy(alpha = 0.2f),
            start = Offset(
                center.x + (innerRadius * cos(lineAngle)).toFloat(),
                center.y + (innerRadius * sin(lineAngle)).toFloat()
            ),
            end = Offset(
                center.x + (outerRadius * cos(lineAngle)).toFloat(),
                center.y + (outerRadius * sin(lineAngle)).toFloat()
            ),
            strokeWidth = 1.dp.toPx()
        )
    }

    // İç daire
    drawCircle(
        color = NebulaPurple.copy(alpha = 0.3f),
        center = center,
        radius = innerRadius,
        style = Stroke(width = 1.5.dp.toPx())
    )

    // Orta daire
    drawCircle(
        color = GoldenStardust.copy(alpha = 0.1f),
        center = center,
        radius = midRadius,
        style = Stroke(width = 1.dp.toPx())
    )
}

// Basitleştirilmiş doğum haritası hesaplama
fun calculateSimpleChart(profile: UserProfile): BirthChartData {
    return try {
        val signs = listOf("Koç", "Boğa", "İkizler", "Yengeç", "Aslan", "Başak",
            "Terazi", "Akrep", "Yay", "Oğlak", "Kova", "Balık")

        val safeDay = profile.birthDay.coerceIn(1, 31)
        val safeMonth = profile.birthMonth.coerceIn(1, 12)
        val sunId = calculateZodiacId(safeDay, safeMonth).coerceIn(0, 11)
        val birthHour = profile.birthHour.coerceAtLeast(0)
        val birthYear = profile.birthYear.coerceAtLeast(0)

        val moonId = (sunId + birthHour / 2) % 12
        val risingId = (sunId + birthHour / 2 + 3) % 12
        val mercuryId = (sunId + 1) % 12
        val venusId = (sunId + 2) % 12
        val marsId = (sunId + birthYear % 2 + 4) % 12
        val jupiterId = (sunId + birthYear % 12) % 12
        val saturnId = (sunId + birthYear % 8 + 3) % 12
        val uranusId = (sunId + 7) % 12
        val neptuneId = (sunId + 10) % 12
        val plutoId = (sunId + 5) % 12

        BirthChartData(
            sunSign = signs[sunId],
            moonSign = signs[moonId],
            risingSign = signs[risingId],
            mercury = signs[mercuryId],
            venus = signs[venusId],
            mars = signs[marsId],
            jupiter = signs[jupiterId],
            saturn = signs[saturnId],
            uranus = signs[uranusId],
            neptune = signs[neptuneId],
            pluto = signs[plutoId]
        )
    } catch (e: Exception) {
        // Hata durumunda varsayılan harita döndür
        BirthChartData(
            sunSign = "Koç", moonSign = "Koç", risingSign = "Koç",
            mercury = "Koç", venus = "Koç", mars = "Koç", jupiter = "Koç",
            saturn = "Koç", uranus = "Koç", neptune = "Koç", pluto = "Koç"
        )
    }
}

