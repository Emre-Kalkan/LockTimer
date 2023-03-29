package net.emrekalkan.locktimer.presentation

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LockTimerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initAds()
    }

    private fun initAds() {
        MobileAds.initialize(this)
    }
}