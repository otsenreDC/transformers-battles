package io.banana.transformersbattle.domain.use_cases

import io.banana.transformersbattle.domain.flaws.NoAvailableTokenFlaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.domain.repositories.ITransformersRepository

class CreateTransformersUseCase(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val transformersRepository: ITransformersRepository
) {
    suspend fun execute(
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

        val token = getToken() ?: return Failure(NoAvailableTokenFlaw())

        return transformersRepository.create(
            token,
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
    }

    private suspend fun getToken(): String? {
        return when (val result = getAccessTokenUseCase.execute()) {
            is Success -> result.data
            is Failure -> null
        }
    }
}