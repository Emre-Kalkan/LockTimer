package net.emrekalkan.locktimer.domain.usecase

import androidx.datastore.preferences.core.Preferences
import net.emrekalkan.locktimer.data.local.PreferenceDataStore
import javax.inject.Inject

class SetDataStorePreference @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) : UseCase<Preferences.Pair<*>, Unit> {
    override suspend fun invoke(params: Preferences.Pair<*>) {
        preferenceDataStore.setPreference(params)
    }
}