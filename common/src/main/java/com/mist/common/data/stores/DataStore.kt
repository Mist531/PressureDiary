package com.mist.common.data.stores

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Duration
import java.time.LocalDateTime

interface DataStoreModel<T> {
	val data: T?
	val lastUpdateFromServer: LocalDateTime?
}

abstract class DataStore<DT : Any,M: DataStoreModel<DT>> {
	protected abstract val dataStore: DataStore<M>

	protected abstract val dataStoreFlow: Flow<M>

	open val updateInterval: Duration = Duration.ofHours(1)

	private val updateMutex = Mutex()

	abstract suspend fun clearData()

	protected abstract suspend fun updateDataFromServer()

	protected abstract suspend fun updateDataStore(
		data: DT,
		updateTime: LocalDateTime?
	)

	fun getStateFlow() : Flow<DT?> {
		return dataStoreFlow.mutableStateIn(
			onNewSubscriber = ::checkAndUpdateData
		).filterNotNull().map {
			it.data
		}
	}

	suspend fun fetchData() {
		updateDataFromServer()
	}

	suspend fun updateDataStore(
		data: DT,
	) {
		updateDataStore(
			data = data,
			updateTime = null
		)
	}

	private suspend fun checkAndUpdateData() {
		updateMutex.withLock {
			dataStoreFlow.firstOrNull()?.lastUpdateFromServer?.let { lastUpdate ->
				val expirationDate = LocalDateTime.now()
					.minus(updateInterval)

				if (lastUpdate.isBefore(expirationDate)) updateDataFromServer()
			} ?: updateDataFromServer()
		}
	}

	private fun <R> Flow<R>.mutableStateIn(
		scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
		initialValue: R? = null,
		onNewSubscriber: suspend () -> Unit = {}
	): MutableStateFlow<R?> {
		val stateFlow = MutableStateFlow(initialValue)

		scope.launch {
			supervisorScope {
				launch {
					this@mutableStateIn.collect(stateFlow)
				}
				launch {
					trackSubscriptionCount(
						stateFlow = stateFlow,
						onNewSubscriber
					)
				}
			}
		}

		return stateFlow
	}

	private suspend fun trackSubscriptionCount(
		stateFlow: MutableStateFlow<*>,
		onNewSubscriber: suspend () -> Unit
	) {
		var count = 0
		stateFlow.subscriptionCount.collectLatest {
			if (count < it) onNewSubscriber()
			count = it
		}
	}
}