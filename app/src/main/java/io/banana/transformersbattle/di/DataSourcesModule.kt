package io.banana.transformersbattle.di

import io.banana.transformersbattle.data.local.data_sources.SharedPreferenceSessionDataSource
import io.banana.transformersbattle.data.remote.data_sources.RemoteSessionDataSource
import io.banana.transformersbattle.data.remote.data_sources.RemoteTransformersDataSource
import io.banana.transformersbattle.domain.data_sources.ISessionDataSource
import io.banana.transformersbattle.domain.data_sources.ITransformersDataSource
import org.koin.dsl.module

val dataSourcesModule = module {
    single<ISessionDataSource>(NAMED_SHARED_PREFERENCE) {
        SharedPreferenceSessionDataSource(get())
    }
    single<ISessionDataSource>(NAMED_REMOTE) {
        RemoteSessionDataSource(get())
    }
    single<ITransformersDataSource> {
        RemoteTransformersDataSource(get())
    }
}