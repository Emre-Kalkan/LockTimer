package net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleService
import dagger.hilt.android.AndroidEntryPoint
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down.CountDownNotificationBuilder.Companion.NOTIFICATION_ID
import javax.inject.Inject

@AndroidEntryPoint
class CountDownService : LifecycleService() {

    @Inject
    lateinit var countDownNotificationBuilder: CountDownNotificationBuilder

    @Inject
    lateinit var countDownTimer: CountDownTimer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (val action = intent?.getParcelableExtra<CountDownAction>(KEY_ACTION)) {
            is CountDownAction.Start -> start(action)
            is CountDownAction.Stop -> stopSelf()
            else -> {}
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(action: CountDownAction.Start) {
        val timeText = action.formattedTimeText
        val notification = countDownNotificationBuilder.createNotification(timeText)
        startForeground(NOTIFICATION_ID, notification)
        startCountDown(action.timeInMillis)
    }

    private fun startCountDown(timeInMillis: Long) {
        with(countDownTimer) {
            clear()
            startTimer(timeInMillis)
            afterTick = {
                countDownNotificationBuilder.notify(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.clear()
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