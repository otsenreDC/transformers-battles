package io.banana.transformersbattle.framework

import android.app.Application
import io.banana.transformersbattle.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TBApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TBApplication)
            modules(appModules)
        }

    }
}