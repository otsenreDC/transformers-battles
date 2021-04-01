package io.banana.transformersbattle.domain.data_sources

import io.banana.transformersbattle.data.remote.dtos.TransformerDTO
import io.banana.transformersbattle.domain.models.Result

interface ITransformersDataSource {
    suspend fun list(token: String): Result<List<TransformerDTO>>
    suspend fun create(token: String, transformerDTO: TransformerDTO): Result<TransformerDTO>
    suspend fun edit(token: String, transformerDTO: TransformerDTO): Result<TransformerDTO>
    suspend fun delete(token: String, id: String): Result<Boolean>
}