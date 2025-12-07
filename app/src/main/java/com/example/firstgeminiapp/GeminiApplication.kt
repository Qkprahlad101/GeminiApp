package com.example.firstgeminiapp

import android.app.Application
import com.example.firstgeminiapp.di.appModule
import com.example.imagen.di.imagenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GeminiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GeminiApplication)
            modules(appModule, imagenModule)
        }
    }
}