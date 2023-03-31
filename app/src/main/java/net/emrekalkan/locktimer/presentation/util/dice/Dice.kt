package net.emrekalkan.locktimer.presentation.util.dice

import kotlin.random.Random

object Dice {

    fun roll(diceChance: DiceChance = DiceChance.MEDIUM): Boolean {
        return Random.nextInt(diceChance.seed) == 0
    }
}