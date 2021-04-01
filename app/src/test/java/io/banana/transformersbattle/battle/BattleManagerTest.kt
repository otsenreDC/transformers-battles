package io.banana.transformersbattle.battle

import com.google.common.truth.Truth.assertThat
import io.banana.transformersbattle.domain.models.*
import org.junit.Test

class BattleManagerTest {

    /*
    Input
    Soundwave, D, 8,9,2,6,7,5,6,10
    Bluestreak, A, 6,6,7,9,5,2,9,7
    Hubcap: A, 4,4,4,4,4,4,4,4

    Output
    1 battle
    Winning team (Decepticons): Soundwave
    Survivors from the losing team (Autobots): Hubcap
     */
    @Test
    fun `sample battle`() {
        val decepticons = listOf(
            newTransformer(
                name = "Soundwave",
                team = Teams.Decepticons,
                strength = 8,
                intelligence = 9,
                speed = 2,
                endurance = 6,
                rank = 7,
                courage = 5,
                firepower = 6,
                skill = 10
            )
        )
        val autobots = listOf(
            newTransformer(
                name = "Bluestreak",
                team = Teams.Autobots,
                strength = 6,
                intelligence = 6,
                speed = 7,
                endurance = 9,
                rank = 5,
                courage = 2,
                firepower = 9,
                skill = 7
            ),
            newTransformer(
                name = "Hubcap",
                team = Teams.Autobots,
                strength = 4,
                intelligence = 4,
                speed = 4,
                endurance = 4,
                rank = 4,
                courage = 4,
                firepower = 4,
                skill = 4
            )
        )

        val manager = BattleManager.create(autobots + decepticons)
        val battleResult = manager.fastBattles() as BattleFinished

        assertThat(battleResult.battlesFought).isEqualTo(1)
        assertThat(battleResult.winningTeam).isAtLeast(Teams.Decepticons)
        assertThat(battleResult.winningSurvivors.size).isEqualTo(1)
        assertThat(battleResult.winningSurvivors[0].name).isEqualTo("Soundwave")
        assertThat(battleResult.losingSurvivors.size).isEqualTo(1)
        assertThat(battleResult.losingSurvivors[0].name).isEqualTo("Hubcap")
    }

    @Test(expected = NoFightersException::class)
    fun `NO autobots and NO decepticons, throws an exception`() {
        val autobots = emptyList<Transformer>()
        val decepticons = emptyList<Transformer>()

        val manager = BattleManager.create(autobots + decepticons)
        manager.fastBattles()
    }

    // when A=1 and D=0, A winds
    @Test
    fun `1 autobot and NO decepticons, Autobots won the battle`() {
        val autobots = listOf(
            newTransformer(Teams.Autobots, courage = 5, strength = 4)
        )
        val decepticons = emptyList<Transformer>()

        val manager = BattleManager.create(autobots + decepticons)
        manager.fastBattles()
        val result = manager.winningTeam

        assertThat(result).isEqualTo(Teams.Autobots)
    }

    // when A=0 and D=1, D wins
    @Test
    fun `1 decepticon and NO autobots, Decepticons won the battle`() {
        val autobots = emptyList<Transformer>()
        val decepticons = listOf(
            newTransformer(Teams.Decepticons, courage = 5, strength = 4)
        )

        val manager = BattleManager.create(autobots + decepticons)
        manager.fastBattles()
        val result = manager.winningTeam

        assertThat(result).isEqualTo(Teams.Decepticons)
    }

    @Test
    fun `1 autobot won the battle`() {
        val autobots = listOf(
            newTransformer(Teams.Autobots, courage = 5, strength = 4)
        )
        val decepticons = listOf(
            newTransformer(Teams.Decepticons, courage = 1, strength = 1),
            newTransformer(Teams.Decepticons, skill = 1)
        )

        val manager = BattleManager.create(autobots + decepticons)
        manager.fastBattles()
        val result = manager.winningTeam

        assertThat(result).isEqualTo(Teams.Autobots)
    }

    @Test
    fun `1 decepticon WON the battle`() {
        val autobots = listOf(
            newTransformer(Teams.Autobots, courage = 1, strength = 1),
            newTransformer(Teams.Autobots, skill = 1)
        )
        val decepticons = listOf(
            newTransformer(Teams.Decepticons, courage = 5, strength = 4)
        )

        val manager = BattleManager.create(autobots + decepticons)
        val result = manager.fastBattles() as BattleFinished

        assertThat(result.battlesFought).isEqualTo(1)
        assertThat(result.winningTeam).isAtLeast(Teams.Decepticons)
        assertThat(result.winningSurvivors.size).isEqualTo(1)
        assertThat(result.losingSurvivors.size).isEqualTo(1)
    }

    @Test
    fun `autobots WON the battle`() {
        val autobots = listOf(
            newTransformer(Teams.Autobots, courage = 5, strength = 4),
            newTransformer(Teams.Autobots, skill = 4)
        )
        val decepticons = listOf(
            newTransformer(Teams.Decepticons, courage = 1, strength = 1),
            newTransformer(Teams.Decepticons, skill = 1)
        )

        val manager = BattleManager.create(autobots + decepticons)
        val result = manager.fastBattles() as? BattleFinished

        assertThat(result!!.winningTeam).isEqualTo(Teams.Autobots)
    }

    @Test
    fun `decepticons WON the battle`() {
        val autobots = listOf(
            newTransformer(Teams.Autobots, courage = 1, strength = 1),
            newTransformer(Teams.Autobots, skill = 1)
        )
        val decepticons = listOf(
            newTransformer(Teams.Decepticons, courage = 5, strength = 4),
            newTransformer(Teams.Decepticons, skill = 4)
        )

        val manager = BattleManager.create(autobots + decepticons)
        val result = manager.fastBattles() as? BattleFinished

        assertThat(result!!.winningTeam).isEqualTo(Teams.Decepticons)
    }

    @Test
    fun `there is a tie in the battle`() {
        val autobots = listOf(
            newTransformer(Teams.Autobots, courage = 5, strength = 4),
            newTransformer(Teams.Autobots, skill = 1)
        )
        val decepticons = listOf(
            newTransformer(Teams.Decepticons, courage = 1, strength = 1),
            newTransformer(Teams.Decepticons, skill = 4)
        )

        val manager = BattleManager.create(autobots + decepticons)
        val result = manager.fastBattles() as? BattleFinished

        assertThat(result!!.winningTeam).isEqualTo(Teams.Unknown)
    }

    // If 4+ down of courage and 3+ of strength, automatically wins
    @Test
    fun `autobots can win by OVERPOWER`() {
        val autobot = newTransformer(Teams.Autobots, courage = 5, strength = 4)
        val decepticon = newTransformer(Teams.Decepticons, courage = 1, strength = 1)

        val manager = BattleManager.create(listOf(autobot, decepticon))

        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner).isEqualTo(autobot)
    }

    // If 4+ down of courage and 3+ of strength, automatically wins
    @Test
    fun `decepticons can win by OVERPOWER`() {
        val autobot = newTransformer(Teams.Autobots, courage = 1, strength = 1)
        val decepticon = newTransformer(Teams.Decepticons, courage = 5, strength = 4)

        val manager = BattleManager.create(listOf(autobot, decepticon))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner).isEqualTo(decepticon)
    }

    // If 3+ skill win the battle
    @Test
    fun `autobots can win by SKILL`() {
        val autobot = newTransformer(Teams.Autobots, skill = 4)
        val decepticon = newTransformer(Teams.Decepticons, skill = 1)

        val manager = BattleManager.create(listOf(autobot, decepticon))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner).isEqualTo(autobot)
    }

    // If 3+ skill win the battle
    @Test
    fun `decepticons can win by SKILL`() {
        val autobot = newTransformer(Teams.Autobots, skill = 1)
        val decepticon = newTransformer(Teams.Decepticons, skill = 4)

        val manager = BattleManager.create(listOf(autobot, decepticon))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner).isEqualTo(decepticon)
    }

    @Test
    fun `autobots can win by overall rating`() {
        val autobot = newTransformer(
            Teams.Autobots,
            strength = 8,
            intelligence = 9,
            speed = 2,
            endurance = 6,
            firepower = 7
        )
        val decepticon = newTransformer(
            Teams.Decepticons,
            strength = 4,
            intelligence = 4,
            speed = 4,
            endurance = 4,
            firepower = 4
        )

        val manager = BattleManager.create(listOf(autobot, decepticon))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner).isEqualTo(autobot)
    }

    @Test
    fun `decepticons can win by overall rating`() {
        val autobot = newTransformer(
            Teams.Autobots,
            strength = 4,
            intelligence = 4,
            speed = 4,
            endurance = 4,
            firepower = 4
        )
        val decepticon = newTransformer(
            Teams.Decepticons,
            strength = 8,
            intelligence = 9,
            speed = 2,
            endurance = 6,
            firepower = 7
        )

        val manager = BattleManager.create(listOf(autobot, decepticon))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner).isEqualTo(decepticon)
    }

    // On tie, both are destroyed
    @Test
    fun `when both have the same overall rating is a tie`() {
        val autobot = newTransformer(
            Teams.Autobots,
            strength = 4,
            intelligence = 4,
            speed = 4,
            endurance = 4,
            firepower = 4
        )
        val decepticon = newTransformer(
            Teams.Decepticons,
            strength = 4,
            intelligence = 4,
            speed = 4,
            endurance = 4,
            firepower = 4
        )

        val manager = BattleManager.create(listOf(autobot, decepticon))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleTie

        assertThat(result).isNotNull()
    }


    @Test
    fun `when 2 Autobots and 1 Decepticon there is ONE combat`() {
        val autobots = (1..2).map { newTransformer(Teams.Autobots, rank = it) }
        val decepticons = newTransformer(Teams.Decepticons, rank = 2, skill = 10)
        val transformers = autobots + decepticons

        val expected = 1

        val manager = BattleManager.create(transformers)
        manager.fastBattles()
        val result = manager.battlesFought

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `when 1 Autobot and 2 Decepticons there is ONE combat`() {
        val autobots = (1..2).map { newTransformer(Teams.Autobots, rank = it, skill = it) }
        val decepticons = newTransformer(Teams.Decepticons, rank = 4, skill = 10)
        val transformers = autobots + decepticons

        val expected = 1

        val manager = BattleManager.create(transformers)
        manager.fastBattles()
        val result = manager.battlesFought

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `when 10 Autobots and 4 Decepticons there is FOUR combat`() {
        val autobots = (1..10).map { newTransformer(Teams.Autobots, rank = it, skill = it) }
        val decepticons = (1..4).map { newTransformer(Teams.Decepticons, rank = it) }
        val transformers = autobots + decepticons

        val expected = 4

        val manager = BattleManager.create(transformers)
        manager.fastBattles()
        val result = manager.battlesFought

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `when 7 Autobots and 6 Decepticons there is SIX combat`() {
        val autobots = (1..7).map { newTransformer(Teams.Autobots, rank = it, skill = it) }
        val decepticons = (1..6).map { newTransformer(Teams.Decepticons, rank = it) }
        val transformers = autobots + decepticons

        val expected = 6

        val manager = BattleManager.create(transformers)
        manager.fastBattles()
        val result = manager.battlesFought

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `battle ends on fastBattles when a cataclysm`() {
        val autobots = (2..4).map { newTransformer(Teams.Autobots, rank = it) }
        val decepticons = (2..4).map { newTransformer(Teams.Decepticons, rank = it) }
        val transformers = listOf(
            newTransformer(
                team = Teams.Autobots,
                name = "Optimus Prime",
                rank = 10
            ),
            newTransformer(
                team = Teams.Decepticons,
                name = "Predaking",
                rank = 10
            ),
        ) + autobots + decepticons

        val manager = BattleManager.create(transformers)
        val result = manager.fastBattles() as BattleFinished

        assertThat(result.wasACataclysm).isTrue()
        assertThat(result.battlesFought).isEqualTo(1)
        assertThat(result.winningSurvivors.size).isEqualTo(0)
        assertThat(result.losingSurvivors.size).isEqualTo(0)
    }

    @Test
    fun `can run fast battles`() {
        val autobots = (1..10).map { newTransformer(Teams.Autobots, rank = it, skill = it) }
        val decepticons = (1..4).map { newTransformer(Teams.Decepticons, rank = it) }
        val transformers = autobots + decepticons

        val expected = 4

        val manager = BattleManager.create(transformers)
        manager.fastBattles()
        val result = manager.battlesFought

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `teams are sorted descending`() {
        val autobots = arrayListOf(3, 5, 2, 6, 6, 2).map {
            newTransformer(Teams.Autobots, rank = it)
        }
        val decepticons = arrayListOf(2, 3, 5, 7, 6, 2, 9).map {
            newTransformer(Teams.Decepticons, rank = it)
        }
        val input = autobots + decepticons

        val expectedAutobots = listOf(6, 6, 5, 3, 2, 2)
        val expectedDecepticons = listOf(9, 7, 6, 5, 3, 2, 2)

        val manager = BattleManager.create(input)

        expectedAutobots.forEachIndexed { index, expected ->
            assertThat(manager.autobots[index].rank).isEqualTo(expected)
        }

        expectedDecepticons.forEachIndexed { index, expected ->
            assertThat(manager.decepticons[index].rank).isEqualTo(expected)
        }
    }

    // SURVIVORS

    @Test
    fun `OPTIMUS PRIME as autobot always wins`() {
        val optimusPrime = newTransformer(team = Teams.Autobots, name = "Optimus Prime")
        val decepticons = (1..4).map { newTransformer(Teams.Decepticons, rank = it) }

        val manager = BattleManager.create(listOf(optimusPrime) + decepticons)
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner.isOptimusPrime).isTrue()
    }

    @Test
    fun `OPTIMUS PRIME as decepticon always wins`() {
        val autobots = (1..4).map { newTransformer(Teams.Autobots, rank = it) }
        val optimusPrime = newTransformer(team = Teams.Decepticons, name = "Optimus Prime")

        val manager = BattleManager.create(listOf(optimusPrime) + autobots)
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner.isOptimusPrime).isTrue()
    }

    @Test
    fun `PREDAKING as decepticon always wins`() {
        val autobots = (1..4).map { newTransformer(Teams.Autobots, rank = it) }
        val predaking = newTransformer(team = Teams.Decepticons, name = "Predaking")

        val manager = BattleManager.create(listOf(predaking) + autobots)
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner.isPredaking).isTrue()
    }

    @Test
    fun `PREDAKING as autobot always wins`() {
        val predaking = newTransformer(team = Teams.Autobots, name = "Predaking")
        val decepticons = (1..4).map { newTransformer(Teams.Decepticons, rank = it) }

        val manager = BattleManager.create(listOf(predaking) + decepticons)
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleWinner

        assertThat(result).isNotNull()
        assertThat(result!!.winner.isPredaking).isTrue()
    }

    @Test
    fun `OPTIMUS PRIME vs OPTIMUS PRIME is a CATACLYSM`() {
        val optimusPrime = newTransformer(team = Teams.Autobots, name = "Optimus Prime")
        val optimusPrimeD = newTransformer(team = Teams.Decepticons, name = "Optimus Prime")

        val manager = BattleManager.create(listOf(optimusPrime) + listOf(optimusPrimeD))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleFinished

        assertThat(result).isNotNull()
        assertThat(result!!.wasACataclysm).isTrue()
    }

    @Test
    fun `PREDAKING vs PREDAKING is a CATACLYSM`() {
        val predakingA = newTransformer(team = Teams.Autobots, name = "Predaking")
        val predaking = newTransformer(team = Teams.Decepticons, name = "Predaking")

        val manager = BattleManager.create(listOf(predakingA) + listOf(predaking))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleFinished

        assertThat(result).isNotNull()
        assertThat(result!!.wasACataclysm).isTrue()
    }

    @Test
    fun `OPTIMUS PRIME as Autobot vs PREDAKING as Decepticon is a CATACLYSM`() {
        val optimusPrime = newTransformer(team = Teams.Autobots, name = "Optimus Prime")
        val predaking = newTransformer(team = Teams.Decepticons, name = "Predaking")

        val manager = BattleManager.create(listOf(optimusPrime) + listOf(predaking))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleFinished

        assertThat(result).isNotNull()
        assertThat(result!!.wasACataclysm).isTrue()
    }

    @Test
    fun `OPTIMUS PRIME as Decepticon vs PREDAKING as Autobot is a CATACLYSM`() {
        val optimusPrime = newTransformer(team = Teams.Decepticons, name = "Optimus Prime")
        val predaking = newTransformer(team = Teams.Autobots, name = "Predaking")

        val manager = BattleManager.create(listOf(optimusPrime) + listOf(predaking))
        manager.getNextBattle()
        val result = manager.getBattleResult() as? BattleFinished

        assertThat(result).isNotNull()
        assertThat(result!!.wasACataclysm).isTrue()
    }

    @Test
    fun `there is no survivors when a cataclysm`() {
        val autobots = listOf(
            newTransformer(Teams.Autobots, courage = 5, strength = 4),
            newTransformer(Teams.Autobots, skill = 4)
        )
        val decepticons = listOf(
            newTransformer(Teams.Decepticons, courage = 1, strength = 1),
            newTransformer(Teams.Decepticons, skill = 1)
        )
        val transformers = listOf(
            newTransformer(
                team = Teams.Autobots,
                name = "Optimus Prime",
                rank = 10
            ),
            newTransformer(
                team = Teams.Decepticons,
                name = "Predaking",
                rank = 10
            ),
        ) + autobots + decepticons

        val manager = BattleManager.create(transformers)
        val result = manager.fastBattles() as? BattleFinished

        assertThat(result).isNotNull()
        assertThat(result?.wasACataclysm).isTrue()
        assertThat(result?.winningSurvivors).isEmpty()
        assertThat(result?.losingSurvivors).isEmpty()
    }

    @Test
    fun `survive SIX autobots`() {
        val autobots = (1..10).map { newTransformer(Teams.Autobots, rank = it) }
        val decepticons = (1..4).map { newTransformer(Teams.Decepticons, rank = it) }
        val transformers = autobots + decepticons

        val expected = 5

        val manager = BattleManager.create(transformers)
        val result = manager.fastBattles() as BattleFinished

        assertThat(result.battlesFought).isEqualTo(expected)
        assertThat(result.winningSurvivors.size).isEqualTo(6)
        assertThat(result.losingSurvivors.size).isEqualTo(0)
    }

    private fun newTransformer(
        team: Teams,
        name: String = team.name,
        rank: Int = 0,
        courage: Int = 0,
        strength: Int = 0,
        skill: Int = 0,
        intelligence: Int = 0,
        speed: Int = 0,
        endurance: Int = 0,
        firepower: Int = 0,
    ): Transformer =
        Transformer(
            id = "",
            teamIcon = "",
            team = team,
            name = name,
            strength = strength,
            intelligence = intelligence,
            speed = speed,
            endurance = endurance,
            firepower = firepower,
            rank = rank,
            courage = courage,
            skill = skill
        )


}