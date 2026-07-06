package com.gokcank.astroyorum.ads

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

// Reklam birimleri (Gerçek yayın)
object AdConfig {
    val BANNER_AD_UNIT_ID = com.gokcank.astroyorum.BuildConfig.ADMOB_BANNER_ID
    val INTERSTITIAL_AD_UNIT_ID = com.gokcank.astroyorum.BuildConfig.ADMOB_INTERSTITIAL_ID
    val REWARDED_AD_UNIT_ID = com.gokcank.astroyorum.BuildConfig.ADMOB_REWARDED_ID
}

object AdManager {
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Geçiş reklamını önceden yükler
    fun loadInterstitialAd(context: Context) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, AdConfig.INTERSTITIAL_AD_UNIT_ID, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                }
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }
            })
    }

    // Geçiş reklamını gösterir (Örn: Burç detayına girince)
    fun showInterstitialAd(activity: Activity, onAdDismissed: () -> Unit) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    onAdDismissed()
                    loadInterstitialAd(activity) // Bir sonrakini yükle
                }
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    interstitialAd = null
                    onAdDismissed()
                }
            }
            interstitialAd?.show(activity)
        } else {
            // Reklam hazır değilse, kesintisiz devam et
            onAdDismissed()
            loadInterstitialAd(activity)
        }
    }

    // Ödüllü reklamı önceden yükler
    fun loadRewardedAd(context: Context) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, AdConfig.REWARDED_AD_UNIT_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                rewardedAd = null
            }
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
            }
        })
    }

    // Ödüllü reklamı gösterir (Örn: 3 Kart Tarot çekimi)
    fun showRewardedAd(activity: Activity, onUserEarnedReward: () -> Unit, onAdDismissedWithoutReward: () -> Unit) {
        if (rewardedAd != null) {
            var rewardEarned = false
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    rewardedAd = null
                    if (rewardEarned) onUserEarnedReward() else onAdDismissedWithoutReward()
                    loadRewardedAd(activity) // Bir sonrakini yükle
                }
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    rewardedAd = null
                    onAdDismissedWithoutReward()
                }
            }
            rewardedAd?.show(activity) { rewardItem ->
                // Kullanıcı ödülü kazandı (reklamı bitirdi)
                rewardEarned = true
            }
        } else {
            // Reklam hazır değilse, ücretsiz ver veya kullanıcıya 'reklam yüklenmedi' uyarısı göster
            // Biz şimdilik sorunsuz UX için ödülü direkt verelim
            onUserEarnedReward()
            loadRewardedAd(activity)
        }
    }
}

fun initAdMob(context: Context) {
    // Geliştirme cihazında test reklamlarının %100 çıkması ve hesabınızın güvende kalması için:
    val testDeviceIds = listOf("DFE5C8B4394FAF4BEAE3B25E6ECDABD8")
    val configuration = com.google.android.gms.ads.RequestConfiguration.Builder()
        .setTestDeviceIds(testDeviceIds)
        .build()
    MobileAds.setRequestConfiguration(configuration)

    MobileAds.initialize(context) {}
    AdManager.loadInterstitialAd(context)
    AdManager.loadRewardedAd(context)
}

@Composable
fun BannerAdView(
    modifier: Modifier = Modifier,
    adUnitId: String = AdConfig.BANNER_AD_UNIT_ID
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                this.adUnitId = adUnitId
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}
