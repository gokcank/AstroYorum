# Changelog

All notable changes, patches, and new features in the AstroYorum project will be documented in this file.

*(AstroYorum projesindeki tüm önemli değişiklikler, yamalar ve yeni özellikler bu dosyada belgelenmektedir.)*

---

## [v1.0.2] - 2026-07-05 (Closed Beta)

### 🇬🇧 English

#### ✨ Features & Visuals
- **Dark Mode Arrived:** You can now choose your preferred theme (Light/Dark/System) directly from the Profile tab.
- **Visual Upgrades:** Color palettes across all screens have been updated with premium and mystical tones.

#### ⚙️ Infrastructure
- **Supabase Migration:** The backend database and edge functions have been entirely migrated from Firebase to Supabase for improved performance and structure.

#### 🛠️ Fixes & Improvements
- **Text Visibility Fix:** Resolved the issue where text fields (like Name, Birthdate) were unreadable when switching to the dark theme.
### 🇹🇷 Türkçe

#### ✨ Yenilikler ve Görsellik
- **Karanlık Mod Eklendi:** Profilinizden temanızı (Açık/Koyu/Sistem) dilediğiniz gibi seçebilirsiniz.
- **Görsel Yenilikler:** Tüm ekranlardaki renk paletleri daha premium ve mistik tonlarla güncellendi.

#### ⚙️ Altyapı
- **Supabase Geçişi:** Daha iyi performans ve yapısal bütünlük için arka plan veritabanı ve Edge Function işlemleri Firebase'den tamamen Supabase'e taşındı.

#### 🛠️ İyileştirmeler ve Hata Düzeltmeleri
- **Metin Görünürlüğü Hatası:** Koyu temaya geçildiğinde metin kutularındaki yazıların okunmama sorunu tamamen çözüldü.

---

## [v1.0.1] - 2026-06-28 (Closed Beta)

### 🇬🇧 English

#### ✨ Features
- **Splash Screen:** A 2-second splash screen containing the logo and legal disclaimer has been added to the app startup.
- **Card Flip Animation:** Added a flip animation for an immersive experience in the Tarot tab; clicking on the cards flips them to reveal their meanings.
- **"Report Bug" Button:** Added a button in the Profile tab for users to quickly provide feedback (Email subject: "HATA BİLDİRİMİ").
- **Version Info & Disclaimer:** The current version info (`AstroYorum v1.0.1 (Internal Testing)`) and updated legal disclaimers were added to the bottom of the Profile screen.

#### 🛠️ Fixes & Improvements
- **Profile Editing System Revamped:** Users can now edit their data directly from the profile tab without resetting all app data and being redirected to the registration screen.
- **Onboarding Fix:** Fixed the bug where the birth year couldn't be selected on the initial registration screen.
- **UI Responsiveness:** Improved the responsiveness of the Profile and Onboarding screens; overflow issues on narrow-screen devices were fixed.
- **Dynamic Firebase Data:** Dynamic data (Astrological energies, Moon phases, etc.) is now configured to be fetched entirely from Firebase.
- **AdMob Production IDs:** AdMob ad IDs (App, Banner, Interstitial, Rewarded) were switched from Test IDs to real Production IDs.

---

### 🇹🇷 Türkçe

#### ✨ Yenilikler
- **Splash Ekranı:** Uygulama açılışına logo ve yasal uyarı içeren 2 saniyelik Splash Ekranı eklendi.
- **Kart Çevirme Animasyonu:** Tarot sekmesinde sürükleyici bir deneyim için animasyon eklendi; kartlara tıklandığında dönerek anlamlarını gösteriyor.
- **"Hata Bildir" Butonu:** Profil sekmesine kullanıcıların hızlı geri bildirim verebilmesi için bir buton eklendi.
- **Sürüm Bilgisi ve Yasal Uyarı:** Profil ekranının en altına mevcut sürüm bilgisi ve güncellenmiş yasal mevzuat uyarıları eklendi.

#### 🛠️ İyileştirmeler ve Hata Düzeltmeleri
- **Profil Düzenleme Sistemi Yenilendi:** Kullanıcıların bilgilerini güncellemek istediklerinde tüm uygulama verilerinin sıfırlanıp kayıt ekranına atılması engellendi; artık profil sekmesinden veriler doğrudan düzenlenebiliyor.
- **Kayıt (Onboarding) Düzeltmesi:** İlk kayıt ekranındaki doğum yılı seçememe hatası giderildi.
- **Arayüz Duyarlılığı:** Profil ve Kayıt ekranlarının duyarlılığı iyileştirildi, dar ekranlı cihazlarda taşmalar düzeltildi.
- **Dinamik Firebase Verileri:** Dinamik veriler tamamen Firebase üzerinden çekilecek şekilde yapılandırıldı.
- **AdMob Canlı Kimlikleri:** AdMob reklam kimlikleri Test ID'lerinden çıkartılarak gerçek Production ID'lerine çevrildi.

---

## [v1.0.0] - Initial Release (İlk Yayın)

### 🇬🇧 English

#### ✨ Features
- **Daily Horoscopes:** Gemini AI-powered, dynamic astrological readings tailored to the user's name, birth date, and location.
- **Tarot Reading:** A random 3-card spread reading system for Love, Career, and General categories.
- **Moon Phases:** A tracking module that shows the current phase of the moon and its astrological energy.
- **Vibecoder Design Language:** A modern UI design featuring a Dark Astro theme, glassmorphism effects, and smooth animations.
- **User Registration:** Offline profile management that stores the user's basic info locally via DataStore.

#### ⚙️ Infrastructure
- **Jetpack Compose:** Developed a fully declarative UI at the presentation layer.
- **MVVM Architecture:** Adopted Model-View-ViewModel architectural principles.
- **Firebase Cloud Functions:** AI logic was routed through a Node.js backend for security, rather than being called directly from the app.
- **Google AdMob:** Integrated Banner, Interstitial, and Rewarded ads.

---

### 🇹🇷 Türkçe

#### ✨ Yenilikler
- **Günlük Burç Yorumları:** Gemini AI destekli; kullanıcının ismine, doğum tarihine ve bulunduğu şehre özel, dinamik astrolojik yorumlar.
- **Tarot Okuması:** Aşk, Kariyer ve Genel kategorilerinde rastgele 3 kartlık okuma sistemi.
- **Ayın Evreleri:** O günkü Ay'ın şeklini ve enerjisini gösteren takip modülü.
- **Vibecoder Tasarım Dili:** Karanlık temalı, cam efektli ve pürüzsüz animasyonlara sahip modern arayüz tasarımı.
- **Kullanıcı Kaydı:** Kullanıcının temel bilgilerini cihazda tutan çevrimdışı profil yönetimi.

#### ⚙️ Altyapı
- **Jetpack Compose:** UI katmanında tamamen bildirimsel bir arayüz geliştirildi.
- **MVVM Mimarisi:** Mimari olarak MVVM prensipleri benimsendi.
- **Firebase Cloud Functions:** Yapay Zeka mantığı güvenliği sağlamak amacıyla arka plan sunucusu üzerinden geçecek şekilde tasarlandı.
- **Google AdMob:** Banner, Geçiş ve Ödüllü reklamlar entegre edildi.
