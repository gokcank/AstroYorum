package com.example.astroyorum

import android.app.Application
import com.example.astroyorum.ads.initAdMob
import com.google.android.gms.ads.MobileAds

class AstroYorumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // AdMob başlat
        initAdMob(this)
    }
}
