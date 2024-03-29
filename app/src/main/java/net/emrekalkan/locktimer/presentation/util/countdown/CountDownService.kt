package net.emrekalkan.locktimer.presentation.util.countdown

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions.TimerActionPerformer
import net.emrekalkan.locktimer.presentation.util.countdown.CountDownNotificationBuilder.Companion.NOTIFICATION_ID
import java.util.concurrent.TimeUnit
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
        val action: CountDownAction? = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(KEY_ACTION, CountDownAction::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra(KEY_ACTION)
        }
        when (action) {
            is CountDownAction.Start -> start(action)
            is CountDownAction.Notification.Stop -> stopSelf()
            is CountDownAction.Notification.Extend -> extendRemainingTime(action.extend)
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

    private fun extendRemainingTime(extend: CountDownExtend) {
        val extendTimeInMillis = TimeUnit.MINUTES.toMillis(extend.timeInMinutes.toLong())
        countDownTimer.extend(extendTimeInMillis)
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