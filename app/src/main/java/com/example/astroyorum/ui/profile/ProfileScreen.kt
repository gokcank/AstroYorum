package com.example.astroyorum.ui.profile

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import com.example.astroyorum.data.UserProfile
import com.example.astroyorum.data.ZodiacDatabase
import com.example.astroyorum.data.calculateZodiacId
import com.example.astroyorum.theme.*
import com.example.astroyorum.ui.components.*

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onSaveProfile: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sign = ZodiacDatabase.getById(userProfile.zodiacSignId)
    var showEditDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CosmicDeepPurple),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ─── Başlık ──────────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(CosmicMidnight, CosmicDeepPurple))
                    )
                    .padding(20.dp)
            ) {
                Text(
                    text = "👤 Profilim",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MoonSilver,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // ─── Profil Kartı ─────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                GoldenDim.copy(0.3f),
                                NebulaPurple.copy(0.2f),
                                CosmicCard
                            )
                        )
                    )
                    .border(1.dp, GoldenStardust.copy(0.3f), RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(GoldenDim.copy(0.5f), CosmicCard)
                                )
                            )
                            .border(2.dp, GoldenStardust.copy(0.6f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = sign.emoji, fontSize = 48.sp)
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = userProfile.name.ifEmpty { "Yolcu" },
                        style = MaterialTheme.typography.headlineMedium,
                        color = GoldenStardust,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${sign.name} ${sign.symbol}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MoonSilver
                    )

                    Text(
                        text = sign.dates,
                        style = MaterialTheme.typography.bodySmall,
                        color = CometGray
                    )

                    Spacer(Modifier.height(16.dp))
                    StarDivider()
                    Spacer(Modifier.height(16.dp))

                    // Doğum bilgileri
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileInfoItem(
                            "📅",
                            "Doğum",
                            "${userProfile.birthDay}/${userProfile.birthMonth}/${userProfile.birthYear}"
                        )
                        ProfileInfoItem(
                            "🕐",
                            "Saat",
                            "${userProfile.birthHour.toString().padStart(2, '0')}:${userProfile.birthMinute.toString().padStart(2, '0')}"
                        )
                        if (userProfile.birthCity.isNotEmpty()) {
                            ProfileInfoItem("🌍", "Şehir", userProfile.birthCity)
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = { showEditDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldenStardust.copy(0.15f),
                            contentColor = GoldenStardust
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("✏️ Profili Düzenle")
                    }
                }
            }
        }

        // ─── Element & Gezegen Bilgileri ─────────────────────────────────
        item {
            CosmicCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SectionHeader("🔮 Astrolojik Kimliğin", "")
                Spacer(Modifier.height(12.dp))

                listOf(
                    Triple("☀️", "Güneş Burcu", sign.name),
                    Triple(sign.elementEmoji, "Element", sign.element),
                    Triple("💎", "Şans Taşı", sign.luckyStone),
                    Triple("🔢", "Şans Sayısı", sign.luckyNumber.toString()),
                    Triple("🎨", "Şans Rengi", sign.luckyColor)
                ).forEach { (emoji, label, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = emoji, fontSize = 18.sp)
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = CometGray,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.titleSmall,
                            color = GoldenStardust,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    HorizontalDivider(color = CosmicCardLight.copy(0.5f))
                }
            }
        }

        // ─── Uygulama Hakkında ───────────────────────────────────────────
        item {
            CosmicCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SectionHeader("ℹ️ Uygulama", "")
                Spacer(Modifier.height(12.dp))

                listOf(
                    "🌟 AstroYorum v1.0.1 (Internal Testing)",
                    "👨‍💻 Geliştirici: gokcank",
                    "🔮 Günlük burç yorumları",
                    "🃏 Tarot fal modülü",
                    "🌙 Ay takvimi & ritüeller"
                ).forEach { item ->
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodySmall,
                        color = CometGray,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {
                        val subject = Uri.encode("HATA BİLDİRİMİ")
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:destek.gokcank@gmail.com?subject=$subject")
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Cihazda e-posta istemcisi yoksa hatayı yoksay
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldenStardust),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldenStardust),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🐞 Hata Bildir")
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Yasal Uyarı ve Sorumluluk Reddi: Bu uygulama yalnızca eğlence ve kişisel gelişim amaçlıdır. Astroloji ve tarot okumaları kesin yargılar içermez, tıbbi, hukuki veya finansal tavsiye yerine geçemez.",
                    style = MaterialTheme.typography.labelSmall,
                    color = CometGray.copy(0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            currentProfile = userProfile,
            onDismiss = { showEditDialog = false },
            onSave = {
                onSaveProfile(it)
                showEditDialog = false
            }
        )
    }
}

@Composable
private fun ProfileInfoItem(emoji: String, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = emoji, fontSize = 20.sp)
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = StarWhite,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = CometGray
        )
    }
}

@Composable
private fun EditProfileDialog(
    currentProfile: UserProfile,
    onDismiss: () -> Unit,
    onSave: (UserProfile) -> Unit
) {
    var name by remember { mutableStateOf(currentProfile.name) }
    var day by remember { mutableStateOf(currentProfile.birthDay.toString()) }
    var month by remember { mutableStateOf(currentProfile.birthMonth.toString()) }
    var year by remember { mutableStateOf(currentProfile.birthYear.toString()) }
    var hour by remember { mutableStateOf(currentProfile.birthHour.toString()) }
    var minute by remember { mutableStateOf(currentProfile.birthMinute.toString()) }
    var city by remember { mutableStateOf(currentProfile.birthCity) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CosmicMidnight,
        title = {
            Text("Profili Düzenle", color = GoldenStardust, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Adın") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldenStardust,
                        unfocusedBorderColor = CosmicCardLight,
                        focusedLabelColor = GoldenStardust,
                        focusedTextColor = StarWhite,
                        unfocusedTextColor = MoonSilver
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = day,
                        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) day = it },
                        label = { Text("Gün") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = CosmicCardLight,
                            focusedTextColor = StarWhite, unfocusedTextColor = MoonSilver
                        )
                    )
                    OutlinedTextField(
                        value = month,
                        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) month = it },
                        label = { Text("Ay") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = CosmicCardLight,
                            focusedTextColor = StarWhite, unfocusedTextColor = MoonSilver
                        )
                    )
                    OutlinedTextField(
                        value = year,
                        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) year = it },
                        label = { Text("Yıl") },
                        singleLine = true,
                        modifier = Modifier.weight(1.2f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = CosmicCardLight,
                            focusedTextColor = StarWhite, unfocusedTextColor = MoonSilver
                        )
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = hour,
                        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) hour = it },
                        label = { Text("Saat") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = CosmicCardLight,
                            focusedTextColor = StarWhite, unfocusedTextColor = MoonSilver
                        )
                    )
                    OutlinedTextField(
                        value = minute,
                        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) minute = it },
                        label = { Text("Dakika") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = CosmicCardLight,
                            focusedTextColor = StarWhite, unfocusedTextColor = MoonSilver
                        )
                    )
                }

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Doğum Şehrin") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldenStardust,
                        unfocusedBorderColor = CosmicCardLight,
                        focusedLabelColor = GoldenStardust,
                        focusedTextColor = StarWhite,
                        unfocusedTextColor = MoonSilver
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val d = day.toIntOrNull() ?: 1
                    val m = month.toIntOrNull() ?: 1
                    val y = year.toIntOrNull() ?: 1990
                    val h = hour.toIntOrNull() ?: 12
                    val min = minute.toIntOrNull() ?: 0

                    onSave(currentProfile.copy(
                        name = name,
                        birthDay = d,
                        birthMonth = m,
                        birthYear = y,
                        birthHour = h,
                        birthMinute = min,
                        birthCity = city,
                        zodiacSignId = calculateZodiacId(d, m)
                    ))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldenStardust,
                    contentColor = CosmicDeepPurple
                )
            ) { Text("Kaydet") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal", color = CometGray)
            }
        }
    )
}

