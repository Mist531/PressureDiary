package com.mist.common.data.stores.impl

import android.content.Context
import com.example.api.models.TokensModel
import com.example.api.utils.LocalDateTimeSerializer
import com.mist.common.data.stores.DataStore
import com.mist.common.data.stores.DataStoreModel
import com.mist.common.data.stores.tokensDataStore
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TokenDataStoreModel(
    @Serializable(with = LocalDateTimeSerializer::class)
    override val lastUpdateFromServer: LocalDateTime? = null,
    override val data: TokensModel? = null
) : DataStoreModel<TokensModel>

class TokensDataStore(
    //private val userRepository: UserRepository,
    context: Context,
) : DataStore<TokensModel, TokenDataStoreModel>() {
    override val dataStore = context.tokensDataStore

    override val dataStoreFlow = dataStore.data
    override suspend fun clearData() {
        dataStore.updateData {
            TokenDataStoreModel()
        }
    }

    override suspend fun updateDataFromServer() {
        //На сервере реализовать метод обновления токена по refresh токену
        TODO("Not yet implemented")
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
}