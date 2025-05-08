package io.pressurediary.android.common.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State : Any> : ViewModel() {

    protected abstract val initialState: State

    private val _uiState by lazy {
        MutableStateFlow(initialState)
    }

    val uiState by lazy {
        _uiState.asStateFlow()
    }

    //protected
    var state: State
        get() = _uiState.value
        set(newState) = _uiState.update { newState }
}