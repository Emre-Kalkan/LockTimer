package net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DisableWifiAction @Inject constructor(
    @ApplicationContext val context: Context
) : TimerAction {

    @Suppress("DEPRECATION")
    override fun perform() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) return

        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = false
        }
    }
}