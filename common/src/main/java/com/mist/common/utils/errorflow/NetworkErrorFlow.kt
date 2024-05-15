package com.mist.common.utils.errorflow

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NetworkErrorFlow {
    private val _flow = MutableSharedFlow<NetworkError>()
    val flow: SharedFlow<NetworkError>
        get() = _flow.asSharedFlow()

    suspend fun pushError(error: NetworkError): Unit = _flow.emit(error)
}