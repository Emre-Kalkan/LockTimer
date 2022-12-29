package net.emrekalkan.locktimer.presentation.ui.screen.preferences

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.emrekalkan.locktimer.data.local.PreferenceDataStore
import net.emrekalkan.locktimer.domain.mapper.PreferenceModelMapper
import net.emrekalkan.locktimer.presentation.base.BaseViewModel
import net.emrekalkan.locktimer.presentation.base.Event
import net.emrekalkan.locktimer.presentation.base.State
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferencesViewModel.PreferencesEvent
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferencesViewModel.PreferencesUiState
import net.emrekalkan.locktimer.presentation.util.extensions.isAdminActive
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val application: Application,
    private val preferenceDataStore: PreferenceDataStore,
    private val preferenceModelMapper: PreferenceModelMapper,
) : BaseViewModel<PreferencesUiState, PreferencesEvent>(PreferencesUiState()) {

    init {
        getPrefs()
    }

    private fun getPrefs() {
        viewModelScope.launch {
            val prefs = preferenceModelMapper.getPreferencesModels()
            setState {
                copy(preferences = prefs)
            }
        }
    }

    fun onBooleanPrefChange(checked: Boolean, model: BooleanPreferenceModel) {
        if (model.requiresAdmin && application.isAdminActive().not()) {
            setState {
                val prefs = preferences.updateModel(checked = false, model)
                copy(preferences = prefs)
            }
            setEvent { PreferencesEvent.NavigateToOnBoarding }
            return
        }

        viewModelScope.launch {
            preferenceDataStore.setPreference(model.key to checked)
        }
        setState {
            val prefs = preferences.updateModel(checked, model)
            copy(preferences = prefs)
        }
    }

    private fun List<PreferenceModel<*>>.updateModel(checked: Boolean, model: BooleanPreferenceModel): MutableList<PreferenceModel<*>> {
        val index = indexOf(model)
        val updated = model.copy(value = checked)
        return mutableListOf<PreferenceModel<*>>().apply {
            addAll(this@updateModel)
            set(index, updated)
        }
    }

    data class PreferencesUiState(
        val preferences: List<PreferenceModel<*>> = PreferenceModel.defaults
    ) : State

    sealed class PreferencesEvent : Event {
        object NavigateToOnBoarding : PreferencesEvent()
    }
}