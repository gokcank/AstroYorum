package com.example.astroyorum.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme

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
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ─── Başlık ──────────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.background))
                    )
                    .padding(20.dp)
            ) {
                Text(
                    text = "👤 Profilim",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
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
                                MaterialTheme.colorScheme.surfaceVariant
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
                                    listOf(GoldenDim.copy(0.5f), MaterialTheme.colorScheme.surfaceVariant)
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
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = sign.dates,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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
                        if (userProfile.gender != "Belirtmek İstemiyorum") {
                            ProfileInfoItem(
                                if (userProfile.gender == "Kadın") "👩" else "👨", 
                                "Cinsiyet", 
                                userProfile.gender
                            )
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
            AstroCard(
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
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.titleSmall,
                            color = GoldenStardust,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(0.5f))
                }
            }
        }

        // ─── Ayarlar & Görünüm ───────────────────────────────────────────
        item {
            val repo = remember { com.example.astroyorum.data.UserPreferencesRepository(context) }
            val themePref by repo.themePreference.collectAsState(initial = 0)
            val scope = rememberCoroutineScope()

            AstroCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SectionHeader("⚙️ Görünüm Ayarları", "Temanı seç")
                Spacer(Modifier.height(16.dp))

                val options = listOf("Sistem" to 0, "Açık" to 1, "Koyu" to 2)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    options.forEach { (label, value) ->
                        val isSelected = themePref == value
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) GoldenStardust.copy(0.2f) else MaterialTheme.colorScheme.outline)
                                .border(
                                    1.dp,
                                    if (isSelected) GoldenStardust else androidx.compose.ui.graphics.Color.Transparent,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    scope.launch { repo.saveThemePreference(value) }
                                }
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isSelected) GoldenStardust else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // ─── Uygulama Hakkında ───────────────────────────────────────────
        item {
            AstroCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SectionHeader("ℹ️ Uygulama", "")
                Spacer(Modifier.height(12.dp))

                listOf(
                    "🌟 AstroYorum v1.0.2 (Closed Beta)",
                    "👨‍💻 Geliştirici: gokcank",
                    "🔮 Günlük burç yorumları",
                    "🃏 Tarot fal modülü",
                    "🌙 Ay takvimi & ritüeller"
                ).forEach { item ->
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f),
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
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
    var gender by remember { mutableStateOf(currentProfile.gender) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
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
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = GoldenStardust,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
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
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    OutlinedTextField(
                        value = month,
                        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) month = it },
                        label = { Text("Ay") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    OutlinedTextField(
                        value = year,
                        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) year = it },
                        label = { Text("Yıl") },
                        singleLine = true,
                        modifier = Modifier.weight(1.2f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface
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
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    OutlinedTextField(
                        value = minute,
                        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) minute = it },
                        label = { Text("Dakika") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldenStardust, unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface
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
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = GoldenStardust,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text("Cinsiyet", color = GoldenStardust, style = MaterialTheme.typography.labelMedium)
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
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                    1.dp,
                                    if (gender == g) GoldenStardust else MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { gender = g }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = g,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (gender == g) GoldenStardust else MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
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
                        gender = gender,
                        zodiacSignId = calculateZodiacId(d, m)
                    ))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldenStardust,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) { Text("Kaydet") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}


