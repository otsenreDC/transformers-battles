package io.banana.transformersbattle.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sharedPreferenceModule = module {
    single {
        androidApplication().getSharedPreferences("session", Context.MODE_PRIVATE)
    }
}