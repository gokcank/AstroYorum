package com.example.astroyorum.data

// ─── Tarot Kartları Veritabanı ────────────────────────────────────────────────
object TarotDatabase {

    val majorArcana = listOf(
        TarotCard(0, "Deli", "Major", meaningUpright = "Yeni başlangıçlar, macera, saf potansiyel, özgürlük, sezgi",
            meaningReversed = "Pervasızlık, risk almak, naiflik, kaygısızlık",
            keywords = listOf("Başlangıç", "Macera", "Özgürlük"), emoji = "🃏"),
        TarotCard(1, "Büyücü", "Major", meaningUpright = "Güç, beceri, irade, kaynaklar, manifestasyon",
            meaningReversed = "Manipülasyon, zayıf irade, eksik beceri",
            keywords = listOf("Güç", "Beceri", "Yaratma"), emoji = "🧙"),
        TarotCard(2, "Yüksek Rahibe", "Major", meaningUpright = "Sezgi, bilinçdışı, iç bilgelik, sır, sabır",
            meaningReversed = "Gizlilik, yüzeysellik, bastırılmış bilgi",
            keywords = listOf("Sezgi", "Bilgelik", "Sır"), emoji = "🌙"),
        TarotCard(3, "İmparatoriçe", "Major", meaningUpright = "Bolluk, doğurganlık, güzellik, doğa, annelik",
            meaningReversed = "Yaratıcılık engeli, bağımlılık, ihmal",
            keywords = listOf("Bolluk", "Doğa", "Güzellik"), emoji = "👑"),
        TarotCard(4, "İmparator", "Major", meaningUpright = "Otorite, düzen, baba figürü, liderlik, koruma",
            meaningReversed = "Tiranlık, katılık, aşırı kontrol",
            keywords = listOf("Otorite", "Düzen", "Güç"), emoji = "🏰"),
        TarotCard(5, "Hierophant", "Major", meaningUpright = "Gelenek, din, öğretim, uygunluk, kurumlar",
            meaningReversed = "İsyan, sapma, özgür düşünce",
            keywords = listOf("Gelenek", "Öğretim", "İnanç"), emoji = "⛪"),
        TarotCard(6, "Aşıklar", "Major", meaningUpright = "Sevgi, uyum, değerler, seçimler, birliktelik",
            meaningReversed = "Uyumsuzluk, dengesizlik, yanlış seçim",
            keywords = listOf("Sevgi", "Seçim", "Bağlantı"), emoji = "❤️"),
        TarotCard(7, "Savaş Arabası", "Major", meaningUpright = "Zafer, kontrol, disiplin, başarı, irade",
            meaningReversed = "Kontrol kaybı, saldırganlık, engeller",
            keywords = listOf("Zafer", "Kontrol", "Başarı"), emoji = "⚔️"),
        TarotCard(8, "Güç", "Major", meaningUpright = "İç güç, cesaret, sabır, öz-kontrol, merhamet",
            meaningReversed = "Şüphe, zayıflık, ham enerji",
            keywords = listOf("Güç", "Cesaret", "Sabır"), emoji = "🦁"),
        TarotCard(9, "Ermiş", "Major", meaningUpright = "İç yolculuk, rehberlik, yalnızlık, meditasyon",
            meaningReversed = "İzolasyon, geri çekilme, yalnızlık",
            keywords = listOf("İçe bakış", "Rehberlik", "Yalnızlık"), emoji = "🕯️"),
        TarotCard(10, "Kader Çarkı", "Major", meaningUpright = "Kader, dönüm noktası, şans, döngüler, fırsatlar",
            meaningReversed = "Şanssızlık, direnç, dış kontrol",
            keywords = listOf("Kader", "Döngü", "Şans"), emoji = "☸️"),
        TarotCard(11, "Adalet", "Major", meaningUpright = "Denge, doğruluk, hukuk, adalet, nedensellik",
            meaningReversed = "Haksızlık, sahtekarlık, dengesizlik",
            keywords = listOf("Adalet", "Denge", "Gerçek"), emoji = "⚖️"),
        TarotCard(12, "Asılan Adam", "Major", meaningUpright = "Bekleyiş, teslim olma, iç aydınlanma, kurban",
            meaningReversed = "Zaman kaybı, direniş, erteleme",
            keywords = listOf("Bekleme", "Kabul", "Perspektif"), emoji = "🙃"),
        TarotCard(13, "Ölüm", "Major", meaningUpright = "Son, değişim, dönüşüm, geçiş, yenilenme",
            meaningReversed = "Değişime direnç, durgunluk, çürüme",
            keywords = listOf("Dönüşüm", "Son", "Yenilenme"), emoji = "💀"),
        TarotCard(14, "Ölçülülük", "Major", meaningUpright = "Denge, sabır, uyum, orta yol, iyileşme",
            meaningReversed = "Dengesizlik, aşırılık, hizalanma bozukluğu",
            keywords = listOf("Denge", "Uyum", "Sabır"), emoji = "🕊️"),
        TarotCard(15, "Şeytan", "Major", meaningUpright = "Bağlar, maddecilik, korkular, gölge benlik",
            meaningReversed = "Özgürleşme, farkındalık, bağlardan kurtuluş",
            keywords = listOf("Bağlar", "Korku", "Gölge"), emoji = "😈"),
        TarotCard(16, "Kule", "Major", meaningUpright = "Ani değişim, kaos, yıkım, ifşa, aydınlanma",
            meaningReversed = "İşaret edilmemiş kaos, yapay bir çöküşü önleme",
            keywords = listOf("Kaos", "Yıkım", "İfşa"), emoji = "⚡"),
        TarotCard(17, "Yıldız", "Major", meaningUpright = "Umut, inanç, ilham, tazelenme, yenileme",
            meaningReversed = "Umutsuzluk, imansızlık, kendine güvensizlik",
            keywords = listOf("Umut", "İlham", "İnanç"), emoji = "⭐"),
        TarotCard(18, "Ay", "Major", meaningUpright = "Yanılsama, korku, bilinçdışı, sezgi, belirsizlik",
            meaningReversed = "Kafa karışıklığı, özgürleşme, gerçeğin açığa çıkması",
            keywords = listOf("Yanılsama", "Sezgi", "Bilinçdışı"), emoji = "🌕"),
        TarotCard(19, "Güneş", "Major", meaningUpright = "Mutluluk, başarı, neşe, canlılık, aydınlık",
            meaningReversed = "İç neşenin yitirilmesi, aşırı iyimserlik",
            keywords = listOf("Mutluluk", "Başarı", "Neşe"), emoji = "☀️"),
        TarotCard(20, "Yargı", "Major", meaningUpright = "Uyanış, yeniden doğuş, değerlendirme, çağrı",
            meaningReversed = "Şüphe, özeleştiri, sorgulamama",
            keywords = listOf("Uyanış", "Yeniden doğuş", "Değerlendirme"), emoji = "🎺"),
        TarotCard(21, "Dünya", "Major", meaningUpright = "Tamamlanma, bütünleşme, başarı, seyahat, bütünlük",
            meaningReversed = "Tamamlanmamış döngü, eksik bütünlük",
            keywords = listOf("Tamamlanma", "Başarı", "Bütünlük"), emoji = "🌍")
    )

    // Minor Arcana - Kupalar (Duygular, ilişkiler)
    val minorKupalar = listOf(
        TarotCard(22, "Kupalar Ası", "Minor", "Kupalar", "Yeni duygular, aşk başlangıcı, sezgi, yaratıcılık", "Duygusal blok, boşluk", listOf("Aşk", "Başlangıç", "Sezgi"), "💧"),
        TarotCard(23, "Kupalar İkilisi", "Minor", "Kupalar", "Birlik, ortaklık, karşılıklı çekim, aşk", "Kopukluk, uyumsuzluk", listOf("Ortaklık", "Aşk", "Birlik"), "💑"),
        TarotCard(24, "Kupalar Üçlüsü", "Minor", "Kupalar", "Kutlama, dostluk, yaratıcılık, topluluk", "Bağımlılık, aşırılık", listOf("Kutlama", "Dostluk", "Sevinç"), "🥂"),
        TarotCard(25, "Kupalar Dördüsü", "Minor", "Kupalar", "Hayal kırıklığı, ilgisizlik, yeniden değerlendirme", "Fırsatı kaçırma, motivasyon", listOf("Hayal kırıklığı", "Meditasyon", "Dönüş"), "😔"),
        TarotCard(26, "Kupalar Beşlisi", "Minor", "Kupalar", "Kayıp, hayal kırıklığı, yası tutmak, pişmanlık", "Kabul, ilerlemek, af", listOf("Kayıp", "Yas", "Pişmanlık"), "😢"),
        TarotCard(27, "Kupalar Altılısı", "Minor", "Kupalar", "Nostalji, mutluluk, çocukluk anıları, yeniden kavuşma", "Geçmişte kalmak, gerçekçi olmayan", listOf("Nostalji", "Çocukluk", "Mutluluk"), "🌸"),
        TarotCard(28, "Kupalar Yedilisi", "Minor", "Kupalar", "Seçenekler, hayal kurma, yanılsama, fantezi", "Gerçeklik, netlik, kararlar", listOf("Seçim", "Hayal", "Yanılsama"), "✨"),
        TarotCard(29, "Kupalar Sekizlisi", "Minor", "Kupalar", "Terk etmek, hayal kırıklığı, yeni yön arama", "Kaçış, bırakamama, umursamazlık", listOf("Bırakmak", "Yön değiştirme", "Arayış"), "🚶"),
        TarotCard(30, "Kupalar Dokuzlusu", "Minor", "Kupalar", "Dilek kartı, tatmin, refah, mutluluk, şükran", "Tatminsizlik, maddecilik", listOf("Dilek", "Tatmin", "Refah"), "⭐"),
        TarotCard(31, "Kupalar Onlusu", "Minor", "Kupalar", "İdeal mutluluk, aile refahı, doyum, huzur", "Gerçekçi olmayan beklentiler", listOf("Mutluluk", "Aile", "Huzur"), "🏡")
    )

    fun getDailyCard(): TarotCard {
        val allCards = majorArcana + minorKupalar
        val today = java.util.Calendar.getInstance()
        val seed = today.get(java.util.Calendar.DAY_OF_YEAR) + today.get(java.util.Calendar.YEAR)
        return allCards[seed % allCards.size]
    }

    fun getThreeCardSpread(): Triple<TarotCard, TarotCard, TarotCard> {
        val allCards = majorArcana + minorKupalar
        val today = java.util.Calendar.getInstance()
        val seed = today.get(java.util.Calendar.DAY_OF_YEAR) + today.get(java.util.Calendar.YEAR)
        return Triple(
            allCards[seed % allCards.size],
            allCards[(seed + 7) % allCards.size],
            allCards[(seed + 13) % allCards.size]
        )
    }
}
