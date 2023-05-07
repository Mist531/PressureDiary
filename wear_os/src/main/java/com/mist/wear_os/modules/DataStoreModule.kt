package com.mist.wear_os.modules

import com.mist.wear_os.data.settings.SettingsDataStore
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