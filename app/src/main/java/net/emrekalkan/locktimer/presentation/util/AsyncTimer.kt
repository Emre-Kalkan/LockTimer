package net.emrekalkan.locktimer.presentation.util

import kotlinx.coroutines.*

typealias TimerTick = (Long) -> Unit

abstract class AsyncTimer {

    private val timerScope = CoroutineScope(Dispatchers.Main)
    private var timerJob: Job? = null

    protected var remaining: Long = 0
    protected var onTick: (TimerTick)? = null

    protected fun start(timeInMillis: Long) {
        if (isActive()) return

        remaining = timeInMillis
        timerJob = timerScope.launch {
            while (remaining > 0L) {
                delay(SECOND_IN_MILLIS)
                remaining = remaining.minus(SECOND_IN_MILLIS).coerceAtLeast(0L)
                onTick?.invoke(remaining)
            }
        }
    }

    protected fun stop() {
        timerJob?.cancel()
    }

    fun isActive() = timerJob?.isActive ?: false

    companion object {
        private const val SECOND_IN_MILLIS = 1000L
    }
}