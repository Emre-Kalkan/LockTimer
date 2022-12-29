package net.emrekalkan.locktimer.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BaseViewModel<S : State, E: Event>(initialState: S) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState

    private val _event = MutableSharedFlow<E>()
    val event: SharedFlow<E> = _event

    val currentUiState: S
        get() = _uiState.value

    fun setState(produce: S.() -> S) {
        _uiState.value = produce(_uiState.value)
    }

    fun setEvent(produce: () -> E) {
        viewModelScope.launch {
            _event.emit(produce())
        }
    }
}

interface State

interface Event
