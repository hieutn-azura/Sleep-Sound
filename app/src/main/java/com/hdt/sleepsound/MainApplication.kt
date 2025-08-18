package com.hdt.sleepsound

import android.app.Application
import com.hdt.sleepsound.di.appModule
import com.hdt.sleepsound.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                )
            )
        }
    }
}