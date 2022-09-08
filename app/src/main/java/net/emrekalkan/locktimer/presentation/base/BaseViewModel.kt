package net.emrekalkan.locktimer.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel<S : State>(initialState: S) : ViewModel() {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: StateFlow<S> = _stateFlow

    val currentUiState: S
        get() = _stateFlow.value

    fun setState(block: S.() -> S) {
        _stateFlow.value = block(_stateFlow.value)
    }
}

interface State
