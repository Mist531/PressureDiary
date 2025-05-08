package io.pressurediary.android.wear.modules

import io.pressurediary.android.wear.ui.screens.create.CreateOrUpdateEntryViewModel
import io.pressurediary.android.wear.ui.screens.history.HistoryViewModel
import io.pressurediary.android.wear.ui.screens.settings.main.SettingsViewModel
import io.pressurediary.android.wear.ui.screens.settings.theme.SelectThemeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CreateOrUpdateEntryViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::SelectThemeViewModel)
}