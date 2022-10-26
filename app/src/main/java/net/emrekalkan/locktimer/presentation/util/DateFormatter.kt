package net.emrekalkan.locktimer.presentation.util

import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

object DateFormatter {

    fun countDownFormat(timeInMillis: Long): String {
        val decimalFormat = DecimalFormat("00")
        val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
            .mod(24)
            .let(decimalFormat::format)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
            .mod(60)
            .let(decimalFormat::format)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis)
            .mod(60)
            .let(decimalFormat::format)

        return "$hours:$minutes:$seconds"
    }

    fun minutesToMillis(minutes: Int) = TimeUnit.MINUTES.toMillis(minutes.toLong())
}