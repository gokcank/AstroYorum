package com.gokcank.astroyorum

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AstroYorumE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun completeE2EUserJourney() {
        // --- 1. ONBOARDING (KARŞILAMA) TESTİ ---
        // Splash screen may take a moment to disappear. 
        // We wait for the Onboarding text to appear.
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("Seni nasıl çağıralım?").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Adım 1: İsim girişi
        composeTestRule.onNodeWithText("Adın").performTextInput("Emülatör Test")
        composeTestRule.onNodeWithText("Devam →").performClick()
        
        // Adım 2: Doğum Tarihi girişi (Varsayılan değerlerle devam edelim)
        composeTestRule.onNodeWithText("Devam →").performClick()
        
        // Adım 3: Şehir ve Cinsiyet
        composeTestRule.onNodeWithText("İstanbul").performClick()
        composeTestRule.onNodeWithText("Belirtmek İstemiyorum").performClick()
        composeTestRule.onNodeWithText("Yolculuğu Başlat ✨").performClick()

        // --- 2. ANA SAYFA VE NAVIGATION (GEZİNME) TESTİ ---
        // Ana sayfaya geldiğimizi "Merhaba" metni üzerinden doğruluyoruz.
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("Merhaba, Emülatör Test ✨").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Merhaba, Emülatör Test ✨").assertExists()
        
        // --- 3. BURÇLAR EKRANI TESTİ ---
        // Bottom Navigation'dan Burçlar sekmesine tıklıyoruz.
        composeTestRule.onNodeWithText("Burçlar").performClick()
        // Burç listesinin geldiğini (örneğin "Koç" burcunun ekranda olduğunu) doğruluyoruz.
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("Koç").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Koç").assertExists()

        // --- 4. TAROT EKRANI TESTİ ---
        composeTestRule.onNodeWithText("Tarot").performClick()
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("Günün Mesajı").fetchSemanticsNodes().isNotEmpty()
        }
        // "Kartına Odaklan" yazılı kapalı karta tıklıyoruz
        composeTestRule.onNodeWithText("Kartına\nOdaklan").performClick()

        // --- 5. AY EVRELERİ EKRANI TESTİ ---
        composeTestRule.onNodeWithText("Ay").performClick()
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("🌙 Ay Takvimi").fetchSemanticsNodes().isNotEmpty()
        }
        
        // --- 6. PROFİL EKRANI VE GÜNCELLEME TESTİ ---
        composeTestRule.onNodeWithText("Profil").performClick()
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("👤 Profilim").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Profili Düzenle dialogunu açıyoruz
        composeTestRule.onNodeWithText("✏️ Profili Düzenle").performClick()
        
        // İsmi temizleyip yeni isim yazıyoruz
        val nameFieldNode = composeTestRule.onNodeWithText("Adın")
        nameFieldNode.performTextClearance()
        nameFieldNode.performTextInput("Astro Uzman")
        
        // Kaydet butonuna tıklıyoruz
        composeTestRule.onNodeWithText("Kaydet").performClick()
        
        // Ana Sayfaya dönüp ismin değiştiğini teyit ediyoruz
        composeTestRule.onNodeWithText("Ana Sayfa").performClick()
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("Merhaba, Astro Uzman ✨").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Merhaba, Astro Uzman ✨").assertExists()
    }
}
