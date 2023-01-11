package net.emrekalkan.locktimer.presentation.util.countdown

import net.emrekalkan.locktimer.presentation.util.DateFormatter
import net.emrekalkan.locktimer.presentation.util.extensions.AsyncTimer

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