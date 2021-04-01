package io.banana.transformersbattle.data.remote.data_sources

import io.banana.transformersbattle.data.remote.apis.TransformersApi
import io.banana.transformersbattle.data.remote.dtos.TransformerDTO
import io.banana.transformersbattle.domain.data_sources.ITransformersDataSource
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success

class RemoteTransformersDataSource(
    private val transformersApi: TransformersApi
) : ITransformersDataSource {
    override suspend fun list(token: String): Result<List<TransformerDTO>> {
        return try {
            Success(transformersApi.getList("Bearer $token").transformers)
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }

    override suspend fun create(
        token: String,
        transformerDTO: TransformerDTO
    ): Result<TransformerDTO> {
        return try {
            Success(transformersApi.create("Bearer $token", transformerDTO))
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }

    override suspend fun edit(
        token: String,
        transformerDTO: TransformerDTO
    ): Result<TransformerDTO> {
        return try {
            Success(transformersApi.edit("Bearer $token", transformerDTO))
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }

    override suspend fun delete(
        token: String,
        id: String
    ): Result<Boolean> {
        return try {
            transformersApi.delete("Bearer $token", id)
            Success(true)
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }
}