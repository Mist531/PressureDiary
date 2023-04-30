package com.mist.pressurediary.modules

import org.koin.dsl.module

val masterModule = module {
    includes(
        viewModelModule
    )
}