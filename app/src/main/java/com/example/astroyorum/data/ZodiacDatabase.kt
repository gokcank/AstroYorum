package com.example.astroyorum.data

// ─── Burç Veritabanı (Statik, Türkçe) ────────────────────────────────────────
object ZodiacDatabase {

    val signs = listOf(
        ZodiacSign(
            id = 0, name = "Koç", englishName = "Aries", symbol = "♈", emoji = "🐏",
            element = "Ateş", elementEmoji = "🔥", dates = "21 Mar - 19 Nis",
            dailyHoroscope = "Bugün enerjiniz zirveye çıkıyor! Uzun süredir ertelediğiniz o önemli adımı atmak için mükemmel bir gün. Venüs'ün konumu romantik sürprizlere kapı aralıyor. Sabah erken saatlerde aldığınız kararlar gün boyu sizi olumlu etkiler. Mali konularda aceleci davranmayın; özellikle ortaklık tekliflerini iyice değerlendirin.",
            weeklyHoroscope = "Bu hafta Mars'ın gücü yanınızda! Kariyer alanında attığınız adımlar somut meyveler vermeye başlıyor. Salı-Çarşamba günleri özellikle kişisel gelişim için güçlü. İlişkilerinizde dürüst olmak sizi özgürleştirecek. Hafta sonuna doğru finansal haberler sizi güçlendirecek.",
            monthlyHoroscope = "Bu ay yeni döngünün başlangıcı. Güneş doğum haritanızı aydınlatırken, kişisel markalaşma ve kendinizi ifade etme konularında büyük fırsatlar doğuyor. Kariyer kapıları aralanıyor, cesur olun. Aşk hayatında romantik sürprizler bekleniyor. Sağlık açısından aktif kalmak enerji seviyenizi artıracak.",
            loveScore = 85, careerScore = 90, healthScore = 78,
            luckyNumber = 9, luckyColor = "Kırmızı", luckyStone = "Elmas"
        ),
        ZodiacSign(
            id = 1, name = "Boğa", englishName = "Taurus", symbol = "♉", emoji = "🐂",
            element = "Toprak", elementEmoji = "🌍", dates = "20 Nis - 20 May",
            dailyHoroscope = "Sabırlı ve kararlı yaklaşımınız bugün meyvelerini veriyor. Venüs'ün hükmü altında olduğunuz için estetik ve güzellik konularında ilham dolusunuzdur. Öğleden sonra mali konularda iyi haberler alabilirsiniz. Sevdiklerinizle kaliteli zaman geçirmek ruhunuzu besleyecek. Fiziksel aktivite enerjinizi dengelemenize yardımcı olur.",
            weeklyHoroscope = "Bu hafta finansal fırsatlar kapınıza geliyor. Pratik zekanızı kullanarak uzun vadeli planlar yapın. Çarşamba günü iş görüşmeleri veya müzakereler için ideal. Aşk hayatınızda istikrar ve derinlik ön plana çıkıyor. Beslenme düzeninize dikkat ettiğinizde hafta sonuna enerjik ulaşırsınız.",
            monthlyHoroscope = "Venüs evinizi aydınlatırken güzellik, zevk ve lüks temalı fırsatlar bolca geliyor. Maddi konularda akıllı kararlar vermek için mükemmel bir ay. Yaratıcı projeleriniz dikkat çekiyor. Sağlıklı beslenme ve yoga gibi uygulamalar bedeninizi ve zihninizi canlandırır.",
            loveScore = 88, careerScore = 75, healthScore = 82,
            luckyNumber = 6, luckyColor = "Yeşil", luckyStone = "Zümrüt"
        ),
        ZodiacSign(
            id = 2, name = "İkizler", englishName = "Gemini", symbol = "♊", emoji = "👯",
            element = "Hava", elementEmoji = "💨", dates = "21 May - 20 Haz",
            dailyHoroscope = "Zihniniz bugün olağanüstü aktif! Yeni fikirler ve bağlantılar ışık hızıyla geliyor. İletişim konuları ön plana çıkıyor; önemli bir konuşmayı bugüne ertelemeyin. Sosyal medya veya yazışmalar üzerinden beklenmedik fırsatlar doğabilir. Öğleden sonra dikkatiniz dağılabilir, önemli işleri sabaha bırakın.",
            weeklyHoroscope = "Merkür'ün etkisiyle bu hafta iletişim yetenekleriniz parlayor. Sunum, yazarlık veya sosyal görüşmeler için mükemmel bir dönem. Perşembe günü beklenmedik bir haberle sürpriz yaşayabilirsiniz. İkizlerin çift doğası bu hafta hem sosyal hem de kişisel gelişimi destekliyor.",
            monthlyHoroscope = "Bu ay düşüncelerinizi hayata geçirme zamanı. Projeleriniz ivme kazanırken çevrenizdeki insanları da etkiliyorsunuz. Eğitim ve seyahat konuları ön plana çıkıyor. Aşk hayatınızda duygusal derinlik artıyor. Hafif egzersizler ve meditasyon zihninizi dinginleştirecek.",
            loveScore = 72, careerScore = 88, healthScore = 70,
            luckyNumber = 5, luckyColor = "Sarı", luckyStone = "Akik"
        ),
        ZodiacSign(
            id = 3, name = "Yengeç", englishName = "Cancer", symbol = "♋", emoji = "🦀",
            element = "Su", elementEmoji = "🌊", dates = "21 Haz - 22 Tem",
            dailyHoroscope = "Duygusal sezginiz bugün zirvede. Ailenizle veya yakın çevrenizle geçireceğiniz zaman ruhunuzu besleyecek. Ay'ın ev burcu olarak güçlü sezgilerinize güvenin; bir konuda içgüdüleriniz sizi doğru yönlendirecek. İş hayatında duygusal zeka en büyük kozunuz. Akşam saatlerinde dinlendirici ritüeller uygulayın.",
            weeklyHoroscope = "Ev ve aile konularında güzel gelişmeler yaşanıyor. Bir taşınma, yenileme veya aile birlikteliği gündemdeyse bu hafta adım atmak için uygun. Kariyer cephesinde yaratıcı projeleriniz öne çıkıyor. Duygusal sınırlarınızı korumak bu hafta özellikle önemli.",
            monthlyHoroscope = "Dolunay kişisel ilişkilerinizi aydınlatırken, ev ve aile konularında önemli kararlar alacaksınız. Yaratıcılığınız patlama noktasında; sanatsal projeler için mükemmel bir ay. Duygusal iyileşme ve öz-bakım bu ayın ana teması.",
            loveScore = 90, careerScore = 70, healthScore = 80,
            luckyNumber = 2, luckyColor = "Gümüş", luckyStone = "İnci"
        ),
        ZodiacSign(
            id = 4, name = "Aslan", englishName = "Leo", symbol = "♌", emoji = "🦁",
            element = "Ateş", elementEmoji = "🔥", dates = "23 Tem - 22 Ağu",
            dailyHoroscope = "Sahne sizin bugün! Güneş'in hükmü altında olarak doğal karizmanız ve liderlik özellikleriniz parıldıyor. Bir sunum, performans veya önemli bir toplantı için mükemmel bir gün. Sevdiklerinize cömert olun; verdiğiniz sevgi kat kat geri dönecek. Kibrinizi bir kenara bırakırsanız iş birliktelikleri güçlenir.",
            weeklyHoroscope = "Bu hafta tüm gözler sizde! Yaratıcı projeleriniz büyük ilgi görüyor. Aşk hayatında tutkulu anlara hazır olun. Çarşamba günü kariyer alanında önemli bir fırsat kapınızı çalabilir. Kendinize iyi bakın ve hayatınızdaki güzellikleri kutlayın.",
            monthlyHoroscope = "Güneş'in etkisiyle bu ay yaratıcılık ve öz-ifade temalı güçlü bir dönem. Liderlik rolleriniz genişlerken tanınırlığınız artıyor. Romantik ilişkilerinizde tutku ve derinlik var. Kalbinizin sesini dinleyin.",
            loveScore = 82, careerScore = 92, healthScore = 85,
            luckyNumber = 1, luckyColor = "Altın", luckyStone = "Yakut"
        ),
        ZodiacSign(
            id = 5, name = "Başak", englishName = "Virgo", symbol = "♍", emoji = "👩‍⚕️",
            element = "Toprak", elementEmoji = "🌍", dates = "23 Ağu - 22 Eyl",
            dailyHoroscope = "Detay odaklı zihniniz bugün çözülmesi gereken sorunlara mükemmel çözümler buluyor. İş ve sağlık konularında sistematik yaklaşımınız takdir görüyor. Birinin size danışabileceğini hissediyorsanız, yardım etmek için hazır olun. Akşam saatlerinde biraz kendinize zaman ayırın; mükemmeliyetçiliğinizi bir kenara koyun.",
            weeklyHoroscope = "Sağlık ve beslenme konuları bu hafta ön plana çıkıyor. İş hayatında dikkatli analiziniz büyük bir sorunu önlüyor. Hizmet ve yardım temaları güçlü. Perşembe günü yeni bir rutin başlatmak için mükemmel.",
            monthlyHoroscope = "Bu ay analitik güçleriniz zirveye çıkıyor. Sağlıklı alışkanlıklar edinmek için mükemmel bir dönem. İş hayatında ciddi adımlar atılıyor, detaylara verdiğiniz önem fark yaratıyor. İlişkilerinizde öz-ifadeyi geliştirin.",
            loveScore = 75, careerScore = 88, healthScore = 92,
            luckyNumber = 6, luckyColor = "Lacivert", luckyStone = "Safir"
        ),
        ZodiacSign(
            id = 6, name = "Terazi", englishName = "Libra", symbol = "♎", emoji = "⚖️",
            element = "Hava", elementEmoji = "💨", dates = "23 Eyl - 22 Eki",
            dailyHoroscope = "Denge ve uyum arayışınız bugün sizi güçlü kılıyor. Venüs'ün etkisiyle sosyal çevrenizdeki ilişkiler güçleniyor. Bir anlaşmazlıkta arabuluculuk yapabilir ve herkes için kazan-kazan çözümleri bulabilirsiniz. Estetik konularda ilham dolusunuz; sanatsal bir proje başlatmak için ideal bir gün. Karar vermekte güçlük çeksaniz de bugün sezgilerinize güvenin.",
            weeklyHoroscope = "Ortaklıklar ve işbirlikleri bu hafta ön plana çıkıyor. Uzlaşma yeteneğiniz hem iş hem de kişisel yaşamda değer kazanıyor. Venüs'ün etkisiyle aşk hayatında güzel bir dönem başlıyor. Hafta ortasında önemli bir karar için kendinize zaman tanıyın.",
            monthlyHoroscope = "İlişkiler, ortaklıklar ve denge bu ayın ana temaları. Profesyonel anlaşmalar güçleniyor. Güzellik ve estetik konularda yaratıcılığınız patlıyor. Kendinize ve sevdiklerinize zaman ayırın.",
            loveScore = 92, careerScore = 78, healthScore = 76,
            luckyNumber = 7, luckyColor = "Pembe", luckyStone = "Opal"
        ),
        ZodiacSign(
            id = 7, name = "Akrep", englishName = "Scorpio", symbol = "♏", emoji = "🦂",
            element = "Su", elementEmoji = "🌊", dates = "23 Eki - 21 Kas",
            dailyHoroscope = "Derin sezgileriniz bugün gerçeği ortaya çıkarıyor. Gizli tutulan bir şey gün yüzüne çıkabilir ve bu sizi güçlendirecek. Plüton'un etkisiyle dönüşüm ve yenilenme enerjisi güçlü. Duygusal derinliğiniz bugün en büyük gücünüz. Finansal konularda özellikle ortak hesaplar veya miras meseleleri gündemde olabilir.",
            weeklyHoroscope = "Bu hafta gizli bilgiler ve sezgisel farkındalık ön plana çıkıyor. Araştırma gerektiren projeler için mükemmel bir dönem. İlişkilerinizde dürüstlük ve derinlik değer kazanıyor. Hafta sonu spiritüel uygulamalar için ideal.",
            monthlyHoroscope = "Dönüşüm ve yenilenme bu ayın ana teması. Eski kalıpları bırakmak için mükemmel bir dönem. Finansal konularda stratejik hamleler yapılıyor. Spiritüel uygulamalar ruhunuzu besleyecek.",
            loveScore = 88, careerScore = 85, healthScore = 72,
            luckyNumber = 8, luckyColor = "Bordo", luckyStone = "Obsidyen"
        ),
        ZodiacSign(
            id = 8, name = "Yay", englishName = "Sagittarius", symbol = "♐", emoji = "🏹",
            element = "Ateş", elementEmoji = "🔥", dates = "22 Kas - 21 Ara",
            dailyHoroscope = "Özgürlük ve macera ruhu bugün çağırıyor! Jüpiter'in bolluğuyla yeni ufuklara açılma zamanı. Yabancı bir ülkeden veya farklı bir kültürden ilham alabilirsiniz. Felsefi konular zihinsel tatmin sağlıyor. Bir eğitim programına kaydolmak veya uzun vadeli hedeflerinizi netleştirmek için ideal bir gün. Aşırıya kaçma eğiliminize dikkat edin.",
            weeklyHoroscope = "Seyahat, eğitim ve geniş perspektifler bu hafta gündemde. Yeni bir dil öğrenmek veya yabancı bağlantılar kurmak için uygun bir dönem. Cuma günü beklenmedik bir fırsat kapınızı çalabilir. Enerjinizi odaklayarak sonuç almak bu hafta mümkün.",
            monthlyHoroscope = "Jüpiter'in bereketiyle bu ay genişleme ve büyüme zamanı. Seyahat, yayıncılık veya eğitim fırsatları kapınıza geliyor. Felsefi düşünceler yaşamınıza yeni anlam katıyor. Özgürlüğünüzü koruyarak ilişkilerinizi derinleştirin.",
            loveScore = 78, careerScore = 85, healthScore = 80,
            luckyNumber = 3, luckyColor = "Mor", luckyStone = "Ametist"
        ),
        ZodiacSign(
            id = 9, name = "Oğlak", englishName = "Capricorn", symbol = "♑", emoji = "🐐",
            element = "Toprak", elementEmoji = "🌍", dates = "22 Ara - 19 Oca",
            dailyHoroscope = "Disiplin ve azminiz bugün somut başarılar getiriyor. Satürn'ün desteğiyle uzun vadeli planlarınız güçlü temeller üzerine oturuyor. Kariyer alanında otorite figürlerinden olumlu geri bildirim alabilirsiniz. Sabah saatlerinde en önemli işlerinizi tamamlayın. Sosyal hayatınızı biraz canlandırmak iyi gelecek.",
            weeklyHoroscope = "Kariyer ve profesyonel hedefler bu hafta ön plana çıkıyor. Sistematik çalışmanız meyve veriyor. Pazartesi-Salı günleri önemli iş görüşmeleri için ideal. Hafta sonuna doğru dinlenmeyi ihmal etmeyin.",
            monthlyHoroscope = "Bu ay kariyer zirvesine tırmanma zamanı. Satürn'ün etkisiyle uzun vadeli hedefleriniz netleşiyor. Disiplininiz ve kararlılığınız takdir görüyor. Kişisel ilişkilerinizde duygusal olgunluk artıyor.",
            loveScore = 70, careerScore = 95, healthScore = 78,
            luckyNumber = 10, luckyColor = "Kahverengi", luckyStone = "Oniks"
        ),
        ZodiacSign(
            id = 10, name = "Kova", englishName = "Aquarius", symbol = "♒", emoji = "🏺",
            element = "Hava", elementEmoji = "💨", dates = "20 Oca - 18 Şub",
            dailyHoroscope = "Özgün fikirleriniz bugün çevrenizdekileri şaşırtıyor! Uranüs'ün etkisiyle alışılmışın dışında çözümler üretiyorsunuz. Teknoloji ve yenilik konularında ilham dolusunuzdur. İnsanlığa fayda sağlayan bir projeye katkıda bulunmak ruhunuzu canlandırır. Grup dinamiklerinde doğal liderliğiniz öne çıkıyor. Biraz daha duygusal bağlantıya alan açın.",
            weeklyHoroscope = "Teknoloji, yenilik ve sosyal değişim bu hafta gündemde. Arkadaşlıklar ve topluluk bağları güçleniyor. Bir projeye beklenmedik bir ortak katılabilir. Çarşamba günü yaratıcı atılımlar için mükemmel.",
            monthlyHoroscope = "Uranüs'ün etkisiyle devrimsel değişimler için mükemmel bir ay. Teknoloji ve yenilik alanında öne çıkıyorsunuz. Sosyal çevreniz genişliyor. Özgünlüğünüzü kucaklayarak büyük fırsatlar yaratıyorsunuz.",
            loveScore = 75, careerScore = 82, healthScore = 75,
            luckyNumber = 11, luckyColor = "Elektrik Mavisi", luckyStone = "Akuamarin"
        ),
        ZodiacSign(
            id = 11, name = "Balık", englishName = "Pisces", symbol = "♓", emoji = "🐟",
            element = "Su", elementEmoji = "🌊", dates = "19 Şub - 20 Mar",
            dailyHoroscope = "Sezgisel güçleriniz bugün olağanüstü güçlü! Neptün'ün etkisiyle hayal gücünüz ve empati yeteneğiniz zirveye çıkıyor. Sanatsal yaratıcılık için mükemmel bir gün. Rüyalarınıza ve sezgilerinize özellikle dikkat edin; gizli mesajlar taşıyor olabilirler. Sınırlarınızı korumak bu dönemde özellikle önemli. Spiritüel uygulamalar ruhunuzu besleyecek.",
            weeklyHoroscope = "Spiritüel farkındalık ve yaratıcı ilham bu hafta güçlü. Sanat, müzik veya şiir gibi yaratıcı alanlarda büyük ilham geliyor. Cumartesi günü meditasyon veya doğa yürüyüşü için mükemmel. Sezgilerinize güvenin.",
            monthlyHoroscope = "Neptün'ün etkisiyle bu ay spiritüel uyanan ve yaratıcı patlama dönemi. Sanatsal yetenekleriniz parıldıyor. Şifa, empati ve evrensel sevgi temaları güçlü. Sınırlarınızı net belirleyerek ruhsal yolculuğunuza devam edin.",
            loveScore = 87, careerScore = 70, healthScore = 74,
            luckyNumber = 12, luckyColor = "Deniz Mavisi", luckyStone = "Ay Taşı"
        )
    )

    fun getById(id: Int): ZodiacSign = signs[id.coerceIn(0, 11)]
}
