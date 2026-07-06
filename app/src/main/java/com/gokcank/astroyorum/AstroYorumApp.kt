package com.gokcank.astroyorum

import android.app.Application
import com.gokcank.astroyorum.ads.initAdMob
import com.google.android.gms.ads.MobileAds

class AstroYorumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // AdMob başlat
        initAdMob(this)
    }
}
