package com.example.astroyorum.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HoroscopeRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getDailyAstroData(): DailyAstroData? {
        return try {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            
            val docSnapshot = firestore.collection("daily_horoscopes")
                .document(dateStr)
                .get()
                .await()

            if (docSnapshot.exists()) {
                val horoscopes = docSnapshot.get("horoscopes") as? Map<String, String> ?: emptyMap()
                val scoresRaw = docSnapshot.get("scores") as? Map<String, Map<String, Long>> ?: emptyMap()
                val moonPhaseMap = docSnapshot.get("moon_phase") as? Map<String, Any>

                val scores = scoresRaw.mapValues { (_, value) ->
                    ZodiacScores(
                        love = (value["love"] ?: 50L).toInt(),
                        career = (value["career"] ?: 50L).toInt(),
                        health = (value["health"] ?: 50L).toInt()
                    )
                }

                val moonPhase = moonPhaseMap?.let {
                    MoonPhase(
                        date = it["date"] as? String ?: dateStr,
                        phaseName = it["phaseName"] as? String ?: "",
                        phaseEmoji = it["phaseEmoji"] as? String ?: "",
                        illumination = (it["illumination"] as? Double)?.toFloat() ?: 0f,
                        zodiacSign = it["zodiacSign"] as? String ?: "",
                        ritual = it["ritual"] as? String ?: "",
                        energy = it["energy"] as? String ?: ""
                    )
                }

                DailyAstroData(horoscopes, scores, moonPhase)
            } else {
                // Veritabanı boşsa sahte verilerle doldur
                val mockHoroscopes = mapOf(
                    "Aries" to "Bugün Koç burçları için harika bir gün! Enerjiniz yüksek.",
                    "Taurus" to "Sevgili Boğa, finansal konularda sürpriz gelişmeler yaşayabilirsiniz.",
                    "Gemini" to "İkizler, iletişim becerileriniz bugün tavan yapıyor.",
                    "Cancer" to "Duygusal yengeçler, ailenizle vakit geçirmek iyi gelecek.",
                    "Leo" to "Aslanlar sahneye çıkmaya hazır olun! Liderliğiniz parlıyor.",
                    "Virgo" to "Başak burçları, detaylara olan takıntınız kriz çözecek.",
                    "Libra" to "Teraziler, uyum arayışınız sonuç veriyor.",
                    "Scorpio" to "Gizemli Akrepler, sezgileriniz inanılmaz kuvvetli.",
                    "Sagittarius" to "Özgür ruhlu Yaylar, yeni maceralara atılmak için harika bir gün.",
                    "Capricorn" to "Oğlaklar, disiplinli çalışmanızın karşılığını alıyorsunuz.",
                    "Aquarius" to "Yenilikçi Kovalar, farklı fikirleriniz ilham verecek.",
                    "Pisces" to "Duyarlı Balıklar, bugün hayal gücünüz sınır tanımıyor."
                )

                val mockScores = mockHoroscopes.keys.associateWith { ZodiacScores(80, 85, 75) }
                val mockMoon = todayMoonPhase() // Eskisi gibi fallback

                val dbData = mapOf(
                    "horoscopes" to mockHoroscopes,
                    "scores" to mockScores,
                    "moon_phase" to mockMoon
                )
                
                // Firestore'a kaydet
                firestore.collection("daily_horoscopes")
                    .document(dateStr)
                    .set(dbData)
                    .await()
                    
                DailyAstroData(mockHoroscopes, mockScores, mockMoon)
            }
        } catch (e: Exception) {
            Log.e("HoroscopeRepository", "Error fetching astro data", e)
            null
        }
    }
}
