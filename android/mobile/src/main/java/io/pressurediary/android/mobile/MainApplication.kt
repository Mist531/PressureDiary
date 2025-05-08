package io.pressurediary.android.mobile

import android.app.Application
import io.pressurediary.android.common.data.bd.Database
import io.pressurediary.android.common.modules.listModules
import io.pressurediary.android.mobile.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.initLocalDatabase(this)
        startKoin {
            androidContext(this@MainApplication)
            androidLogger(Level.DEBUG)
            modules(
                listModules + viewModelModule
            )
        }
    }
}