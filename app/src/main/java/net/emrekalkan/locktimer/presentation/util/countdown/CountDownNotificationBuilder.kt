package net.emrekalkan.locktimer.presentation.util.countdown

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.util.pendingIntentFlags
import javax.inject.Inject

class CountDownNotificationBuilder @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private lateinit var builder: NotificationCompat.Builder

    private val notificationManager by lazy { context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    init {
        initBuilder()
        initChannel()
    }

    private fun initBuilder() {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_lock)
            .setContentTitle(context.getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .addAction(createStopAction())
            .addAction(createExtendAction(CountDownAction.REQUEST_CODE_EXTEND_FIVE, CountDownExtend.FIVE))
            .addAction(createExtendAction(CountDownAction.REQUEST_CODE_EXTEND_FIFTEEN, CountDownExtend.FIFTEEN))
    }

    private fun initChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channelName = context.getString(R.string.notification_count_down_name)
        val channelDescription = context.getString(R.string.notification_count_down_description)
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = channelDescription
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun createPendingIntent(countDownAction: CountDownAction.Notification): PendingIntent {
        val countDownServiceIntent = CountDownService.create(context, countDownAction)
        return PendingIntent.getService(context, countDownAction.requestCode, countDownServiceIntent, pendingIntentFlags)
    }

    private fun createStopAction(): NotificationCompat.Action {
        val action = CountDownAction.Notification.Stop
        val pendingIntent = createPendingIntent(action)
        val title = context.getString(R.string.stop)
        return NotificationCompat.Action(null, title, pendingIntent)
    }

    private fun createExtendAction(requestCode: Int, extend: CountDownExtend): NotificationCompat.Action {
        val pendingIntent = createPendingIntent(CountDownAction.Notification.Extend(requestCode, extend))
        val title = context.getString(extend.titleRes)
        return NotificationCompat.Action(null, title, pendingIntent)
    }

    fun createNotification(text: String): Notification {
        return builder
            .setContentText(text)
            .build()
    }

    fun notify(timeText: String) {
        val notification = createNotification(timeText)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_ID = 65432345
        private const val CHANNEL_ID = "CountDownNotificationId"
    }
}