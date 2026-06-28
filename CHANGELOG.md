# Changelog

All notable changes, patches, and new features in the AstroYorum project will be documented in this file.
*(AstroYorum projesindeki tüm önemli değişiklikler, yamalar ve yeni özellikler bu dosyada belgelenmektedir.)*

---

## [v1.0.1] - 2026-06-28 (Closed Beta)

### ✨ Features (Yenilikler)
- **Splash Screen added:** A 2-second splash screen containing the logo and legal disclaimer has been added to the app startup.
  *(Uygulama açılışına logo ve yasal uyarı içeren 2 saniyelik Splash Ekranı eklendi.)*
- **Card flip animation:** Added a flip animation for an immersive experience in the Tarot tab; clicking on the cards flips them to reveal their meanings.
  *(Tarot sekmesinde sürükleyici bir deneyim için kart çevirme animasyonu eklendi; kartlara tıklandığında dönerek anlamlarını gösteriyor.)*
- **"Report Bug" button:** Added a button in the Profile tab for users to quickly provide feedback (Email subject: "HATA BİLDİRİMİ").
  *(Profil sekmesine kullanıcıların hızlı geri bildirim verebilmesi için "Hata Bildir" butonu eklendi.)*
- **Version info & Disclaimer:** The current version info (`AstroYorum v1.0.1 (Internal Testing)`) and updated legal disclaimers were added to the bottom of the Profile screen.
  *(Profil ekranının en altına mevcut sürüm bilgisi ve güncellenmiş yasal mevzuat uyarıları eklendi.)*

### 🛠️ Fixes & Improvements (İyileştirmeler ve Hata Düzeltmeleri)
- **Profile Editing System Revamped:** Users can now edit their data directly from the profile tab without resetting all app data and being redirected to the registration screen.
  *(Kullanıcıların bilgilerini güncellemek istediklerinde tüm uygulama verilerinin sıfırlanıp kayıt ekranına atılması engellendi; artık profil sekmesinden veriler doğrudan düzenlenebiliyor.)*
- **Onboarding fix:** Fixed the bug where the birth year couldn't be selected on the initial registration screen.
  *(İlk kayıt ekranındaki doğum yılı seçememe hatası giderildi.)*
- **UI Responsiveness:** Improved the responsiveness of the Profile and Onboarding screens; overflow issues on narrow-screen devices were fixed.
  *(Profil ve Kayıt ekranlarının duyarlılığı iyileştirildi, dar ekranlı cihazlarda taşmalar düzeltildi.)*
- **Dynamic Firebase Data:** Dynamic data (Astrological energies, Moon phases, etc.) is now configured to be fetched entirely from Firebase.
  *(Dinamik veriler tamamen Firebase üzerinden çekilecek şekilde yapılandırıldı.)*
- **AdMob Production IDs:** AdMob ad IDs (App, Banner, Interstitial, Rewarded) were switched from Test IDs to real Production IDs.
  *(AdMob reklam kimlikleri Test ID'lerinden çıkartılarak gerçek Production ID'lerine çevrildi.)*

---

## [v1.0.0] - Initial Release (İlk Yayın)

### ✨ Features (Yenilikler)
- **Daily Horoscopes:** Gemini AI-powered, dynamic astrological readings tailored to the user's name, birth date, and location.
  *(Gemini AI destekli; kullanıcının ismine, doğum tarihine ve bulunduğu şehre özel, dinamik astrolojik yorumlar.)*
- **Tarot Reading:** A random 3-card spread reading system for Love, Career, and General categories.
  *(Aşk, Kariyer ve Genel kategorilerinde rastgele 3 kartlık okuma sistemi.)*
- **Moon Phases:** A tracking module that shows the current phase of the moon and its astrological energy.
  *(O günkü Ay'ın şeklini ve enerjisini gösteren takip modülü.)*
- **Vibecoder Design Language:** A modern UI design featuring a Dark Astro theme, glassmorphism effects, and smooth animations.
  *(Karanlık temalı, cam efektli ve pürüzsüz animasyonlara sahip modern arayüz tasarımı.)*
- **User Registration:** Offline profile management that stores the user's basic info (Name, Birth Date, City) locally via DataStore.
  *(Kullanıcının temel bilgilerini cihazda tutan çevrimdışı profil yönetimi.)*

### ⚙️ Infrastructure (Altyapı)
- **Jetpack Compose:** Developed a fully declarative UI at the presentation layer.
  *(UI katmanında Jetpack Compose kullanılarak tamamen bildirimsel bir arayüz geliştirildi.)*
- **MVVM Architecture:** Adopted Model-View-ViewModel architectural principles.
  *(Mimari olarak MVVM prensipleri benimsendi.)*
- **Firebase Cloud Functions:** AI (Gemini API) logic was routed through a Node.js backend for security, rather than being called directly from the app.
  *(Yapay Zeka mantığı güvenliği sağlamak amacıyla Firebase Cloud Functions üzerinden geçecek şekilde tasarlandı.)*
- **Google AdMob:** Integrated Banner, Interstitial, and Rewarded ads.
  *(Google AdMob reklamları entegre edildi.)*
