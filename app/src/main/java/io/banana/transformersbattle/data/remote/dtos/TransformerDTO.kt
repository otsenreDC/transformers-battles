package io.banana.transformersbattle.data.remote.dtos

import com.google.gson.annotations.SerializedName

class TransformerDTO {
    var id: String? = null
        private set
    var team: String? = null
        private set
    var name: String? = null
        private set
    var strength: Int? = null
        private set
    var intelligence: Int? = null
        private set
    var speed: Int? = null
        private set
    var endurance: Int? = null
        private set
    var rank: Int? = null
        private set
    var courage: Int? = null
        private set
    var firepower: Int? = null
        private set
    var skill: Int? = null
        private set

    @SerializedName("team_icon")
    var teamIcon: String? = null
        private set

    companion object {
        fun create(
            id: String? = null,
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
        ): TransformerDTO {
            return TransformerDTO().apply {
                this.id = id
                this.name = name
                this.team = team
                this.strength = strength
                this.intelligence = intelligence
                this.speed = speed
                this.endurance = endurance
                this.rank = rank
                this.courage = courage
                this.firepower = firepower
                this.skill = skill
            }
        }
    }
}