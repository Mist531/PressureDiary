package com.mist.mobile_app

import android.app.Application
import com.mist.common.modules.listModules
import com.mist.mobile_app.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MasterApplication)
            androidLogger(Level.DEBUG)
            modules(
                listModules + viewModelModule
            )
        }
    }
}