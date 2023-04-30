package com.mist.pressurediary

import android.app.Application
import com.mist.pressurediary.data.Database
import com.mist.pressurediary.modules.masterModule
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
                masterModule
            )
        }
    }
}