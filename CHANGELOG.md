# Changelog (Sürüm Notları)

---

## [v1.0.1] - 2026-06-28 (Kapalı Test Sürümü)

### ✨ Yenilikler (Features)
- Uygulama açılışına logo ve yasal uyarı (disclaimer) içeren 2 saniyelik **Splash Ekranı** eklendi.
- Tarot sekmesinde sürükleyici bir deneyim için **kart çevirme (flip) animasyonu** eklendi; kartlara tıklandığında dönerek anlamlarını gösteriyor.
- Profil sekmesine kullanıcıların hızlı geri bildirim verebilmesi için **"Hata Bildir"** butonu eklendi (Mail konu başlığı: "HATA BİLDİRİMİ").
- Profil ekranının en altına mevcut sürüm bilgisi (`AstroYorum v1.0.1 (Internal Testing)`) ve güncellenmiş yasal mevzuat uyarıları (disclaimer) eklendi.

### 🛠️ İyileştirmeler ve Hata Düzeltmeleri (Fixes & Improvements)
- **Profil Düzenleme Sistemi Yenilendi:** Kullanıcıların bilgilerini güncellemek istediklerinde tüm uygulama verilerinin sıfırlanıp kayıt ekranına atılması engellendi; artık profil sekmesinden veriler doğrudan düzenlenebiliyor.
- İlk kayıt (Onboarding) ekranındaki **doğum yılı seçememe hatası** giderildi.
- Profil ve Kayıt (Onboarding) ekranlarının duyarlılığı (responsive yapısı) iyileştirildi, dar ekranlı cihazlarda taşmalar düzeltildi.
- Dinamik veriler (Astroloji enerjileri, Ay durumları vb.) tamamen Firebase üzerinden çekilecek şekilde yapılandırıldı.
- AdMob reklam kimlikleri (App, Banner, Geçiş, Ödüllü) Test ID'lerinden çıkartılarak gerçek Production ID'lerine çevrildi.

---

## [v1.0.0] - İlk Yayın (Internal Test Release)

### ✨ Yenilikler (Features)
- **Günlük Burç Yorumları:** Gemini AI destekli; kullanıcının ismine, doğum tarihine ve bulunduğu şehre özel, dinamik astrolojik yorumlar.
- **Tarot Okuması:** Aşk, Kariyer ve Genel kategorilerinde rastgele 3 kartlık okuma sistemi.
- **Ayın Evreleri:** O günkü Ay'ın şeklini (Yeni Ay, Dolunay vb.) ve enerjisini gösteren takip modülü.
- **Vibecoder Tasarım Dili:** Karanlık (Dark Astro) temalı, cam (glassmorphism) efektli ve pürüzsüz animasyonlara sahip modern arayüz tasarımı.
- **Kullanıcı Kaydı:** Kullanıcının temel bilgilerini (İsim, Doğum Tarihi, Şehir) cihazda (DataStore) tutan çevrimdışı profil yönetimi.

### ⚙️ Altyapı (Infrastructure)
- UI katmanında **Jetpack Compose** kullanılarak tamamen bildirimsel (declarative) bir arayüz geliştirildi.
- Mimari olarak **MVVM (Model-View-ViewModel)** prensipleri benimsendi.
- Yapay Zeka (Gemini API) mantığı doğrudan uygulamanın içinden çağrılmak yerine, güvenliği sağlamak amacıyla **Firebase Cloud Functions (Node.js)** üzerinden geçecek şekilde tasarlandı.
- Google AdMob (Banner, Geçiş ve Ödüllü reklamlar) entegre edildi.
