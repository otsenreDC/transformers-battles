package io.banana.transformersbattle.di

import io.banana.transformersbattle.data.repositories.SessionRepository
import io.banana.transformersbattle.data.repositories.TransformersRepository
import io.banana.transformersbattle.domain.repositories.ISessionRepository
import io.banana.transformersbattle.domain.repositories.ITransformersRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val NAMED_SHARED_PREFERENCE
    get() = named("shared_preference")
val NAMED_REMOTE
    get() = named("remote")

val repositoriesModule = module {
    single<ISessionRepository> {
        SessionRepository(get(NAMED_REMOTE), get(NAMED_SHARED_PREFERENCE))
    }
    single<ITransformersRepository> {
        TransformersRepository(get())
    }
}