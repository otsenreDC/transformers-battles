package io.banana.transformersbattle.domain.repositories

import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Transformer

interface ITransformersRepository {
    suspend fun list(accessToken: String): Result<List<Transformer>>
    suspend fun create(
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
    ): Result<Transformer>

    suspend fun edit(
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
    ): Result<Transformer>

    suspend fun delete(token: String, id: String): Result<Boolean>
}