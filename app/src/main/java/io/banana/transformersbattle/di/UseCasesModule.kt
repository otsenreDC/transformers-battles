package io.banana.transformersbattle.di

import io.banana.transformersbattle.domain.use_cases.*
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        GetAccessTokenUseCase(get())
    }
    factory {
        GetTransformersUseCase(get(), get())
    }
    factory {
        CreateTransformersUseCase(get(), get())
    }
    factory {
        EditTransformersUseCase(get(), get())
    }
    factory {
        DeleteTransformerUseCase(get(), get())
    }
}