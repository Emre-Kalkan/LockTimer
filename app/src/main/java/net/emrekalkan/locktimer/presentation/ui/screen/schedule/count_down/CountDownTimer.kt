package net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down

import net.emrekalkan.locktimer.presentation.util.AsyncTimer
import net.emrekalkan.locktimer.presentation.util.DateFormatter

typealias TimeAfterTick = (String) -> Unit

class CountDownTimer : AsyncTimer() {

    var afterTick: TimeAfterTick? = null
    var onComplete: (() -> Unit)? = null

    fun startTimer(timeInMillis: Long) {
        if (isActive()) return

        onTick = {
            val text = DateFormatter.countDownFormat(it)
            afterTick?.invoke(text)
        }
        onDone = {
            onComplete?.invoke()
        }
        start(timeInMillis)
    }

    fun clear() {
        stop()
        onTick = null
    }
}