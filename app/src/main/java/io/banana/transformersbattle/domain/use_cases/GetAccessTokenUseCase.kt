package io.banana.transformersbattle.domain.use_cases

import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.flaws.NoAvailableTokenFlaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.repositories.ISessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAccessTokenUseCase(
    private val sessionRepository: ISessionRepository
) {
    suspend fun execute(): Result<String> {
        try {
            return withContext(Dispatchers.IO) {
                when (val result = sessionRepository.getToken()) {
                    is Success -> {
                        if (result.data.isNotBlank()) {
                            Success(result.data)
                        } else {
                            Failure(NoAvailableTokenFlaw())
                        }
                    }
                    is Failure -> {
                        Failure(Flaw(cause = result.flaw.cause))
                    }
                }
            }

        } catch (throwable: Throwable) {
            return Failure(Flaw(cause = throwable))
        }
    }
}