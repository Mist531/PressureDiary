package com.mist.common.modules

import com.mist.common.data.stores.impl.TokensDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val dataStoreModule = module {
    singleOf(::TokensDataStore)
}