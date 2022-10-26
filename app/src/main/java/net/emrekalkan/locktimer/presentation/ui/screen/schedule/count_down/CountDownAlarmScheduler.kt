package net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import dagger.hilt.android.qualifiers.ActivityContext
import net.emrekalkan.locktimer.presentation.util.baseActivity
import net.emrekalkan.locktimer.presentation.util.pendingIntentFlags
import javax.inject.Inject

class CountDownAlarmScheduler @Inject constructor(
    @ActivityContext context: Context
) {
    private val alarmManager by lazy {
        context.baseActivity.getSystemService(ALARM_SERVICE) as AlarmManager
    }

    private val pendingIntent by lazy {
        PendingIntent.getBroadcast(
            context,
            ALARM_SCHEDULER_REQUEST_CODE,
            Intent(context, CountDownAlarmReceiver::class.java),
            pendingIntentFlags
        )
    }

    fun schedule() {
        val clockInfo = AlarmManager.AlarmClockInfo(System.currentTimeMillis() + 2000, pendingIntent)
        alarmManager.setAlarmClock(clockInfo, pendingIntent)
    }

    companion object {
        private const val ALARM_SCHEDULER_REQUEST_CODE = 1997
    }
}