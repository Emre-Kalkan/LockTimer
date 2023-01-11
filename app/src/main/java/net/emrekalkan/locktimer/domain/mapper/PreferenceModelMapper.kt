package net.emrekalkan.locktimer.domain.mapper

import net.emrekalkan.locktimer.data.local.PreferenceDataStore
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import javax.inject.Inject

class PreferenceModelMapper @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) : Mapper<List<PreferenceModel<*>>, List<PreferenceModel<*>>> {

    override suspend fun invoke(input: List<PreferenceModel<*>>): List<PreferenceModel<*>> {
        return input.map { model ->
            val value = preferenceDataStore.getPreference(model.key)
            when (model) {
                is TimerActionPreferenceModel -> model.copy(value = value ?: model.value)
            }
        }
    }
}