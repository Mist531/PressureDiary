package com.mist.wear_os.modules

import org.koin.dsl.module

val masterModule = module {
    includes(
        viewModelModule,
        dataStoreModule
    )
}