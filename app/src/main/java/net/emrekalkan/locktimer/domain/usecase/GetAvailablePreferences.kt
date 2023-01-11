package net.emrekalkan.locktimer.domain.usecase

import net.emrekalkan.locktimer.domain.mapper.PreferenceModelMapper
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.availability.PreferenceAvailability
import javax.inject.Inject

class GetAvailablePreferences @Inject constructor(
    private val preferenceAvailabilities: List<@JvmSuppressWildcards PreferenceAvailability>,
    private val preferenceModelMapper: PreferenceModelMapper
) : NoInputUseCase<List<PreferenceModel<*>>> {

    override suspend fun invoke(): List<PreferenceModel<*>> {
        val availablePreferences = preferenceAvailabilities
            .filter(PreferenceAvailability::checkAvailability)
            .map(PreferenceAvailability::getPreferenceModel)
        return preferenceModelMapper(availablePreferences)
    }
}