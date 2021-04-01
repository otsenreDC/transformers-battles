package io.banana.transformersbattle.di

import io.banana.transformersbattle.data.remote.services.Services
import io.banana.transformersbattle.data.remote.services.StringConverterFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

val NAMED_JSON_RESPONSE
    get() = named("json")

val NAMED_STRING_RESPONSE
    get() = named("string")

val servicesModule = module {
    single(NAMED_STRING_RESPONSE) {
        Services.createClient(StringConverterFactory())
    }
    single(NAMED_JSON_RESPONSE) {
        Services.createClient(GsonConverterFactory.create())
    }
    single {
        Services.sessionApi(get(NAMED_STRING_RESPONSE))
    }
    single {
        Services.transformersApi(get(NAMED_JSON_RESPONSE))
    }
}