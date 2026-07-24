# AstroYorum — Proje Özel Kuralları (Project-Scoped AGENTS.md)

Bu dosya, **AstroYorum** projesinde çalışan tüm yapay zeka asistanları için özel proje içi kuralları içerir.

---

## 1. Katı Onay ve Çalışma Prensipleri (Strict Approval Workflow)

- **Plana Sadakat ve Açık Onay:** Kullanıcı bir plan üzerinde değişiklik veya ince ayar istediğinde (örn: *"Profil ekranına da versiyon yaz"*), bu talimat kesinlikle **"planı uygulama veya koda geçme onayı"** olarak YORUMLANAMAZ. Yapay zeka kodu değiştirmek için kullanıcının açıkça *"uygula"*, *"başla"*, *"onaylıyorum"* veya *"çalıştır"* demesini beklemek zorundadır.
- **Git Komutu Sınırları:** Kullanıcı o turda spesifik olarak bir Git komutu vermediği sürece yapay zeka kesinlikle `git add`, `git commit` veya `git push` çalıştıramaz. Kod planının onaylanması otomatik olarak Git izni VERMEZ.

---

## 2. Proje Güvenlik ve Mimari Kuralları (AstroYorum Standards)

- **Gizli Bilgiler:** Supabase anahtarları, AdMob yayıncı/birim kimlikleri, keystore şifreleri ve gizli veriler asla Kotlin veya Gradle dosyalarına yazılmaz; `local.properties` dosyasından dinamik olarak okunur.
- **AdMob Güvenliği:** Canlı AdMob kimlikleri ile geliştirme ve test yapılırken dikkatli olunmalı, kendi cihazında test cihazı ID'leri tanımlanmalıdır.
- **İletişim Dili:** Raporlar, anlatımlar ve açıklamalar her zaman **Türkçe** olmalıdır.
