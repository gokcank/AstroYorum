import { serve } from "https://deno.land/std@0.168.0/http/server.ts";
import { createClient } from "https://esm.sh/@supabase/supabase-js@2";

const GROQ_API_KEY = Deno.env.get("GROQ_API_KEY")!;
const SUPABASE_URL = Deno.env.get("SUPABASE_URL")!;
const SUPABASE_SERVICE_ROLE_KEY = Deno.env.get("SUPABASE_SERVICE_ROLE_KEY")!;

serve(async (_req) => {
  try {
    const supabase = createClient(SUPABASE_URL, SUPABASE_SERVICE_ROLE_KEY);

    // İstanbul saatine göre bugünün tarihini al
    const now = new Date();
    const istanbulDate = new Date(now.toLocaleString("en-US", { timeZone: "Europe/Istanbul" }));
    const year = istanbulDate.getFullYear();
    const month = String(istanbulDate.getMonth() + 1).padStart(2, "0");
    const day = String(istanbulDate.getDate()).padStart(2, "0");
    const dateStr = `${year}-${month}-${day}`;
    const turkishDate = istanbulDate.toLocaleDateString("tr-TR", {
      day: "numeric",
      month: "long",
      year: "numeric",
    });

    const prompt = `
      Sen uzman ve mistik bir astrologsun. Gökyüzündeki güncel yıldız ve gezegen hareketlerini dikkate alarak ${turkishDate} (${dateStr}) tarihi için 12 burç adına günlük astroloji verilerini hazırla.
      Yorumlar (horoscopes): Her burç için çok detaylı, mistik ve profesyonel bir dille 3 ayrı paragraf yazmalısın. Bu üç paragraf sırasıyla şunlar olmalıdır:
      1. Genel Enerji ve Gökyüzü Gündemi
      2. Aşk ve İlişkiler
      3. Kariyer ve Finans
      Lütfen her paragrafın arasına mutlaka "\\n\\n" ekleyerek metni böl. Sadece metni yaz, paragraf başlıklarını yazmana gerek yok. Düne göre tamamen farklı olsun.
      Skorlar (scores): Her burç için love (aşk), career (kariyer) ve health (sağlık) skorlarını (0-100 arası), luckyNumber (şanslı sayı, 1-99 arası), luckyStone (şanslı taş) ve luckyColor (şanslı renk) belirle.

      Cevabını SADECE aşağıdaki JSON formatında ver, başka hiçbir açıklama ekleme:

      {
        "horoscopes": {
          "Aries": "...", "Taurus": "...", "Gemini": "...", "Cancer": "...",
          "Leo": "...", "Virgo": "...", "Libra": "...", "Scorpio": "...",
          "Sagittarius": "...", "Capricorn": "...", "Aquarius": "...", "Pisces": "..."
        },
        "scores": {
          "Aries": { "love": 85, "career": 70, "health": 90, "luckyNumber": 5, "luckyStone": "Yakut", "luckyColor": "Kırmızı" },
          "Taurus": { "love": 60, "career": 80, "health": 75, "luckyNumber": 12, "luckyStone": "Zümrüt", "luckyColor": "Yeşil" },
          "Gemini": { "love": 90, "career": 65, "health": 80, "luckyNumber": 3, "luckyStone": "Akik", "luckyColor": "Sarı" },
          "Cancer": { "love": 75, "career": 75, "health": 85, "luckyNumber": 2, "luckyStone": "Ay Taşı", "luckyColor": "Beyaz" },
          "Leo": { "love": 80, "career": 90, "health": 70, "luckyNumber": 1, "luckyStone": "Güneş Taşı", "luckyColor": "Turuncu" },
          "Virgo": { "love": 70, "career": 85, "health": 80, "luckyNumber": 5, "luckyStone": "Yeşim", "luckyColor": "Kahverengi" },
          "Libra": { "love": 85, "career": 75, "health": 90, "luckyNumber": 6, "luckyStone": "Pembe Kuvars", "luckyColor": "Pembe" },
          "Scorpio": { "love": 90, "career": 85, "health": 75, "luckyNumber": 8, "luckyStone": "Obsidyen", "luckyColor": "Siyah" },
          "Sagittarius": { "love": 80, "career": 90, "health": 85, "luckyNumber": 9, "luckyStone": "Ametist", "luckyColor": "Mor" },
          "Capricorn": { "love": 75, "career": 95, "health": 80, "luckyNumber": 4, "luckyStone": "Oltu Taşı", "luckyColor": "Gri" },
          "Aquarius": { "love": 85, "career": 80, "health": 90, "luckyNumber": 11, "luckyStone": "Turkuaz", "luckyColor": "Mavi" },
          "Pisces": { "love": 95, "career": 75, "health": 80, "luckyNumber": 7, "luckyStone": "Akuamarin", "luckyColor": "Su Yeşili" }
        }
      }
    `;

    // Groq API çağrısı
    const groqResponse = await fetch("https://api.groq.com/openai/v1/chat/completions", {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${GROQ_API_KEY}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        model: "llama-3.3-70b-versatile",
        messages: [{ role: "user", content: prompt }],
        temperature: 0.8,
        response_format: { type: "json_object" },
      }),
    });

    if (!groqResponse.ok) {
      throw new Error(`Groq API hatası: ${groqResponse.status} ${await groqResponse.text()}`);
    }

    const groqData = await groqResponse.json();
    const responseJson = JSON.parse(groqData.choices[0].message.content);

    // Supabase'e kaydet (UPSERT)
    const { error } = await supabase
      .from("daily_horoscopes")
      .upsert({
        date: dateStr,
        horoscopes: responseJson.horoscopes,
        scores: responseJson.scores,
      });

    if (error) throw error;

    console.log(`✅ ${dateStr} için tüm burç yorumları Supabase'e kaydedildi.`);
    return new Response(JSON.stringify({ success: true, date: dateStr }), {
      headers: { "Content-Type": "application/json" },
    });
  } catch (err) {
    console.error("❌ Hata:", err);
    return new Response(JSON.stringify({ error: String(err) }), {
      status: 500,
      headers: { "Content-Type": "application/json" },
    });
  }
});
