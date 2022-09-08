package net.emrekalkan.locktimer.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import net.emrekalkan.locktimer.presentation.ui.screen.base.LockTimer
import net.emrekalkan.locktimer.presentation.ui.screen.onboarding.LockScreenAdminReceiver
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val lockScreenAdminReceiver by lazy { LockScreenAdminReceiver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockTimerTheme {
                LockTimer()
            }
        }
    }
}