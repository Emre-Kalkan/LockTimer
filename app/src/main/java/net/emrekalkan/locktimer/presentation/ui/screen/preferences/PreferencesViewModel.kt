package net.emrekalkan.locktimer.presentation.ui.screen.preferences

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import net.emrekalkan.locktimer.domain.usecase.GetAvailablePreferences
import net.emrekalkan.locktimer.domain.usecase.SetDataStorePreference
import net.emrekalkan.locktimer.presentation.ui.base.BaseViewModel
import net.emrekalkan.locktimer.presentation.ui.base.Event
import net.emrekalkan.locktimer.presentation.ui.base.State
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferencesViewModel.PreferencesEvent
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferencesViewModel.PreferencesUiState
import net.emrekalkan.locktimer.presentation.util.extensions.isAdminActive
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val application: Application,
    private val setDataStorePreference: SetDataStorePreference,
    private val getAvailablePreferences: GetAvailablePreferences,
) : BaseViewModel<PreferencesUiState, PreferencesEvent>(PreferencesUiState()) {

    init {
        getPrefs()
    }

    private fun getPrefs() {
        viewModelScope.launch {
            val prefs = getAvailablePreferences()
            setState {
                copy(preferences = prefs)
            }
        }
    }

    fun onBooleanPrefChange(checked: Boolean, model: TimerActionPreferenceModel) {
        if (shouldRequireAdminPermission(model)) return

        viewModelScope.launch {
            setDataStorePreference(model.key to checked)
        }
        setState {
            val prefs = preferences.updateModel(checked, model)
            copy(preferences = prefs)
        }
    }

    private fun shouldRequireAdminPermission(model: TimerActionPreferenceModel): Boolean {
        return if (model.requiresAdmin && application.isAdminActive().not()) {
            setState {
                val prefs = preferences.updateModel(checked = false, model)
                copy(preferences = prefs)
            }
            setEvent { PreferencesEvent.NavigateToAdminPermission }
            true
        } else {
            false
        }
    }

    private fun List<PreferenceModel<*>>.updateModel(checked: Boolean, model: TimerActionPreferenceModel): MutableList<PreferenceModel<*>> {
        val index = indexOf(model)
        val updated = model.copy(value = checked)
        return mutableListOf<PreferenceModel<*>>().apply {
            addAll(this@updateModel)
            set(index, updated)
        }
    }

    data class PreferencesUiState(
        val preferences: List<PreferenceModel<*>> = emptyList(),
    ) : State

    sealed class PreferencesEvent : Event {
        object NavigateToAdminPermission : PreferencesEvent()
    }
}