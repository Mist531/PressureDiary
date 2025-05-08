package io.pressurediary.android.common.data.stores

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Duration
import java.time.LocalDateTime

internal data class RuntimeStoreModel<T>(
    val data: T?,
    val lastUpdate: LocalDateTime?
)

abstract class RuntimeStore<T : Any> {
    protected open val initialValueData: T? = null

    private val initialValueState = RuntimeStoreModel<T>(
        data = initialValueData,
        lastUpdate = null
    )

    private val storeFlow = MutableStateFlow(initialValueState)

    protected val storeScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    protected open val updateInterval: Duration? = null

    init {
        storeScope.launch {
            var count = 0
            storeFlow.subscriptionCount.collectLatest {
                if (count < it) {
                    if (updateInterval == null)
                        fetchDataInStore()
                    else
                        checkAndUpdateData()
                }
                count = it
            }
        }
    }

    private val updateMutex = Mutex()

    fun clearDataInStore() = storeFlow.update {
        initialValueState
    }

    protected abstract suspend fun getData(): T

    fun getStateFlowStore(): Flow<T> = storeFlow.mapNotNull {
        it.data
    }

    suspend fun fetchDataInStore() = updateDataStore(getData())

    protected fun updateDataStore(
        data: T
    ) = storeFlow.update {
        RuntimeStoreModel(
            data = data,
            lastUpdate = LocalDateTime.now()
        )
    }

    private suspend fun checkAndUpdateData() = updateMutex.withLock {
        storeFlow.value.lastUpdate?.let { lastUpdate ->
            val expirationDate = LocalDateTime.now()
                .minus(updateInterval)

            if (lastUpdate.isBefore(expirationDate)) fetchDataInStore()
        } ?: fetchDataInStore()
    }
}