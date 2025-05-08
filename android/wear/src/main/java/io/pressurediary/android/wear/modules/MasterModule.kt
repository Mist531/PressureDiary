package io.pressurediary.android.wear.modules

import org.koin.dsl.module

val masterModule = module {
    includes(
        viewModelModule,
        dataStoreModule
    )
}