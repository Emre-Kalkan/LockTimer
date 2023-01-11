package net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions

import androidx.datastore.preferences.core.Preferences
import net.emrekalkan.locktimer.data.local.PreferenceDataStore
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import javax.inject.Inject

class TimerActionPerformer @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
    private val timerActions: List<@JvmSuppressWildcards TimerAction>
) {

    suspend fun perform() {
        TimerActionPreferenceModel.defaults.forEach { preferenceModel ->
            val enabled = preferenceDataStore.getPreference(preferenceModel.key) ?: false
            if (enabled.not()) return@forEach
            val action = findAction(preferenceModel.key) ?: return@forEach

            action.perform()
        }
    }

    private fun findAction(preferenceKey: Preferences.Key<*>): TimerAction? {
        val actionClass = when (preferenceKey) {
            TimerActionPreferenceModel.PREF_LOCK_SCREEN -> LockScreenAction::class.java
            TimerActionPreferenceModel.PREF_STOP_AUDIO_VIDEO -> StopAudioVideoAction::class.java
            TimerActionPreferenceModel.PREF_DISABLE_BLUETOOTH -> DisableBluetoothAction::class.java
            else -> return null
        }

        return timerActions.filterIsInstance(actionClass).firstOrNull()
    }
}