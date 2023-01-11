package net.emrekalkan.locktimer.presentation.ui.screen.preferences.availability

import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionType

interface PreferenceAvailability {
    val actionType: TimerActionType
    fun checkAvailability(): Boolean
    fun getPreferenceModel(): PreferenceModel<*>
}