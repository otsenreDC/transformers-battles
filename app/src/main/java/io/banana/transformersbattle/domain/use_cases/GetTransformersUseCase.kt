package io.banana.transformersbattle.domain.use_cases

import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.flaws.NoAvailableTokenFlaw
import io.banana.transformersbattle.domain.flaws.NoTransformerRegisterFlaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.domain.repositories.ITransformersRepository

class GetTransformersUseCase(
    private val transformersRepository: ITransformersRepository,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) {
    suspend fun execute(): Result<List<Transformer>> {
        return try {
            val token = getToken() ?: return Failure(NoAvailableTokenFlaw())

            when (val result = transformersRepository.list(token)) {
                is Success -> {
                    if (result.data.isEmpty())
                        Failure(NoTransformerRegisterFlaw())
                    else
                        result
                }
                is Failure -> result
            }
        } catch (throwable: Throwable) {
            return Failure(Flaw(cause = throwable))
        }
    }

    private suspend fun getToken(): String? {
        return when (val result = getAccessTokenUseCase.execute()) {
            is Success -> result.data
            is Failure -> null
        }
    }
}