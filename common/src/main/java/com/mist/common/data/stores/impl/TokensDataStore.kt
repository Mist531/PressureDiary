package com.mist.common.data.stores.impl

import android.content.Context
import com.example.api.models.TokensModel
import com.example.api.utils.LocalDateTimeSerializer
import com.mist.common.data.repository.UserRepository
import com.mist.common.data.stores.DataStore
import com.mist.common.data.stores.DataStoreModel
import com.mist.common.data.stores.tokensDataStore
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.LocalDateTime

@Serializable
data class TokenDataStoreModel(
    @Serializable(with = LocalDateTimeSerializer::class)
    override val lastUpdateFromServer: LocalDateTime? = null,
    override val data: TokensModel? = null
) : DataStoreModel<TokensModel>

class TokensDataStore(
    private val userRepository: UserRepository,
    context: Context,
) : DataStore<TokensModel, TokenDataStoreModel>() {
    override val dataStore = context.tokensDataStore

    override val dataStoreFlow = dataStore.data

    override val updateInterval: Duration = Duration.ofDays(3)

    private val maxUpdateInterval: Duration = Duration.ofDays(6)

    private val _checkInitialRouteState by lazy {
        MutableStateFlow<StateEventWithContent<Boolean?>>(consumed())
    }

    val checkInitialRouteState by lazy {
        _checkInitialRouteState.asStateFlow()
    }

    override suspend fun clearData() {
        dataStore.updateData {
            TokenDataStoreModel()
        }
    }

    override suspend fun updateDataFromServer() {
        //TODO обновление токенов
    }

    public override suspend fun updateDataStore(
        data: TokensModel,
        updateTime: LocalDateTime?
    ) {
        dataStore.updateData { dataStore ->
            dataStore.copy(
                data = data,
                lastUpdateFromServer = updateTime ?: dataStore.lastUpdateFromServer
            )
        }
    }

    override suspend fun checkAndUpdateData() {
        updateMutex.withLock {
            dataStoreFlow.firstOrNull()?.lastUpdateFromServer?.let { lastUpdateFromServer ->
                val dateTimeNow = LocalDateTime.now()

                val expirationDate = dateTimeNow.minus(updateInterval)
                val maxExpirationDate = dateTimeNow.minus(maxUpdateInterval)

                if (lastUpdateFromServer.isAfter(maxExpirationDate) &&
                    lastUpdateFromServer.isBefore(expirationDate)
                ) {
                    updateDataFromServer()
                    onTriggeredInitialRoute(false)
                } else {
                    val durationBetweenNowAndExpiration =
                        Duration.between(lastUpdateFromServer, expirationDate)
                    if (durationBetweenNowAndExpiration > updateInterval) {
                        //Откидывать на логин
                        onTriggeredInitialRoute(true)
                    } else {
                        onTriggeredInitialRoute(false)
                    }
                }
            } ?: onTriggeredInitialRoute(true)
        }
    }

    private fun onTriggeredInitialRoute(
        result: Boolean?
    ) {
        _checkInitialRouteState.update {
            triggered(result)
        }
    }

    fun onConsumedInitialRoute() {
        _checkInitialRouteState.update {
            consumed()
        }
    }
}