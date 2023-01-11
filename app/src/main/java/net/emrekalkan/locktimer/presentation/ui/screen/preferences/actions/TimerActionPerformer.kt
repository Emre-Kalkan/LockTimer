package net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions

import androidx.datastore.preferences.core.Preferences
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionType
import net.emrekalkan.locktimer.domain.usecase.GetAvailablePreferences
import javax.inject.Inject

class TimerActionPerformer @Inject constructor(
    private val getAvailablePreferences: GetAvailablePreferences,
    private val timerActions: List<@JvmSuppressWildcards TimerAction>
) {

    suspend fun perform() {
        getAvailablePreferences().forEach { preferenceModel ->
            val model = preferenceModel as? TimerActionPreferenceModel ?: return@forEach
            if (model.value.not()) return@forEach
            val action = findAction(preferenceModel.key) ?: return@forEach

            action.perform()
        }
    }

    private fun findAction(preferenceKey: Preferences.Key<*>): TimerAction? {
        val actionClass = when (preferenceKey) {
            TimerActionType.PREF_LOCK_SCREEN.key -> LockScreenAction::class.java
            TimerActionType.PREF_STOP_AUDIO_VIDEO.key -> StopAudioVideoAction::class.java
            TimerActionType.PREF_DISABLE_BLUETOOTH.key -> DisableBluetoothAction::class.java
            else -> return null
        }

        return timerActions.filterIsInstance(actionClass).firstOrNull()
    }
}