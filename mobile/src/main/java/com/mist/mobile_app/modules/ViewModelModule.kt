package com.mist.mobile_app.modules

import com.example.api.models.PressureRecordModel
import com.mist.mobile_app.ui.screens.auth.login.LoginViewModel
import com.mist.mobile_app.ui.screens.auth.registration.RegistrationViewModel
import com.mist.mobile_app.ui.screens.main.MainViewModel
import com.mist.mobile_app.ui.screens.main.history.HistoryViewModel
import com.mist.mobile_app.ui.screens.main.records.new_rec.NewRecordViewModel
import com.mist.mobile_app.ui.screens.main.records.refactor.RefactorRecordViewModel
import com.mist.mobile_app.ui.screens.main.settings.SettingsViewModel
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