package io.pressurediary.android.mobile.modules

import io.pressurediary.server.api.models.PressureRecordModel
import io.pressurediary.android.mobile.ui.screens.auth.login.LoginViewModel
import io.pressurediary.android.mobile.ui.screens.auth.registration.RegistrationViewModel
import io.pressurediary.android.mobile.ui.screens.main.MainViewModel
import io.pressurediary.android.mobile.ui.screens.main.history.HistoryViewModel
import io.pressurediary.android.mobile.ui.screens.main.records.new_rec.NewRecordViewModel
import io.pressurediary.android.mobile.ui.screens.main.records.refactor.RefactorRecordViewModel
import io.pressurediary.android.mobile.ui.screens.main.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::NewRecordViewModel)
    viewModelOf(::MainViewModel)
    viewModel { (earningPeriodEndDate: PressureRecordModel?) ->
        RefactorRecordViewModel(
            pressureRecordModel = earningPeriodEndDate,
            pressureRecordRepository = get()
        )
    }
}