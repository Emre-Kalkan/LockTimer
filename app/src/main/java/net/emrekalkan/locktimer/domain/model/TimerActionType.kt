package net.emrekalkan.locktimer.domain.model

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

enum class TimerActionType(private val prefKey: String) {
    PREF_LOCK_SCREEN("PrefLockScreen"),
    PREF_STOP_AUDIO_VIDEO("PrefStopAudio"),
    PREF_DISABLE_BLUETOOTH("PrefDisableBluetooth"),
    PREF_DISABLE_WIFI("PrefDisableWifi"),
    PREF_DISABLE_CELLULAR_DATA("PrefDisableCellularData");

    val key: Preferences.Key<Boolean>
        get() = booleanPreferencesKey(prefKey)
}