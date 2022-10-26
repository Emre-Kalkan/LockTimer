package net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CountDownAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Alarm went on", Toast.LENGTH_LONG).show()
    }
}