package io.pressurediary.android.common.utils.errorflow

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NetworkErrorFlow {
    private val _flow = MutableSharedFlow<NetworkError>()
    val flow: SharedFlow<NetworkError>
        get() = _flow.asSharedFlow()

    suspend fun pushError(error: NetworkError) {
        _flow.emit(error)
    }
}