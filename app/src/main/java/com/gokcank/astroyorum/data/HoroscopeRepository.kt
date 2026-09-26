package com.gokcank.astroyorum.data

import android.util.Log
import com.gokcank.astroyorum.BuildConfig
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
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

            var row = fetchHoroscopeRow(dateStr)

            // Eğer veritabanında bugün için kayıt yoksa Edge Function'ı otomatik tetikle (On-Demand)
            if (row == null) {
                Log.i("HoroscopeRepository", "Bugün için kayıt bulunamadı ($dateStr), Edge Function tetikleniyor...")
                val success = triggerEdgeFunction()
                if (success) {
                    row = fetchHoroscopeRow(dateStr)
                }
            }

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
                Log.w("HoroscopeRepository", "Bugün için astroloji verisi temin edilemedi: $dateStr")
                null
            }
        } catch (e: Exception) {
            Log.e("HoroscopeRepository", "Supabase veri çekme hatası", e)
            null
        }
    }

    private suspend fun fetchHoroscopeRow(dateStr: String): SupabaseHoroscopeRow? {
        return try {
            supabase.from("daily_horoscopes")
                .select {
                    filter { eq("date", dateStr) }
                }
                .decodeSingleOrNull<SupabaseHoroscopeRow>()
        } catch (e: Exception) {
            Log.e("HoroscopeRepository", "fetchHoroscopeRow hatası: $dateStr", e)
            null
        }
    }

    private suspend fun triggerEdgeFunction(): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("${BuildConfig.SUPABASE_URL}/functions/v1/update-horoscopes")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Authorization", "Bearer ${BuildConfig.SUPABASE_ANON_KEY}")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.connectTimeout = 15000
            connection.readTimeout = 30000
            connection.doOutput = true
            val responseCode = connection.responseCode
            connection.disconnect()
            responseCode in 200..299
        } catch (e: Exception) {
            Log.e("HoroscopeRepository", "Edge function tetikleme hatası", e)
            false
        }
    }
}
