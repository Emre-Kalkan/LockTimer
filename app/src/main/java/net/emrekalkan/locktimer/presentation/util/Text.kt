package net.emrekalkan.locktimer.presentation.util

import android.content.Context
import androidx.annotation.StringRes

sealed class Text {
    data class Res(@StringRes val textRes: Int) : Text()
    data class Raw(val text: String) : Text()

    fun getText(context: Context): String {
        return when (this) {
            is Raw -> text
            is Res -> context.getString(textRes)
        }
    }
}
