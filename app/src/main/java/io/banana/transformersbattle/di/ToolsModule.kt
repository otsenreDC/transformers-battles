package io.banana.transformersbattle.di

import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val toolsModule = module {
    factory {
        Glide.get(androidApplication())
    }
}