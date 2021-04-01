package io.banana.transformersbattle.framework.ui.battle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.models.BattleFinished
import io.banana.transformersbattle.domain.models.BattleWinner
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.domain.view_models.IBattleViewModel
import kotlinx.coroutines.launch

class BattleViewModel : ViewModel(), IBattleViewModel {

    val onShowStartBattleButton = MutableLiveData<Boolean>()
    val onShowFightResult = MutableLiveData<FightResultLabels>()
    val onPrepareField = MutableLiveData<Boolean>()
    val onShowFighters = MutableLiveData<Fighters?>()
    val onClose = MutableLiveData<Boolean>()
    val onTie = MutableLiveData<Boolean>()
    val onError = MutableLiveData<String>()

    val onFinished = MutableLiveData<BattleResultsLabels>()

    var transformers: List<Transformer> = emptyList()
        private set

    override fun getFighters(): List<Transformer> {
        return transformers
    }

    override fun onPrepareField() {
        onShowFightResult.postValue(FightResultLabels.CLEARED)
        onShowFighters.postValue(Fighters(null, null))
        onTie.postValue(false)
    }

    override fun onShowFighters(autobot: Transformer?, decepticon: Transformer?) {
        onShowFighters.postValue(Fighters(autobot, decepticon))
    }

    override fun onTie() {
        onTie.postValue(true)
    }

    override fun onShowWinner(battleWinner: BattleWinner) {
        onShowFightResult.postValue(
            FightResultLabels.create(
                autobotHasWon = battleWinner.winner.isAutobot,
                decepticonHasWon = battleWinner.winner.isDecepticon
            )
        )
    }

    override fun onFinished(battleFinished: BattleFinished) {
        onFinished.postValue(BattleResultsLabels.create(battleFinished) {
            onClose.postValue(true)
        })
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun onDone(data: Nothing) {

    }

    override fun onError(flaw: Flaw?) {
        onError.postValue("An error happened during the battle.")
    }

    fun init(transformers: List<Transformer>) {
        onShowStartBattleButton.postValue(true)
        this.transformers = transformers
        onPrepareField()
    }

    fun startBattle() {
        onShowStartBattleButton.postValue(false)
        viewModelScope.launch {
            runBattles()
        }
    }

    class Fighters(
        val autobot: Transformer?,
        val decepticon: Transformer?
    )

    class FightResultLabels(
        val autobotWon: Boolean,
        val autobotLost: Boolean,
        val decepticonWon: Boolean,
        val decepticonLost: Boolean,
    ) {
        companion object {
            fun create(autobotHasWon: Boolean, decepticonHasWon: Boolean): FightResultLabels {
                return FightResultLabels(
                    autobotWon = autobotHasWon,
                    autobotLost = !autobotHasWon,
                    decepticonWon = decepticonHasWon,
                    decepticonLost = !decepticonHasWon
                )
            }

            val CLEARED: FightResultLabels
                get() = FightResultLabels(
                    autobotWon = false,
                    autobotLost = false,
                    decepticonWon = false,
                    decepticonLost = false
                )
        }
    }

    class BattleResultsLabels(
        val battlesFought: String,
        val winningTeam: String,
        val winningSurvivors: String,
        val losingTeam: String,
        val losingSurvivors: String,
        val tie: Boolean,
        val cataclysm: Boolean,
        val close: () -> Unit
    ) {
        companion object {
            fun create(
                battleFinished: BattleFinished,
                closeScreen: () -> Unit
            ): BattleResultsLabels {
                return BattleResultsLabels(
                    battlesFought = "${battleFinished.battlesFought}",
                    winningTeam = battleFinished.winningTeam.name,
                    winningSurvivors = createSurvivorsContent(battleFinished.winningSurvivors),
                    losingTeam = battleFinished.losingTeam.name,
                    losingSurvivors = createSurvivorsContent(battleFinished.losingSurvivors),
                    tie = battleFinished.wasATie && !battleFinished.wasACataclysm,
                    cataclysm = battleFinished.wasACataclysm,
                    close = closeScreen
                )
            }

            private fun createSurvivorsContent(transformers: List<Transformer>): String {
                if (transformers.isEmpty()) return "No survivors"

                val survivors = StringBuilder()

                transformers.forEach {
                    survivors.appendLine(it.name)
                }

                return survivors.toString().trimEnd()
            }
        }
    }
}