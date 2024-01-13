package com.mist.wear_os

import android.app.Application
import com.mist.common.data.bd.Database
import com.mist.wear_os.modules.masterModule
import com.mist.wear_os.modules.viewModelModule
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