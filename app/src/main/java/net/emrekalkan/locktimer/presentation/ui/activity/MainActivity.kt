package net.emrekalkan.locktimer.presentation.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import net.emrekalkan.locktimer.presentation.ui.base.BaseActivity
import net.emrekalkan.locktimer.presentation.ui.screen.base.LockTimer
import net.emrekalkan.locktimer.presentation.ui.screen.interstitial.InterstitialAdManager
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InterstitialAdManager.tryLoad(this)
        setContent {
            LockTimerTheme {
                LockTimer()
            }
        }
    }
}