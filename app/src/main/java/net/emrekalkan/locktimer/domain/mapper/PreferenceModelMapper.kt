package net.emrekalkan.locktimer.domain.mapper

import net.emrekalkan.locktimer.data.local.PreferenceDataStore
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.BooleanPreferenceModel
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferenceModel
import javax.inject.Inject

class PreferenceModelMapper @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) {

    suspend fun getPreferencesModels(): List<PreferenceModel<*>> {
        return PreferenceModel.defaults.map { model ->
            val value = preferenceDataStore.getPreference(model.key)
            when (model) {
                is BooleanPreferenceModel -> model.copy(value = value ?: model.value)
            }
        }
    }
}