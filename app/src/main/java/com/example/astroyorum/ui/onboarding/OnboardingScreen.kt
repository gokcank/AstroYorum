package com.example.astroyorum.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astroyorum.data.*
import com.example.astroyorum.theme.*

@Composable
fun OnboardingScreen(
    onComplete: (UserProfile) -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }
    var birthDay by remember { mutableStateOf("1") }
    var birthMonth by remember { mutableStateOf("1") }
    var birthYear by remember { mutableStateOf("1990") }
    var birthHour by remember { mutableStateOf("12") }
    var birthMinute by remember { mutableStateOf("0") }
    var birthCity by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Belirtmek İstemiyorum") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(AstroBackground, Color(0xFF150D30), AstroSurface)
                )
            )
    ) {
        // Yıldızlar arka plan dekorasyonu
        Text(
            text = "✦ ✧ ⋆ ✦ ✧ ⋆ ✦ ✧ ⋆ ✦ ✧ ⋆ ✦",
            color = GoldenStardust.copy(alpha = 0.15f),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp),
            letterSpacing = 8.sp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(80.dp))

            // Logo / Başlık
            Text(
                text = "🌟",
                fontSize = 64.sp
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "AstroYorum",
                style = MaterialTheme.typography.displayMedium,
                color = GoldenStardust,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Evrenin rehberliğiyle yaşa",
                style = MaterialTheme.typography.bodyMedium,
                color = AstroTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(40.dp))

            // Adım göstergesi
            StepIndicator(currentStep = currentStep, totalSteps = 3)

            Spacer(Modifier.height(32.dp))

            // Adım içeriği
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
                }
            ) { step ->
                when (step) {
                    0 -> StepOne(name = name, onNameChange = { name = it })
                    1 -> StepTwo(
                        day = birthDay, month = birthMonth, year = birthYear,
                        hour = birthHour, minute = birthMinute,
                        onDayChange = { birthDay = it },
                        onMonthChange = { birthMonth = it },
                        onYearChange = { birthYear = it },
                        onHourChange = { birthHour = it },
                        onMinuteChange = { birthMinute = it }
                    )
                    2 -> StepThree(
                        city = birthCity, 
                        gender = gender,
                        onCityChange = { birthCity = it },
                        onGenderChange = { gender = it }
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Navigasyon butonları
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (currentStep > 0) {
                    OutlinedButton(
                        onClick = { currentStep-- },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = GoldenStardust
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldenStardust)
                    ) {
                        Text("← Geri")
                    }
                }

                Button(
                    onClick = {
                        if (currentStep < 2) {
                            currentStep++
                        } else {
                            val d = birthDay.toIntOrNull() ?: 1
                            val m = birthMonth.toIntOrNull() ?: 1
                            val zodiacId = calculateZodiacId(d, m)
                            onComplete(
                                UserProfile(
                                    name = name.ifEmpty { "Yolcu" },
                                    birthDay = d,
                                    birthMonth = m,
                                    birthYear = birthYear.toIntOrNull() ?: 1990,
                                    birthHour = birthHour.toIntOrNull() ?: 12,
                                    birthMinute = birthMinute.toIntOrNull() ?: 0,
                                    birthCity = birthCity,
                                    gender = gender,
                                    zodiacSignId = zodiacId
                                )
                            )
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldenStardust,
                        contentColor = AstroBackground
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (currentStep < 2) "Devam →" else "Yolculuğu Başlat ✨",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Eğlence amaçlı uyarı
            Text(
                text = "⚠️ Bu uygulama eğlence amaçlıdır.",
                style = MaterialTheme.typography.labelSmall,
                color = AstroTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun StepIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { index ->
            val isActive = index == currentStep
            val isDone = index < currentStep
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(if (isActive) 32.dp else 16.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (isActive || isDone) GoldenStardust
                        else AstroCardLight
                    )
            )
        }
    }
}

@Composable
private fun StepOne(name: String, onNameChange: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Merhaba! 👋",
            style = MaterialTheme.typography.headlineMedium,
            color = AstroDark,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Seni nasıl çağıralım?",
            style = MaterialTheme.typography.bodyMedium,
            color = AstroTextSecondary,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Adın") },
            placeholder = { Text("Örn: Ayşe") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GoldenStardust,
                unfocusedBorderColor = AstroCardLight,
                focusedLabelColor = GoldenStardust,
                cursorColor = GoldenStardust,
                focusedTextColor = AstroDark,
                unfocusedTextColor = AstroText
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
private fun StepTwo(
    day: String, month: String, year: String, hour: String, minute: String,
    onDayChange: (String) -> Unit, onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit, onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Doğum Tarihin 🎂",
            style = MaterialTheme.typography.headlineMedium,
            color = AstroDark,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Güneş, Ay ve Yükselen burcunu hesaplayalım",
            style = MaterialTheme.typography.bodySmall,
            color = AstroTextSecondary,
            textAlign = TextAlign.Center
        )

        // Tarih satırı
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberField("Gün", day, 1, 31, Modifier.weight(1f), onDayChange)
            NumberField("Ay", month, 1, 12, Modifier.weight(1f), onMonthChange)
            NumberField("Yıl", year, 1900, 2010, Modifier.weight(1.5f), onYearChange)
        }

        // Saat satırı
        Text(
            text = "Doğum Saati (yaklaşık olabilir)",
            style = MaterialTheme.typography.labelMedium,
            color = AstroTextSecondary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberField("Saat", hour, 0, 23, Modifier.weight(1f), onHourChange)
            NumberField("Dakika", minute, 0, 59, Modifier.weight(1f), onMinuteChange)
        }

        // Hesaplanan burç önizlemesi
        val d = day.toIntOrNull() ?: 1
        val m = month.toIntOrNull() ?: 1
        val zodiacId = calculateZodiacId(d, m)
        val sign = com.example.astroyorum.data.ZodiacDatabase.getById(zodiacId)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(GoldenDim.copy(0.3f), NebulaPurple.copy(0.2f))
                    )
                )
                .border(1.dp, GoldenStardust.copy(0.5f), RoundedCornerShape(12.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${sign.emoji} Güneş Burcun: ${sign.name}",
                style = MaterialTheme.typography.titleMedium,
                color = GoldenStardust,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun NumberField(
    label: String, value: String, min: Int, max: Int,
    modifier: Modifier = Modifier, onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { text ->
            // Sadece sayı girilmesine izin ver (boşluklara veya geçici geçersizliklere izin vererek)
            if (text.isEmpty() || text.all { it.isDigit() }) {
                onValueChange(text)
            }
        },
        label = { Text(label, fontSize = 11.sp) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GoldenStardust,
            unfocusedBorderColor = AstroCardLight,
            focusedLabelColor = GoldenStardust,
            cursorColor = GoldenStardust,
            focusedTextColor = AstroDark,
            unfocusedTextColor = AstroText
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
private fun StepThree(city: String, gender: String, onCityChange: (String) -> Unit, onGenderChange: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Doğum Şehrin 🌍",
            style = MaterialTheme.typography.headlineMedium,
            color = AstroDark,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Yükselen burcun için isteğe bağlıdır",
            style = MaterialTheme.typography.bodySmall,
            color = AstroTextSecondary
        )
        OutlinedTextField(
            value = city,
            onValueChange = onCityChange,
            label = { Text("Şehir") },
            placeholder = { Text("Örn: İstanbul") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GoldenStardust,
                unfocusedBorderColor = AstroCardLight,
                focusedLabelColor = GoldenStardust,
                cursorColor = GoldenStardust,
                focusedTextColor = AstroDark,
                unfocusedTextColor = AstroText
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Popüler şehirler
        Text(
            text = "Hızlı seç:",
            style = MaterialTheme.typography.labelMedium,
            color = AstroTextSecondary,
            modifier = Modifier.align(Alignment.Start)
        )
        val cities = listOf("İstanbul", "Ankara", "İzmir", "Antalya", "Bursa", "Adana")
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.heightIn(max = 120.dp)
        ) {
            items(cities) { c ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(AstroCard)
                        .border(
                            1.dp,
                            if (city == c) GoldenStardust else AstroCardLight,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onCityChange(c) }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = c,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (city == c) GoldenStardust else AstroText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Cinsiyet (Yapay zeka analizleri için)",
            style = MaterialTheme.typography.labelMedium,
            color = AstroTextSecondary,
            modifier = Modifier.align(Alignment.Start)
        )
        val genders = listOf("Kadın", "Erkek", "Belirtmek İstemiyorum")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            genders.forEach { g ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AstroCard)
                        .border(
                            1.dp,
                            if (gender == g) GoldenStardust else AstroCardLight,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onGenderChange(g) }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = g,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (gender == g) GoldenStardust else AstroText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

