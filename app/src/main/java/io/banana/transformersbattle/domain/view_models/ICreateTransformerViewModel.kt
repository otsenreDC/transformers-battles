package io.banana.transformersbattle.domain.view_models

import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Result
import io.banana.transformersbattle.domain.models.Teams
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.domain.use_cases.CreateTransformersUseCase
import io.banana.transformersbattle.domain.use_cases.EditTransformersUseCase

interface ICreateTransformerViewModel : IViewModel<Transformer> {

    val createTransformersUseCase: CreateTransformersUseCase
    val editTransformersUseCase: EditTransformersUseCase

    suspend fun isValidaData(
        team: Teams?,
        name: String,
        strength: Int,
        intelligence: Int,
        speed: Int,
        endurance: Int,
        rank: Int,
        courage: Int,
        firepower: Int,
        skill: Int
    ): Boolean {
        return team != null &&
                name.isNotBlank() &&
                strength.inRange() &&
                intelligence.inRange() &&
                speed.inRange() &&
                endurance.inRange() &&
                rank.inRange() &&
                courage.inRange() &&
                firepower.inRange() &&
                skill.inRange()
    }

    suspend fun createTransformer(
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
        try {
            return createTransformersUseCase.execute(
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
        } catch (throwable: Throwable) {
            return Failure(Flaw(cause = throwable))
        }
    }


    private fun Int.inRange(): Boolean {
        return this in 1..10
    }
}