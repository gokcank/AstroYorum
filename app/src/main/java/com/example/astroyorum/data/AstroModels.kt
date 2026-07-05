package com.example.astroyorum.data

import java.util.Calendar

// ─── Burç Modeli ───────────────────────────────────────────────────────────────
data class ZodiacSign(
    val id: Int,
    val name: String,
    val englishName: String,
    val symbol: String,
    val emoji: String,
    val element: String,
    val elementEmoji: String,
    val dates: String,
    val dailyHoroscope: String = "",
    val weeklyHoroscope: String = "",
    val monthlyHoroscope: String = "",
    val loveScore: Int = 0,
    val careerScore: Int = 0,
    val healthScore: Int = 0,
    val luckyNumber: Int = 0,
    val luckyColor: String = "",
    val luckyStone: String = ""
)

// ─── Tarot Kartı ───────────────────────────────────────────────────────────────
data class TarotCard(
    val id: Int,
    val name: String,
    val arcana: String, // "Major" veya "Minor"
    val suit: String = "", // Cups, Swords, Wands, Pentacles (minor için)
    val meaningUpright: String,
    val meaningReversed: String,
    val keywords: List<String>,
    val emoji: String
)

// ─── Ay Fazı ───────────────────────────────────────────────────────────────────
data class MoonPhase(
    val date: String,
    val phaseName: String,
    val phaseEmoji: String,
    val illumination: Float, // 0.0 - 1.0
    val zodiacSign: String,
    val ritual: String = "",
    val energy: String = ""
)

// ─── Doğum Haritası ─────────────────────────────────────────────────────────
data class BirthChartData(
    val sunSign: String,
    val moonSign: String,
    val risingSign: String,
    val mercury: String,
    val venus: String,
    val mars: String,
    val jupiter: String,
    val saturn: String,
    val uranus: String,
    val neptune: String,
    val pluto: String
)

// ─── Dinamik Günlük Veriler ────────────────────────────────────────────────
data class ZodiacScores(
    val love: Int = 50,
    val career: Int = 50,
    val health: Int = 50,
    val luckyNumber: Int = 0,
    val luckyStone: String = "",
    val luckyColor: String = ""
)

data class DailyAstroData(
    val horoscopes: Map<String, String> = emptyMap(),
    val scores: Map<String, ZodiacScores> = emptyMap(),
    val moonPhase: MoonPhase? = null
)

// ─── Kullanıcı Profili ─────────────────────────────────────────────────────
data class UserProfile(
    val name: String = "",
    val birthDay: Int = 1,
    val birthMonth: Int = 1,
    val birthYear: Int = 1990,
    val birthHour: Int = 12,
    val birthMinute: Int = 0,
    val birthCity: String = "",
    val gender: String = "Belirtmek İstemiyorum",
    val zodiacSignId: Int = 0
)

// ─── Yardımcı: Doğum tarihinden burç hesapla ─────────────────────────────────
fun calculateZodiacId(day: Int, month: Int): Int {
    return when {
        (month == 3 && day >= 21) || (month == 4 && day <= 19) -> 0  // Koç
        (month == 4 && day >= 20) || (month == 5 && day <= 20) -> 1  // Boğa
        (month == 5 && day >= 21) || (month == 6 && day <= 20) -> 2  // İkizler
        (month == 6 && day >= 21) || (month == 7 && day <= 22) -> 3  // Yengeç
        (month == 7 && day >= 23) || (month == 8 && day <= 22) -> 4  // Aslan
        (month == 8 && day >= 23) || (month == 9 && day <= 22) -> 5  // Başak
        (month == 9 && day >= 23) || (month == 10 && day <= 22) -> 6 // Terazi
        (month == 10 && day >= 23) || (month == 11 && day <= 21) -> 7 // Akrep
        (month == 11 && day >= 22) || (month == 12 && day <= 21) -> 8 // Yay
        (month == 12 && day >= 22) || (month == 1 && day <= 19) -> 9  // Oğlak
        (month == 1 && day >= 20) || (month == 2 && day <= 18) -> 10  // Kova
        else -> 11 // Balık
    }
}

fun todayMoonPhase(): MoonPhase {
    val cal = Calendar.getInstance()
    val day = cal.get(Calendar.DAY_OF_MONTH)
    // Basit hesaplama (gerçek uygulamada swiss ephemeris kullanılır)
    return when (day % 8) {
        0 -> MoonPhase(
            "Bugün", "Yeniay", "🌑", 0.0f, "Koç",
            "Yeni başlangıçlar için güçlü bir zaman. Niyetlerinizi belirleyin.",
            "Taze enerji, yeni fikirler"
        )
        1, 2 -> MoonPhase(
            "Bugün", "Hilal", "🌒", 0.25f, "Boğa",
            "Tohumlarınızı atın, adımlarınızı planlayın.",
            "Büyüme, planlama"
        )
        3 -> MoonPhase(
            "Bugün", "İlk Dördün", "🌓", 0.5f, "İkizler",
            "Zorluklarla yüzleşme ve eylem zamanı.",
            "Eylem, karar"
        )
        4, 5 -> MoonPhase(
            "Bugün", "Şişan Ay", "🌔", 0.75f, "Aslan",
            "İstekleriniz güçleniyor, enerjinizi odaklayın.",
            "Yoğun enerji, tutku"
        )
        6 -> MoonPhase(
            "Bugün", "Dolunay", "🌕", 1.0f, "Akrep",
            "Hasat zamanı. Şükran ritüeli yapın, neyi salmak istediğinizi düşünün.",
            "Zirve, açığa çıkma, şükran"
        )
        else -> MoonPhase(
            "Bugün", "Son Dördün", "🌗", 0.5f, "Kova",
            "Bırakma ve temizlenme zamanı.",
            "Salıverme, temizlik"
        )
    }
}
