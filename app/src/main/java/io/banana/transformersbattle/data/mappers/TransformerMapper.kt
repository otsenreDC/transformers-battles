package io.banana.transformersbattle.data.mappers

import io.banana.transformersbattle.data.remote.dtos.TransformerDTO
import io.banana.transformersbattle.domain.models.TeamsHelper
import io.banana.transformersbattle.domain.models.Transformer

fun TransformerDTO.toDomain(): Transformer =
    Transformer(
        id = this.id ?: "",
        team = TeamsHelper.fromString(this.team),
        name = this.name ?: "",
        strength = this.strength ?: 0,
        intelligence = this.intelligence ?: 0,
        speed = this.speed ?: 0,
        endurance = this.endurance ?: 0,
        rank = this.rank ?: 0,
        courage = this.courage ?: 0,
        firepower = this.firepower ?: 0,
        skill = this.skill ?: 0,
        teamIcon = this.teamIcon ?: ""
    )
