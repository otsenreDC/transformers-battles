package io.banana.transformersbattle.data.repositories

import io.banana.transformersbattle.domain.flaws.ErrorAccessingLocalStorage
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.data_sources.ISessionDataSource
import io.banana.transformersbattle.domain.repositories.ISessionRepository

class SessionRepository(
    private val remoteDataSource: ISessionDataSource,
    private val localDataSource: ISessionDataSource
) : ISessionRepository {
    override suspend fun getToken(): Result<String> {
        return try {
            when (val localToken = localDataSource.getToken()) {
                is Success -> {
                    return Success(localToken.data)
                }
                is Failure -> {
                    when (val result = remoteDataSource.getToken()) {
                        is Success -> {
                            if (cacheToken(result.data))
                                Success(result.data)
                            else
                                Failure(ErrorAccessingLocalStorage())
                        }
                        is Failure -> {
                            result
                        }
                    }
                }
            }

        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }

    private suspend fun cacheToken(token: String): Boolean {
        return localDataSource.saveToken(token) is Success
    }

}