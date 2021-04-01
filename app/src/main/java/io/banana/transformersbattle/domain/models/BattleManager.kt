package io.banana.transformersbattle.domain.models

import android.util.Log

class BattleManager private constructor(
    val autobots: List<Transformer>,
    val decepticons: List<Transformer>
) {

    private val autobotsIterator: Iterator<Transformer> = autobots.iterator()
    private val decepticonsIterator: Iterator<Transformer> = decepticons.iterator()

    private lateinit var battle: Battle

    var winningTeam: Teams = Teams.Unknown
        private set
        @Throws(BattleHasNotBeenFoughtException::class)
        get() {
            return when {
                battlesFought == 0 -> throw BattleHasNotBeenFoughtException()
                winsForAutobots > winsForDecepticons -> Teams.Autobots
                winsForDecepticons > winsForAutobots -> Teams.Decepticons
                else -> Teams.Unknown
            }

        }
    val losingTeam: Teams
        get() = when (winningTeam) {
            Teams.Decepticons -> Teams.Autobots
            Teams.Autobots -> Teams.Decepticons
            else -> Teams.Unknown
        }

    var battlesFought: Int = 0
        private set

    private var winsForAutobots: Int = 0
    private var autobotsSurvivors = mutableListOf<Transformer>()
    private var winsForDecepticons: Int = 0
    private var decepticonsSurvivors = mutableListOf<Transformer>()

    fun fastBattles(): BattleResult {
        while (true) {
            getNextBattle()
            val result = getBattleResult()
            if (result is BattleFinished)
                return result
        }
    }

    fun getNextBattle(): Battle {
        battle = Battle(
            autobot = if (autobotsIterator.hasNext()) autobotsIterator.next() else null,
            deception = if (decepticonsIterator.hasNext()) decepticonsIterator.next() else null
        )

        return battle
    }

    @Throws(NoFightersException::class)
    fun getBattleResult(): BattleResult {
        val hasNextAutobot = battle.autobot != null
        val hasNextDeception = battle.deception != null

        if (battlesFought == 0 && !hasNextAutobot && !hasNextDeception)
            throw  NoFightersException()

        val battleResult =
            if (hasNextAutobot && hasNextDeception) {
                battlesFought++

                fight(
                    autobot = battle.autobot!!,
                    deception = battle.deception!!
                ).also {
                    computeResult(it)
                }
            } else if (hasNextAutobot && !hasNextDeception) {
                if (battlesFought == 0 || winsForAutobots == 0 && winsForDecepticons == 0) {
                    battlesFought++
                    computeResult(BattleWinner(battle.autobot!!, null))
                } else autobotsSurvivors.add(battle.autobot!!)

                finishBattle()
            } else if (!hasNextAutobot && hasNextDeception) {
                if (battlesFought == 0 || winsForAutobots == 0 && winsForDecepticons == 0) {
                    battlesFought++
                    computeResult(BattleWinner(battle.deception!!, null))
                } else decepticonsSurvivors.add(battle.deception!!)

                finishBattle()
            } else {
                finishBattle()
            }

        return if (battleResult is BattleCataclysm) finishBattle(true)
        else battleResult
    }

    private fun finishBattle(endsByCataclysm: Boolean = false): BattleFinished {
        if (endsByCataclysm) {
            destroyThemAll()
        } else {
            while (autobotsIterator.hasNext())
                autobotsSurvivors.add(autobotsIterator.next())
            while (decepticonsIterator.hasNext())
                decepticonsSurvivors.add(decepticonsIterator.next())
        }

        return BattleFinished(
            wasACataclysm = endsByCataclysm,
            winningTeam = winningTeam,
            losingTeam = losingTeam,
            battlesFought = battlesFought,
            winningSurvivors =
            when {
                endsByCataclysm -> emptyList()
                winningTeam == Teams.Autobots -> autobotsSurvivors
                else -> decepticonsSurvivors
            },
            losingSurvivors =
            when {
                endsByCataclysm -> emptyList()
                winningTeam == Teams.Autobots -> decepticonsSurvivors
                else -> autobotsSurvivors
            }
        )
    }

    private fun destroyThemAll() {
        decepticonsSurvivors.clear()
        autobotsSurvivors.clear()
    }

    private fun fight(autobot: Transformer, deception: Transformer): BattleResult {

        if (autobot.isOptimusPrime && deception.isOptimusPrime ||
            autobot.isPredaking && deception.isPredaking ||
            autobot.isOptimusPrime && deception.isPredaking ||
            autobot.isPredaking && deception.isOptimusPrime
        ) return BattleCataclysm

        if (autobot.isOptimusPrime) return BattleWinner(autobot, deception)
        if (deception.isOptimusPrime) return BattleWinner(deception, deception)
        if (deception.isPredaking) return BattleWinner(deception, autobot)
        if (autobot.isPredaking) return BattleWinner(autobot, deception)

        val overpower = evaluateOp(autobot, deception)
        val skill = evaluateSkills(autobot, deception)

        return when {
            overpower != null -> overpower
            skill != null -> skill
            else -> evaluateOverallRating(autobot, deception)
        }

    }

    private fun evaluateOp(autobot: Transformer, deception: Transformer): BattleResult? {
        return if (autobot.courage - deception.courage >= 4 && autobot.strength - deception.strength >= 3)
            BattleWinner(autobot, deception)
        else if (deception.courage - autobot.courage >= 4 && deception.strength - autobot.strength >= 3)
            BattleWinner(deception, autobot)
        else
            null
    }

    private fun evaluateSkills(autobot: Transformer, deception: Transformer): BattleResult? {
        return when {
            autobot.skill - deception.skill >= 3 -> BattleWinner(autobot, deception)
            deception.skill - autobot.skill >= 3 -> BattleWinner(deception, autobot)
            else -> null
        }
    }

    private fun evaluateOverallRating(autobot: Transformer, deception: Transformer): BattleResult {
        val autobotOverallRating = autobot.overallRating
        val decepticonOverallRating = deception.overallRating

        return when {
            autobotOverallRating == decepticonOverallRating -> BattleTie
            autobotOverallRating > decepticonOverallRating -> BattleWinner(autobot, deception)
            else -> BattleWinner(deception, autobot)
        }
    }

    private fun computeResult(result: BattleResult) {
        if (result is BattleWinner) {
            if (result.winner.team == Teams.Autobots) {
                autobotsSurvivors.add(result.winner)
                winsForAutobots++
            } else if (result.winner.team == Teams.Decepticons) {
                decepticonsSurvivors.add(result.winner)
                winsForDecepticons++
            }
        }
    }


    companion object {
        fun create(transformers: List<Transformer>): BattleManager {

            val autobots = arrayListOf<Transformer>()
            val decepticons = arrayListOf<Transformer>()

            transformers.forEach {
                when (it.team) {
                    Teams.Autobots -> autobots.add(it)
                    Teams.Decepticons -> decepticons.add(it)
                    Teams.Unknown -> Log.d("CREATING BATTLE", "TRANSFORMER IGNORED")
                }
            }
            autobots.sortByDescending { it.rank }
            decepticons.sortByDescending { it.rank }
            return BattleManager(autobots, decepticons)
        }
    }

}

class Battle(
    val autobot: Transformer?,
    val deception: Transformer?
)

sealed class BattleResult

class BattleWinner(
    val winner: Transformer,
    val loser: Transformer?,
) : BattleResult()

object BattleTie : BattleResult()
class BattleFinished(
    val wasACataclysm: Boolean,
    val winningTeam: Teams,
    val losingTeam: Teams,
    val battlesFought: Int,
    val winningSurvivors: List<Transformer>,
    val losingSurvivors: List<Transformer>,
) : BattleResult() {
    val wasATie = winningSurvivors.size == losingSurvivors.size
}

private object BattleCataclysm : BattleResult()

class BattleHasNotBeenFoughtException : Throwable()
class NoFightersException : Throwable()