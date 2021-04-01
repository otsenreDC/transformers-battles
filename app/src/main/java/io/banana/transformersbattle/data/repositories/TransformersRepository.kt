package io.banana.transformersbattle.data.repositories

import io.banana.transformersbattle.data.mappers.toDomain
import io.banana.transformersbattle.data.remote.dtos.TransformerDTO
import io.banana.transformersbattle.domain.data_sources.ITransformersDataSource
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.domain.repositories.ITransformersRepository

class TransformersRepository(
    private val remoteDataSource: ITransformersDataSource
) : ITransformersRepository {
    override suspend fun list(accessToken: String): Result<List<Transformer>> {
        return try {
            when (val result = remoteDataSource.list(accessToken)) {
                is Success -> Success(result.data.map { it.toDomain() })
                is Failure -> Failure(result.flaw)
            }
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }

    override suspend fun create(
        token: String,
        team: String,
        name: String,
        strength: Int,
        intelligence: Int,
        speed: Int,
        endurance: Int,
        rank: Int,
        courage: Int,
        firepower: Int,
        skill: Int
    ): Result<Transformer> {
        return try {
            val transformerDTO = TransformerDTO.create(
                team = team,
                name = name,
                strength = strength,
                intelligence = intelligence,
                speed = speed,
                endurance = endurance,
                rank = rank,
                courage = courage,
                firepower = firepower,
                skill = skill
            )
            when (val result = remoteDataSource.create(token, transformerDTO)) {
                is Success -> Success(result.data.toDomain())
                is Failure -> Failure(result.flaw)
            }
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }

    override suspend fun delete(token: String, id: String): Result<Boolean> {
        return try {
            remoteDataSource.delete(token, id)
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }

    override suspend fun edit(
        token: String,
        id: String,
        team: String,
        name: String,
        strength: Int,
        intelligence: Int,
        speed: Int,
        endurance: Int,
        rank: Int,
        courage: Int,
        firepower: Int,
        skill: Int
    ): Result<Transformer> {
        return try {
            val transformerDTO = TransformerDTO.create(
                id = id,
                team = team,
                name = name,
                strength = strength,
                intelligence = intelligence,
                speed = speed,
                endurance = endurance,
                rank = rank,
                courage = courage,
                firepower = firepower,
                skill = skill
            )
            when (val result = remoteDataSource.edit(token, transformerDTO)) {
                is Success -> Success(result.data.toDomain())
                is Failure -> Failure(result.flaw)
            }
        } catch (throwable: Throwable) {
            Failure(Flaw(cause = throwable))
        }
    }
}