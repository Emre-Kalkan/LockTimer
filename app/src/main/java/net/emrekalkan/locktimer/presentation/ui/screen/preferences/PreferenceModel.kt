package net.emrekalkan.locktimer.presentation.ui.screen.preferences

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import net.emrekalkan.locktimer.R

sealed class PreferenceModel<T>(
    open val key: Preferences.Key<Boolean>,
    @StringRes open val titleRes: Int,
    open val requiresAdmin: Boolean = false,
    open val value: T?
) {

    companion object {
        val PREF_LOCK_SCREEN = booleanPreferencesKey("PrefLockScreen")
        val PREF_STOP_AUDIO_VIDEO = booleanPreferencesKey("PrefStopAudio")
        val PREF_DISABLE_BLUETOOTH = booleanPreferencesKey("PrefDisableBluetooth")
        val PREF_DISABLE_WIFI = booleanPreferencesKey("PrefDisableWifi")
        val PREF_DISABLE_CELLULAR_DATA = booleanPreferencesKey("PrefDisableCellularData")

        val defaults: List<PreferenceModel<*>>
            get() = listOf(
                BooleanPreferenceModel(key = PREF_LOCK_SCREEN, titleRes = R.string.pref_title_lock_screen, requiresAdmin = true, value = true),
                BooleanPreferenceModel(key = PREF_STOP_AUDIO_VIDEO, titleRes = R.string.pref_title_stop_audio, value = true),
                BooleanPreferenceModel(key = PREF_DISABLE_BLUETOOTH, titleRes = R.string.pref_title_disable_bluetooth),
                BooleanPreferenceModel(key = PREF_DISABLE_WIFI, titleRes = R.string.pref_title_disable_wifi),
                BooleanPreferenceModel(key = PREF_DISABLE_CELLULAR_DATA, titleRes = R.string.pref_title_disable_cellular_data),
            )
    }
}

data class BooleanPreferenceModel(
    override val key: Preferences.Key<Boolean>,
    @StringRes override val titleRes: Int,
    override val requiresAdmin: Boolean = false,
    override val value: Boolean = false
) : PreferenceModel<Boolean>(key, titleRes, requiresAdmin, value)
