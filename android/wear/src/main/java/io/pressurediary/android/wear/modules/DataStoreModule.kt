package io.pressurediary.android.wear.modules

import io.pressurediary.android.wear.data.settings.SettingsDataStore
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataStoreModule = module {
    singleOf(::SettingsDataStore)
    single {
        Json {
            isLenient = true
            prettyPrint = true
        }
    }
}