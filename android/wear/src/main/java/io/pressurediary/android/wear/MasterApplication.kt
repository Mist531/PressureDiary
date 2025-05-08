package io.pressurediary.android.wear

import android.app.Application
import io.pressurediary.android.common.data.bd.Database
import io.pressurediary.android.wear.modules.masterModule
import io.pressurediary.android.wear.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.initLocalDatabase(this)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MasterApplication)
            modules(
                masterModule + viewModelModule
            )
        }
    }
}