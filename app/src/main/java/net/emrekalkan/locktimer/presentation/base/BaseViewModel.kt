package net.emrekalkan.locktimer.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel<S : State>(initialState: S) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState

    val currentUiState: S
        get() = _uiState.value

    fun setState(block: S.() -> S) {
        _uiState.value = block(_uiState.value)
    }
}

interface State
