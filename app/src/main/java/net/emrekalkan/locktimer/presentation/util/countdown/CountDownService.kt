package net.emrekalkan.locktimer.presentation.util.countdown

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions.TimerActionPerformer
import net.emrekalkan.locktimer.presentation.util.countdown.CountDownNotificationBuilder.Companion.NOTIFICATION_ID
import javax.inject.Inject

@AndroidEntryPoint
class CountDownService : LifecycleService() {

    @Inject
    lateinit var countDownNotificationBuilder: CountDownNotificationBuilder

    @Inject
    lateinit var countDownTimer: CountDownTimer

    @Inject
    lateinit var timerActionPerformer: TimerActionPerformer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (val action = intent?.getParcelableExtra<CountDownAction>(KEY_ACTION)) {
            is CountDownAction.Start -> start(action)
            is CountDownAction.Stop -> stopSelf()
            else -> {}
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.clear()
    }

    private fun start(action: CountDownAction.Start) {
        val text = getString(R.string.timer_scheduled)
        val notification = countDownNotificationBuilder.createNotification(text)
        startForeground(NOTIFICATION_ID, notification)
        startCountDown(action.timeInMillis)
    }

    private fun startCountDown(timeInMillis: Long) {
        with(countDownTimer) {
            clear()
            startTimer(timeInMillis)
            afterTick = countDownNotificationBuilder::notify
            onComplete = {
                lifecycleScope.launch {
                    timerActionPerformer.perform()
                    stopSelf()
                }
            }
        }
    }

    companion object {
        const val KEY_ACTION = "CountDownServiceAction"

        fun create(context: Context, action: CountDownAction): Intent {
            return Intent(context, CountDownService::class.java).apply {
                putExtra(KEY_ACTION, action)
            }
        }
    }
}