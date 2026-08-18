package com.example.regprofi26_matule.DI

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MatuleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MatuleApplication)
            modules(networkModule)
        }
    }
}