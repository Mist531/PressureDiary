package com.mist.pressurediary.modules

import com.mist.pressurediary.ui.screens.create.CreateOrUpdateEntryViewModel
import com.mist.pressurediary.ui.screens.history.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CreateOrUpdateEntryViewModel)
    viewModelOf(::HistoryViewModel)
}