package io.banana.transformersbattle.domain.view_models

import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.use_cases.GetAccessTokenUseCase

interface ISplashViewModel : IViewModel<String> {
    val getTokenUseCase: GetAccessTokenUseCase

    suspend fun hasToken() {
        onLoading(true)
        when (val result = getTokenUseCase.execute()) {
            is Success -> {
                onDone(result.data)
            }
            is Failure -> {
                onError(result.flaw)
            }
        }
    }
}