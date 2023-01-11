package net.emrekalkan.locktimer.presentation.ui.screen.preferences.availability

import android.os.Build
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionType

class DisableWifiAvailability : PreferenceAvailability {
    override val actionType: TimerActionType
        get() = TimerActionType.PREF_DISABLE_WIFI

    override fun checkAvailability(): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q

    override fun getPreferenceModel(): PreferenceModel<*> {
        return TimerActionPreferenceModel(key = actionType.key, titleRes = R.string.pref_title_disable_wifi)
    }
}