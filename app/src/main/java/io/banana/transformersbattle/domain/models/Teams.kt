package io.banana.transformersbattle.domain.models

enum class Teams(val designator: String) {
    Autobots("A"),
    Decepticons("D"),
    Unknown("U")
}

object TeamsHelper {
    fun fromString(team: String?): Teams {
        return when (team) {
            "A" -> Teams.Autobots
            "D" -> Teams.Decepticons
            else -> Teams.Unknown
        }
    }
}