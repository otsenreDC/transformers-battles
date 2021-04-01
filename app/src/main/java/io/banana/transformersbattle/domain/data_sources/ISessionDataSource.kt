package io.banana.transformersbattle.domain.data_sources

import io.banana.transformersbattle.domain.models.Result

interface ISessionDataSource {
    suspend fun getToken(): Result<String>
    suspend fun saveToken(token: String): Result<Boolean>
}