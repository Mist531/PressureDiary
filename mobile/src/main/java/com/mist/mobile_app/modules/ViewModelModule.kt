package com.mist.mobile_app.modules

import com.mist.mobile_app.ui.screens.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
}