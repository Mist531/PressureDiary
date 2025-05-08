package io.pressurediary.android.common.data.stores

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.io.InputStream
import java.io.OutputStream

class DataStoreSerializer<T>(
    private val defValue: T,
    private val serializer: KSerializer<T>
) : Serializer<T>, KoinComponent {
    override val defaultValue: T = defValue

    override suspend fun readFrom(input: InputStream): T {
        val jsonStr = input.readBytes().decodeToString()
        return get<Json>().decodeFromString(serializer, jsonStr)
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        withContext(Dispatchers.IO) {
            val jsonStr = get<Json>().encodeToString(serializer, t)
            output.write(jsonStr.encodeToByteArray())
        }
    }
}
