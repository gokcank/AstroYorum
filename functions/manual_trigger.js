const admin = require("firebase-admin");
const { GoogleGenerativeAI } = require("@google/generative-ai");
require('dotenv').config();

// You might need a service account key or ADC. 
// If run via firebase shell or with application default credentials, it should work.
admin.initializeApp({
  projectId: "astroyorum-19"
});

const GEMINI_API_KEY = process.env.GEMINI_API_KEY || "YOUR_API_KEY_HERE";

async function populateHoroscope() {
  console.log("Triggering manual horoscope generation...");
  try {
    const horoscopes = {
      "Aries": "Bugün enerjiniz çok yüksek, yeni projelere başlamak için harika bir gün. Çevrenizdeki insanlara liderlik edebilirsiniz.",
      "Taurus": "Maddi konularda güzel gelişmeler olabilir. Sabırlı olmaya devam edin, yatırımlarınızın meyvesini toplayacaksınız.",
      "Gemini": "İletişim yeteneğiniz bugün zirvede. Çözülmeyen sorunları konuşarak tatlıya bağlayabilirsiniz.",
      "Cancer": "Duygusal olarak yoğun bir gün geçirebilirsiniz. Ailenizle ve sevdiklerinizle vakit geçirmek size iyi gelecek.",
      "Leo": "Dikkatler sizin üzerinizde olacak. Yaratıcılığınızı sergilemek için karşınıza çıkan fırsatları değerlendirin.",
      "Virgo": "Detaylara her zamankinden fazla dikkat edebilirsiniz. İş hayatınızda mükemmeliyetçiliğiniz takdir toplayacak.",
      "Libra": "İlişkilerinizde dengeyi bulduğunuz bir gün. Sanatsal faaliyetlere katılmak ruhunuzu besleyebilir.",
      "Scorpio": "Sezgileriniz bugün çok güçlü. İç sesinizi dinleyin, sizi doğru kararlara yönlendirecektir.",
      "Sagittarius": "Maceracı ruhunuz ön planda. Yeni ufuklara yelken açmak ve yeni şeyler öğrenmek için harika bir gün.",
      "Capricorn": "Kariyerinizle ilgili önemli adımlar atabilirsiniz. Disiplinli çalışmanızın karşılığını alma vakti yaklaşıyor.",
      "Aquarius": "Toplumsal konulara duyarlılığınız artabilir. Arkadaş çevrenizle birlikte faydalı projeler üretebilirsiniz.",
      "Pisces": "Hayal gücünüz sınır tanımıyor. Sanatsal ve yaratıcı çalışmalarda bugün çok başarılı olabilirsiniz."
    };

    const db = admin.firestore();
    const dateStr = new Date().toISOString().split("T")[0];
    
    await db.collection("daily_horoscopes").doc(dateStr).set({
      horoscopes: horoscopes,
      createdAt: admin.firestore.FieldValue.serverTimestamp(),
      date: dateStr
    });

    console.log(`Tüm burç yorumları Firestore'a başarıyla kaydedildi! Tarih: ${dateStr}`);
    process.exit(0);
  } catch (error) {
    console.error("Burç yorumları üretilirken bir hata oluştu: ", error);
    process.exit(1);
  }
}

populateHoroscope();
