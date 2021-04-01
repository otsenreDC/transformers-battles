package io.banana.transformersbattle.data.local.data_sources

import android.content.SharedPreferences
import androidx.core.content.edit
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.flaws.NoAvailableTokenFlaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.data_sources.ISessionDataSource

private const val KEY_TOKEN: String = "session::access_token"

class SharedPreferenceSessionDataSource(
    private val sharedPreferences: SharedPreferences
) :
    ISessionDataSource {
    override suspend fun getToken(): Result<String> {
        val token = sharedPreferences.getString(KEY_TOKEN, "")

        return if (token.isNullOrBlank()) {
            Failure(NoAvailableTokenFlaw())
        } else Success(token)
    }

    override suspend fun saveToken(token: String): Result<Boolean> {
        return try {
            sharedPreferences.edit(commit = true) {
                putString(KEY_TOKEN, token)
            }
            Success(true)
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }
}