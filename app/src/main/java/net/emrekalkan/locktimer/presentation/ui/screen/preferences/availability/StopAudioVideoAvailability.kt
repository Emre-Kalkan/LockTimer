package net.emrekalkan.locktimer.presentation.ui.screen.preferences.availability

import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionType

class StopAudioVideoAvailability : PreferenceAvailability {
    override val actionType: TimerActionType
        get() = TimerActionType.PREF_STOP_AUDIO_VIDEO

    override fun checkAvailability(): Boolean = true

    override fun getPreferenceModel(): PreferenceModel<*> {
        return TimerActionPreferenceModel(key = actionType.key, titleRes = R.string.pref_title_stop_audio, value = true)
    }
}