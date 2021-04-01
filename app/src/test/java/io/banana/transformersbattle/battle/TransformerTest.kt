package io.banana.transformersbattle.battle

import com.google.common.truth.Truth.assertThat
import io.banana.transformersbattle.domain.models.Teams
import io.banana.transformersbattle.domain.models.Transformer
import org.junit.Test

class TransformerTest {

    @Test
    fun `the overall rating is 15`() {
        val input = Transformer(
            id = null,
            team = Teams.Decepticons,
            name = Teams.Decepticons.name,
            strength = 1,
            intelligence = 2,
            speed = 3,
            endurance = 4,
            firepower = 5,
            rank = 6,
            courage = 7,
            skill = 8
        )
        val expected = 15

        val overallRating = input.overallRating

        assertThat(overallRating).isEqualTo(expected)
    }

    @Test
    fun `the overall rating is 30`() {
        val input = Transformer(
            id = null,
            team = Teams.Decepticons,
            name = Teams.Decepticons.name,
            strength = 8,
            intelligence = 7,
            speed = 6,
            endurance = 5,
            firepower = 4,
            rank = 3,
            courage = 2,
            skill = 1
        )
        val expected = 30

        val overallRating = input.overallRating

        assertThat(overallRating).isEqualTo(expected)
    }

    @Test
    fun `the overall rating is 0`() {
        val input = Transformer(
            id = null,
            team = Teams.Decepticons,
            name = Teams.Decepticons.name,
            strength = 0,
            intelligence = 0,
            speed = 0,
            endurance = 0,
            firepower = 0,
            rank = 0,
            courage = 0,
            skill = 0
        )
        val expected = 0

        val overallRating = input.overallRating

        assertThat(overallRating).isEqualTo(expected)
    }

    @Test
    fun `the overall rating is 2`() {
        val input = Transformer(
            id = null,
            team = Teams.Decepticons,
            name = Teams.Decepticons.name,
            strength = 1,
            intelligence = 1,
            speed = 0,
            endurance = 0,
            firepower = 0,
            rank = 8,
            courage = 7,
            skill = 8
        )
        val expected = 2

        val overallRating = input.overallRating

        assertThat(overallRating).isEqualTo(expected)
    }

    @Test
    fun `is optimus prime`() {
        val optimusPrime = Transformer(
        id = null,
        team = Teams.Autobots,
        name = "optimus prime",
        strength = 1,
        intelligence = 1,
        speed = 0,
        endurance = 0,
        firepower = 0,
        rank = 8,
        courage = 7,
        skill = 8
        )

        val result = optimusPrime.isOptimusPrime

        assertThat(result).isTrue()
    }

    @Test
    fun `is Optimus Prime`() {
        val optimusPrime = Transformer(
            id = null,
            team = Teams.Autobots,
            name = "Optimus Prime",
            strength = 1,
            intelligence = 1,
            speed = 0,
            endurance = 0,
            firepower = 0,
            rank = 8,
            courage = 7,
            skill = 8
        )

        val result = optimusPrime.isOptimusPrime

        assertThat(result).isTrue()
    }

    @Test
    fun `is OPTIMUS PRIME`() {
        val optimusPrime = Transformer(
            id = null,
            team = Teams.Autobots,
            name = "OPTIMUS PRIME",
            strength = 1,
            intelligence = 1,
            speed = 0,
            endurance = 0,
            firepower = 0,
            rank = 8,
            courage = 7,
            skill = 8
        )

        val result = optimusPrime.isOptimusPrime

        assertThat(result).isTrue()
    }

    @Test
    fun `is predaking`() {
        val optimusPrime = Transformer(
            id = null,
            team = Teams.Autobots,
            name = "predaking",
            strength = 1,
            intelligence = 1,
            speed = 0,
            endurance = 0,
            firepower = 0,
            rank = 8,
            courage = 7,
            skill = 8
        )

        val result = optimusPrime.isPredaking

        assertThat(result).isTrue()
    }

    @Test
    fun `is Predaking`() {
        val optimusPrime = Transformer(
            id = null,
            team = Teams.Autobots,
            name = "Predaking",
            strength = 1,
            intelligence = 1,
            speed = 0,
            endurance = 0,
            firepower = 0,
            rank = 8,
            courage = 7,
            skill = 8
        )

        val result = optimusPrime.isPredaking

        assertThat(result).isTrue()
    }

    @Test
    fun `is PREDAKING`() {
        val optimusPrime = Transformer(
            id = null,
            team = Teams.Autobots,
            name = "PREDAKING",
            strength = 1,
            intelligence = 1,
            speed = 0,
            endurance = 0,
            firepower = 0,
            rank = 8,
            courage = 7,
            skill = 8
        )

        val result = optimusPrime.isPredaking

        assertThat(result).isTrue()
    }


}