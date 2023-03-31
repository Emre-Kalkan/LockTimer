package net.emrekalkan.locktimer.presentation.util.countdown

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CountDownAction : Parcelable {
    @Parcelize
    data class Start(val timeInMillis: Long) : CountDownAction()

    sealed class Notification(open val requestCode: Int) : CountDownAction() {
        @Parcelize
        object Stop : Notification(REQUEST_CODE_STOP)

        @Parcelize
        data class Extend(override val requestCode: Int, val extend: CountDownExtend) : Notification(requestCode)
    }

    companion object {
        private const val REQUEST_CODE_STOP = 1
        const val REQUEST_CODE_EXTEND_FIVE = 2
        const val REQUEST_CODE_EXTEND_FIFTEEN = 3
    }
}
