package net.emrekalkan.locktimer.domain.usecase

import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import javax.inject.Inject

class DisableAdminPreferences @Inject constructor(
    private val getAvailablePreferences: GetAvailablePreferences,
    private val setDataStorePreference: SetDataStorePreference
) : NoInputUseCase<Unit> {

    override suspend fun invoke() {
        getAvailablePreferences().forEach { model ->
            val enabled = when (model) {
                is TimerActionPreferenceModel -> model.requiresAdmin && model.value
            }
            if (enabled) {
                setDataStorePreference(model.key to false)
            }
        }
    }
}