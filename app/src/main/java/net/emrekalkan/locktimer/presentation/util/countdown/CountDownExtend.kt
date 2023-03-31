package net.emrekalkan.locktimer.presentation.util.countdown

import androidx.annotation.StringRes
import net.emrekalkan.locktimer.R

enum class CountDownExtend(val timeInMinutes: Int, @StringRes val titleRes: Int) {
    FIVE(5, R.string.extend_five_minutes),
    FIFTEEN(15, R.string.extend_fifteen_minutes)
}