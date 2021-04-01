package io.banana.transformersbattle.domain.view_models

import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.models.*
import kotlinx.coroutines.delay

interface IBattleViewModel : IViewModel<Nothing> {

    fun onPrepareField()
    fun getFighters(): List<Transformer>
    fun onShowFighters(autobot: Transformer?, decepticon: Transformer?)
    fun onTie()
    fun onShowWinner(battleWinner: BattleWinner)
    fun onFinished(battleFinished: BattleFinished)

    suspend fun runBattles() {
        val battleManager = BattleManager.create(getFighters())

        while (true) {
            onPrepareField()
            val nextBattle = battleManager.getNextBattle()
            onShowFighters(nextBattle.autobot, nextBattle.deception)
            delay(1500)
            when (val battleResult = battleManager.getBattleResult()) {
                is BattleWinner -> onShowWinner(battleResult)
                is BattleTie -> onTie()
                is BattleFinished -> {
                    onFinished(battleResult)
                    break
                }
                else -> {
                    onError(Flaw("Unknown state"))
                    break
                }
            }
            delay(1500)
        }


    }


}