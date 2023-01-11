package net.emrekalkan.locktimer.presentation.ui.screen.preferences.availability

import android.content.Context
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionType

class LockScreenAvailability constructor(val context: Context) : PreferenceAvailability {
    override val actionType: TimerActionType
        get() = TimerActionType.PREF_LOCK_SCREEN

    override fun checkAvailability(): Boolean = true

    override fun getPreferenceModel(): PreferenceModel<*> {
        return TimerActionPreferenceModel(key = actionType.key, titleRes = R.string.pref_title_lock_screen, requiresAdmin = true)
    }
}