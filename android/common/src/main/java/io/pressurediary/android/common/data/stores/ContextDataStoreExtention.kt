package io.pressurediary.android.common.data.stores

import android.content.Context
import androidx.datastore.dataStore
import io.pressurediary.android.common.data.stores.impl.TokenDataStoreModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private val dataStoreScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

val Context.tokensDataStore by dataStore(
    fileName = "tokens.json",
    serializer = DataStoreSerializer(
        defValue = TokenDataStoreModel(),
        serializer = TokenDataStoreModel.serializer()
    ),
    scope = dataStoreScope
)