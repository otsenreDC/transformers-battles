package io.banana.transformersbattle.data.remote.data_sources

import android.os.Build
import io.banana.transformersbattle.data.remote.apis.SessionApi
import io.banana.transformersbattle.domain.data_sources.ISessionDataSource
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success

class RemoteSessionDataSource(
    private val sessionApi: SessionApi
) : ISessionDataSource {
    override suspend fun getToken(): Result<String> {
        return try {
            val token = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                sessionApi.getTokenBlocking().execute().body()?.string() ?: ""
            } else {
                sessionApi.getToken().string()
            }
            Success(token)
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }

    override suspend fun saveToken(token: String): Result<Boolean> {
        return Failure(Flaw(message = "This function is not available."))
    }
}