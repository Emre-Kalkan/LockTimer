package net.emrekalkan.locktimer.presentation.util.countdown

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.emrekalkan.locktimer.presentation.util.DateFormatter

sealed class CountDownAction : Parcelable {
    @Parcelize
    data class Start(val timeInMillis: Long) : CountDownAction()

    @Parcelize
    object Stop : CountDownAction()

    val formattedTimeText: String
        get() = when (this) {
            is Start -> DateFormatter.countDownFormat(timeInMillis)
            Stop -> ""
        }
}
