package com.example.astroyorum.data

import android.util.Log
import com.example.astroyorum.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
data class SupabaseHoroscopeRow(
    val date: String,
    val horoscopes: JsonObject,
    val scores: JsonObject,
    val moon_phase: JsonObject? = null
)

class HoroscopeRepository {
    private val supabase = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
    }

    suspend fun getDailyAstroData(): DailyAstroData? {
        return try {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val row = supabase.from("daily_horoscopes")
                .select {
                    filter { eq("date", dateStr) }
                }
                .decodeSingleOrNull<SupabaseHoroscopeRow>()

            if (row != null) {
                // horoscopes: JsonObject -> Map<String, String>
                val horoscopes = row.horoscopes.mapValues { (_, v) ->
                    v.jsonPrimitive.content
                }

                // scores: JsonObject -> Map<String, ZodiacScores>
                val scores = row.scores.mapValues { (_, v) ->
                    val obj = v.jsonObject
                    ZodiacScores(
                        love = obj["love"]?.jsonPrimitive?.int ?: 50,
                        career = obj["career"]?.jsonPrimitive?.int ?: 50,
                        health = obj["health"]?.jsonPrimitive?.int ?: 50,
                        luckyNumber = obj["luckyNumber"]?.jsonPrimitive?.int ?: 0,
                        luckyStone = obj["luckyStone"]?.jsonPrimitive?.content ?: "",
                        luckyColor = obj["luckyColor"]?.jsonPrimitive?.content ?: ""
                    )
                }

                // moon_phase (opsiyonel)
                val moonPhase = row.moon_phase?.let {
                    MoonPhase(
                        date = it["date"]?.jsonPrimitive?.content ?: dateStr,
                        phaseName = it["phaseName"]?.jsonPrimitive?.content ?: "",
                        phaseEmoji = it["phaseEmoji"]?.jsonPrimitive?.content ?: "",
                        illumination = it["illumination"]?.jsonPrimitive?.double?.toFloat() ?: 0f,
                        zodiacSign = it["zodiacSign"]?.jsonPrimitive?.content ?: "",
                        ritual = it["ritual"]?.jsonPrimitive?.content ?: "",
                        energy = it["energy"]?.jsonPrimitive?.content ?: ""
                    )
                } ?: todayMoonPhase()

                DailyAstroData(horoscopes, scores, moonPhase)
            } else {
                // Veritabanında bugün için kayıt yoksa fallback verileri döndür
                Log.w("HoroscopeRepository", "Bugün için Supabase'de veri bulunamadı: $dateStr")
                val mockHoroscopes = mapOf(
                    "Aries" to "Bugün Koç burçları için enerjik ve hareketli bir gün.",
                    "Taurus" to "Sevgili Boğa, planlarınızı sağlamlaştırmak için mükemmel bir gün.",
                    "Gemini" to "İkizler, iletişim becerileriniz harika.",
                    "Cancer" to "Duygusal yengeçler, iç sesinize kulak verin.",
                    "Leo" to "Aslanlar sahneye çıkmaya hazır olun!",
                    "Virgo" to "Başak burçları, düzen arayışınız sonuç veriyor.",
                    "Libra" to "Teraziler, uyum ve denge bugün sizinle.",
                    "Scorpio" to "Gizemli Akrepler, odaklanma gücünüz çok yüksek.",
                    "Sagittarius" to "Özgür ruhlu Yaylar, yeni maceralar kapıda.",
                    "Capricorn" to "Oğlaklar, disiplinli çalışmanızın karşılığını alma zamanı geldi.",
                    "Aquarius" to "Yenilikçi Kovalar, farklı fikirlerinizle bugün herkesi şaşırtacaksınız.",
                    "Pisces" to "Duyarlı Balıklar, hayal gücünüz sınır tanımıyor."
                )
                val colors = listOf("Kırmızı", "Mavi", "Yeşil", "Sarı", "Mor", "Turuncu", "Beyaz")
                val stones = listOf("Ametist", "Kuvars", "Yakut", "Zümrüt", "Safir", "Turkuaz")
                val mockScores = mockHoroscopes.keys.associateWith {
                    ZodiacScores(
                        love = (50..100).random(), career = (50..100).random(),
                        health = (50..100).random(), luckyNumber = (1..99).random(),
                        luckyStone = stones.random(), luckyColor = colors.random()
                    )
                }
                DailyAstroData(mockHoroscopes, mockScores, todayMoonPhase())
            }
        } catch (e: Exception) {
            Log.e("HoroscopeRepository", "Supabase veri çekme hatası", e)
            null
        }
    }
}
