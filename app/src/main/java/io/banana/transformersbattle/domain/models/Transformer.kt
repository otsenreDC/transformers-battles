package io.banana.transformersbattle.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transformer(
    val id: String,
    val team: Teams,
    val name: String,
    val strength: Int,
    val intelligence: Int,
    val speed: Int,
    val endurance: Int,
    val rank: Int,
    val courage: Int,
    val firepower: Int,
    val skill: Int,
    val teamIcon: String
) : Parcelable {
    val overallRating: Int
        get() = strength + intelligence + speed + endurance + firepower

    val isOptimusPrime: Boolean
        get() = name.equals("OPTIMUS PRIME", true)

    val isPredaking: Boolean
        get() = name.equals("PREDAKING", true)

    val isAutobot: Boolean
        get() = team == Teams.Autobots

    val isDecepticon: Boolean
        get() = team == Teams.Decepticons
}