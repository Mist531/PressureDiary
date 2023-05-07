package com.mist.wear_os.data.settings

import android.content.Context
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.mist.wear_os.models.SettingsModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.io.InputStream
import java.io.OutputStream

val Context.settings by dataStore("settings.json", SettingsDataStoreSerializer)

object SettingsDataStoreSerializer : Serializer<SettingsModel>, KoinComponent {
    override val defaultValue: SettingsModel = SettingsModel()

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(input: InputStream): SettingsModel {
        return get<Json>().decodeFromStream(input)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun writeTo(t: SettingsModel, output: OutputStream) {
        get<Json>().encodeToStream(t, output)
    }
}