package net.emrekalkan.locktimer.domain.mapper

import net.emrekalkan.locktimer.data.local.PreferenceDataStore
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import javax.inject.Inject

class PreferenceModelMapper @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) {

    suspend fun getPreferencesModels(): List<PreferenceModel<*>> {
        return PreferenceModel.defaults.map { model ->
            val value = preferenceDataStore.getPreference(model.key)
            when (model) {
                is TimerActionPreferenceModel -> model.copy(value = value ?: model.value)
            }
        }
    }
}