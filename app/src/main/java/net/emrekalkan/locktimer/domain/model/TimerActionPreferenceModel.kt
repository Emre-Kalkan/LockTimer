package net.emrekalkan.locktimer.domain.model

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import net.emrekalkan.locktimer.R

data class TimerActionPreferenceModel(
    override val key: Preferences.Key<Boolean>,
    @StringRes override val titleRes: Int,
    override val value: Boolean = false,
    val requiresAdmin: Boolean = false,
    val actionPermission: TimerActionPermission? = null
) : PreferenceModel<Boolean>(key, titleRes, value) {

    companion object {
        val PREF_LOCK_SCREEN = booleanPreferencesKey("PrefLockScreen")
        val PREF_STOP_AUDIO_VIDEO = booleanPreferencesKey("PrefStopAudio")
        val PREF_DISABLE_BLUETOOTH = booleanPreferencesKey("PrefDisableBluetooth")
        val PREF_DISABLE_WIFI = booleanPreferencesKey("PrefDisableWifi")
        val PREF_DISABLE_CELLULAR_DATA = booleanPreferencesKey("PrefDisableCellularData")

        val keys: List<Preferences.Key<Boolean>>
            get() = listOf(PREF_LOCK_SCREEN, PREF_STOP_AUDIO_VIDEO, PREF_DISABLE_BLUETOOTH, PREF_DISABLE_WIFI, PREF_DISABLE_CELLULAR_DATA)

        val defaults: List<PreferenceModel<*>>
            get() = listOf(
                TimerActionPreferenceModel(key = PREF_LOCK_SCREEN, titleRes = R.string.pref_title_lock_screen, requiresAdmin = true),
                TimerActionPreferenceModel(key = PREF_STOP_AUDIO_VIDEO, titleRes = R.string.pref_title_stop_audio, value = true),
                TimerActionPreferenceModel(key = PREF_DISABLE_BLUETOOTH, titleRes = R.string.pref_title_disable_bluetooth, actionPermission = TimerActionPermission.Bluetooth),
                TimerActionPreferenceModel(key = PREF_DISABLE_WIFI, titleRes = R.string.pref_title_disable_wifi),
                TimerActionPreferenceModel(key = PREF_DISABLE_CELLULAR_DATA, titleRes = R.string.pref_title_disable_cellular_data),
            )
    }
}
