const { onSchedule } = require("firebase-functions/v2/scheduler");
const { logger } = require("firebase-functions");
const admin = require("firebase-admin");
const { GoogleGenerativeAI } = require("@google/generative-ai");
require('dotenv').config();

admin.initializeApp();

// Kullanıcının sağladığı Gemini API Anahtarı (Github'da gizli tutulmalıdır)
const GEMINI_API_KEY = process.env.GEMINI_API_KEY || "YOUR_API_KEY_HERE";

exports.updateDailyHoroscopes = onSchedule(
  {
    schedule: "0 0 * * *", // Her gün saat 00:00'da çalışır
    timeZone: "Europe/Istanbul",
  },
  async (event) => {
    logger.info("Günlük burç yorumu oluşturma görevi başladı.");
    
    try {
      const genAI = new GoogleGenerativeAI(GEMINI_API_KEY);
      const model = genAI.getGenerativeModel({ model: "gemini-pro" });

      const prompt = `
        Sen uzman bir astrologsun. Gökyüzündeki güncel yıldız ve gezegen hareketlerini dikkate alarak bugünün tarihi için 12 burç adına günlük burç yorumları hazırla. 
        Her burç için 3 cümlelik, motive edici ve tamamen Türkçe yorumlar yaz. 
        Lütfen cevabını SADECE aşağıdaki JSON formatında ver, başka hiçbir açıklama ekleme:
        
        {
          "Aries": "Koç burcu yorumu...",
          "Taurus": "Boğa burcu yorumu...",
          "Gemini": "İkizler burcu yorumu...",
          "Cancer": "Yengeç burcu yorumu...",
          "Leo": "Aslan burcu yorumu...",
          "Virgo": "Başak burcu yorumu...",
          "Libra": "Terazi burcu yorumu...",
          "Scorpio": "Akrep burcu yorumu...",
          "Sagittarius": "Yay burcu yorumu...",
          "Capricorn": "Oğlak burcu yorumu...",
          "Aquarius": "Kova burcu yorumu...",
          "Pisces": "Balık burcu yorumu..."
        }
      `;

      const result = await model.generateContent(prompt);
      const responseText = result.response.text();
      
      // JSON formatındaki metni temizleyip ayrıştırıyoruz (API bazen ```json etiketleriyle döndürebilir)
      let cleanJsonText = responseText.replace(/```json/g, "").replace(/```/g, "").trim();
      const horoscopes = JSON.parse(cleanJsonText);

      // Firebase Firestore'a kaydetme işlemi
      const db = admin.firestore();
      
      // Tarih formatı: YYYY-MM-DD
      const dateStr = new Date().toISOString().split("T")[0];
      
      await db.collection("daily_horoscopes").doc(dateStr).set({
        horoscopes: horoscopes,
        createdAt: admin.firestore.FieldValue.serverTimestamp(),
        date: dateStr
      });

      logger.info(`Tüm burç yorumları Firestore'a başarıyla kaydedildi! Tarih: ${dateStr}`);
      
    } catch (error) {
      logger.error("Burç yorumları üretilirken bir hata oluştu: ", error);
    }
  }
);
