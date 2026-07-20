package com.gokcank.astroyorum.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

/**
 * Uygulama içi analiz ve kullanıcı davranışlarını takip etmek için kullanılan yardımcı sınıf.
 */
object AnalyticsHelper {
    
    // Firebase Analytics nesnesine lazy erişim (Uygulama açılışından sonra hazır olur)
    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        Firebase.analytics
    }

    /**
     * Ekran geçişlerini (Screen Views) loglamak için kullanılır.
     * @param screenName Ekranın adı (Örn: "Home", "Tarot")
     */
    fun logScreenView(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    /**
     * Spesifik özellik kullanımlarını loglamak için kullanılır.
     * Kullanıcının özel olarak analiz edilmesini istediği tıklama ve etkinlikleri takip eder.
     * @param featureName Özelliğin adı (Örn: "tarot_single_card", "zodiac_details")
     * @param additionalParams Varsa ekstra parametreler
     */
    fun logFeatureUsage(featureName: String, additionalParams: Bundle? = null) {
        val bundle = additionalParams ?: Bundle()
        bundle.putString("feature_name", featureName)
        firebaseAnalytics.logEvent("feature_usage", bundle)
    }
}
