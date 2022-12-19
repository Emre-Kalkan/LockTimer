package net.emrekalkan.locktimer.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferenceModel

class PreferenceDataStore(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DS_PREFERENCES)
    val data: Flow<Preferences>
        get() = context.dataStore.data

    suspend fun setPreference(pair: Preferences.Pair<Boolean>) {
        context.dataStore.edit { preferences ->
            preferences.putAll(pair)
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> getPreference(key: Preferences.Key<T>): T? {
        val result = data.firstOrNull()?.get(key)

        result?.let { return it }

        return PreferenceModel.defaults.find { it.key == key }?.value as? T
    }

    companion object {
        private const val DS_PREFERENCES = "DataStorePreferences"
    }
}