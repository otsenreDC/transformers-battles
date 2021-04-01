package io.banana.transformersbattle.domain.use_cases

import io.banana.transformersbattle.domain.flaws.NoAvailableTokenFlaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.repositories.ITransformersRepository

class DeleteTransformerUseCase(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val transformersRepository: ITransformersRepository
) {

    suspend fun execute(id: String): Result<Boolean> {
        val token = getToken() ?: return Failure(NoAvailableTokenFlaw())

        return transformersRepository.delete(token, id)
    }

    private suspend fun getToken(): String? {
        return when (val result = getAccessTokenUseCase.execute()) {
            is Success -> result.data
            is Failure -> null
        }
    }
}