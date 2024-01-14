package com.mist.mobile_app.modules

import com.mist.mobile_app.ui.screens.auth.login.LoginViewModel
import com.mist.mobile_app.ui.screens.auth.registration.RegistrationViewModel
import com.mist.mobile_app.ui.screens.main.history.HistoryViewModel
import com.mist.mobile_app.ui.screens.main.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::SettingsViewModel)
}