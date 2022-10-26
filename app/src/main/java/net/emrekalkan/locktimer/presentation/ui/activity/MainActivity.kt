package net.emrekalkan.locktimer.presentation.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import dagger.hilt.android.AndroidEntryPoint
import net.emrekalkan.locktimer.presentation.base.BaseActivity
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down.CountDownAlarmScheduler
import net.emrekalkan.locktimer.presentation.ui.screen.base.LockTimer
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme
import javax.inject.Inject

val LocalCountDownAlarmSchedulerProvider = staticCompositionLocalOf<CountDownAlarmScheduler> { error("No scheduler provided.") }

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var countDownAlarmScheduler: CountDownAlarmScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalCountDownAlarmSchedulerProvider provides countDownAlarmScheduler) {
                LockTimerTheme {
                    LockTimer()
                }
            }
        }
    }
}