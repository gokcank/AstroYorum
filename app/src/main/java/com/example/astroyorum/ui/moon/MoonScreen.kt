package com.example.astroyorum.ui.moon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.astroyorum.data.MoonPhase
import com.example.astroyorum.theme.*
import com.example.astroyorum.ui.components.*
import androidx.compose.material3.MaterialTheme

@Composable
fun MoonScreen(
    horoscopeUiState: com.example.astroyorum.ui.main.HoroscopeUiState,
    modifier: Modifier = Modifier
) {
    val today = remember { java.util.Calendar.getInstance() }
    val monthNames = listOf(
        "Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran",
        "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"
    )
    val currentMonth = monthNames[today.get(java.util.Calendar.MONTH)]
    val currentYear = today.get(java.util.Calendar.YEAR)
    val currentDay = today.get(java.util.Calendar.DAY_OF_MONTH)

    // Bu ay için ay fazları hesapla
    val monthlyPhases = remember { generateMonthlyPhases(currentMonth, currentYear) }
    val todayPhase = remember(horoscopeUiState) { 
        if (horoscopeUiState is com.example.astroyorum.ui.main.HoroscopeUiState.Success) {
            horoscopeUiState.data.moonPhase ?: getTodayPhase()
        } else {
            getTodayPhase()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF060D1F), MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.surface)
                )
            )
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ─── Başlık ─────────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF060D1F), MaterialTheme.colorScheme.background)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text = "🌙 Ay Takvimi",
                            style = MaterialTheme.typography.headlineMedium,
                            color = AquaGlow,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$currentMonth $currentYear",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // ─── Bugünkü Ay ─────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    AquaGlow.copy(0.15f),
                                    Color(0xFF0A1628),
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                        .border(
                            1.dp,
                            AquaGlow.copy(0.3f),
                            RoundedCornerShape(24.dp)
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = todayPhase.phaseEmoji,
                            fontSize = 72.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = todayPhase.phaseName,
                            style = MaterialTheme.typography.headlineLarge,
                            color = AquaGlow,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${todayPhase.zodiacSign} Burcunda",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(16.dp))

                        // Aydınlanma oranı
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ) {
                            Text(
                                text = "Aydınlanma: ${(todayPhase.illumination * 100).toInt()}%",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { todayPhase.illumination },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = AquaGlow,
                                trackColor = MaterialTheme.colorScheme.outline
                            )
                        }

                        Spacer(Modifier.height(16.dp))
                        StarDivider()
                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "✨ ${todayPhase.energy}",
                            style = MaterialTheme.typography.titleSmall,
                            color = GoldenStardust,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = todayPhase.ritual,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }

            // ─── Bu Ay Önemli Ay Fazları ─────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeader(
                        title = "📅 Bu Ay Önemli Günler",
                        subtitle = "$currentMonth $currentYear"
                    )
                }
            }

            items(monthlyPhases) { phase ->
                MoonPhaseListItem(
                    phase = phase,
                    isToday = phase.date.contains(currentDay.toString()),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // ─── Ay Ritüelleri ─────────────────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeader("🕯️ Ay Ritüelleri", "Ay enerjisiyle uyum")
                    Spacer(Modifier.height(12.dp))

                    listOf(
                        Triple("🌑 Yeniay", "Yeni Ay Niyeti", "Kağıda bu ay için 3 niyet yaz. Muma yakarak veya meditasyonla niyetlerini evrene bırak. Hafif bir su içi, yeni başlangıçları sembolize eder."),
                        Triple("🌕 Dolunay", "Dolunay Şükranı", "Ayın ışığı altında 3 şükran yaz. Bir şeyi bırakmak istiyorsan kağıda yaz ve yak. Tuz banyosu veya ay suyu hazırlama için idealdir."),
                        Triple("🌗 Son Dördün", "Bırakma Ritüeli", "Hayatından çıkarmak istediğin şeyleri yaz. Bunları bırakmayı dileyen bir meditasyon yap. Evi temizle, yenilenme için yer aç.")
                    ).forEach { (emoji, title, desc) ->
                        AstroCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            gradientColors = listOf(MaterialTheme.colorScheme.surfaceVariant, Color(0xFF0A1628))
                        ) {
                            Text(
                                text = emoji,
                                style = MaterialTheme.typography.titleMedium,
                                color = AquaGlow,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = desc,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MoonPhaseListItem(
    phase: MoonPhase,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (isToday)
                    Brush.horizontalGradient(listOf(AquaGlow.copy(0.2f), MaterialTheme.colorScheme.surfaceVariant))
                else
                    Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface))
            )
            .border(
                1.dp,
                if (isToday) AquaGlow.copy(0.5f) else MaterialTheme.colorScheme.outline,
                RoundedCornerShape(14.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Ay emoji
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(text = phase.phaseEmoji, fontSize = 28.sp)
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = phase.phaseName,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isToday) AquaGlow else MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                if (isToday) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(AquaGlow.copy(0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "BUGÜN",
                            style = MaterialTheme.typography.labelSmall,
                            color = AquaGlow,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Text(
                text = phase.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = phase.zodiacSign + " ✦ " + phase.energy,
                style = MaterialTheme.typography.labelSmall,
                color = GoldenStardust
            )
        }

        // Aydınlanma
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${(phase.illumination * 100).toInt()}%",
                style = MaterialTheme.typography.labelLarge,
                color = AquaGlow,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Işık",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun getTodayPhase(): MoonPhase {
    val cal = java.util.Calendar.getInstance()
    val day = cal.get(java.util.Calendar.DAY_OF_MONTH)
    val monthNames = listOf(
        "Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran",
        "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"
    )
    val month = monthNames[cal.get(java.util.Calendar.MONTH)]

    return when (day % 8) {
        0 -> MoonPhase("$day $month", "Yeniay", "🌑", 0.02f, "Koç", "Niyetlerinizi belirleyin", "Yeni başlangıçlar")
        1, 2 -> MoonPhase("$day $month", "Hilal Ay", "🌒", 0.25f, "Boğa", "Tohumları ekin", "Büyüme enerjisi")
        3 -> MoonPhase("$day $month", "İlk Dördün", "🌓", 0.50f, "İkizler", "Eylem zamanı", "Karar ve eylem")
        4, 5 -> MoonPhase("$day $month", "Şişan Ay", "🌔", 0.75f, "Aslan", "Enerjinizi odaklayın", "Yoğun enerji")
        6 -> MoonPhase("$day $month", "Dolunay", "🌕", 1.0f, "Akrep", "Şükran ritüeli yapın", "Zirve ve açığa çıkma")
        else -> MoonPhase("$day $month", "Son Dördün", "🌗", 0.50f, "Kova", "Bırakma zamanı", "Salıverme")
    }
}

private fun generateMonthlyPhases(month: String, year: Int): List<MoonPhase> {
    // Ayın önemli fazları (basitleştirilmiş hesaplama)
    return listOf(
        MoonPhase("1 $month $year", "Yeniay", "🌑", 0.02f, "İkizler", "Yeni döngünün başlangıcı. Niyetlerinizi belirleyin.", "Taze başlangıçlar"),
        MoonPhase("8 $month $year", "İlk Dördün", "🌓", 0.50f, "Başak", "Hedeflerinizde ilerleme zamanı.", "Eylem ve karar"),
        MoonPhase("15 $month $year", "Dolunay", "🌕", 1.0f, "Yay", "Hasat zamanı. Şükran ritüeli yapın.", "Doruk nokta"),
        MoonPhase("22 $month $year", "Son Dördün", "🌗", 0.50f, "Balık", "Bırakmak için doğru an.", "Salıverme ve temizlik"),
        MoonPhase("29 $month $year", "Yeniay", "🌑", 0.02f, "Kova", "Yeni bir döngü başlıyor.", "Taze başlangıçlar")
    )
}

