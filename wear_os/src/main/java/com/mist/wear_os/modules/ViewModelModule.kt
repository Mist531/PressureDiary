package com.mist.wear_os.modules

import com.mist.wear_os.ui.screens.create.CreateOrUpdateEntryViewModel
import com.mist.wear_os.ui.screens.history.HistoryViewModel
import com.mist.wear_os.ui.screens.settings.main.SettingsViewModel
import com.mist.wear_os.ui.screens.settings.theme.SelectThemeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CreateOrUpdateEntryViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::SelectThemeViewModel)
}