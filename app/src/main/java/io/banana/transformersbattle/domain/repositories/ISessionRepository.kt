package io.banana.transformersbattle.domain.repositories

import io.banana.transformersbattle.domain.models.Result

interface ISessionRepository {
    suspend fun getToken(): Result<String>
}