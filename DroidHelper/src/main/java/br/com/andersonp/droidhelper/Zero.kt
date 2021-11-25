package br.com.andersonp.droidhelper

import br.com.andersonp.droidhelper.Chrono.intOfDay
import java.math.BigDecimal
import kotlin.random.Random

/**
 * Zero - The exact calculations nerd
 *
 * The methods here intend to allow simpler math calculations
 */
@Suppress("unused")
object Zero {

    /**
     * Round a Float number and returns it as a BigDecimal
     *
     * @param digits number of digits of the final number
     * @return rounded BigDecimal equivalent
     */
    fun Float.round(digits: Int = 2): BigDecimal {
        return toBigDecimal().setScale(digits, BigDecimal.ROUND_HALF_EVEN)
    }

    /**
     * Round a Double number and returns it as a BigDecimal
     *
     * @param digits number of digits of the final number
     * @return rounded BigDecimal equivalent
     */
    fun Double.round(digits: Int = 2): BigDecimal {
        return toBigDecimal().setScale(digits, BigDecimal.ROUND_HALF_EVEN)
    }

    /**
     * Gets the Ratio of two number given a base
     *
     * @param firstSize First number of the ratio calculation
     * @param secondSize Second number of the ratio calculation
     * @param base Number of the used as minimum base on the ratio
     * @return the ratio as string value
     */
    fun ratio(firstSize: Int, secondSize: Int, base: Int): String {

        val lesser: Int = listOf(firstSize, secondSize).minOrNull()!!

        val divider: Double = lesser.toDouble() / base
        return if (firstSize == lesser) {
            "(${secondSize/divider}:$base)"
        } else {
            "(${firstSize/divider}:$base)"
        }
    }

    /**
     * Generate a seed (class Random) for the current date
     *
     * @return object (as Random class) to be used as seed
     */
    fun seedOfDay(): Random {
        return Random(intOfDay())
    }
}