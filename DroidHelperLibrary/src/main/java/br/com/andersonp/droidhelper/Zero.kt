package br.com.andersonp.droidhelper

import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
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


    /**
     * Returns an integer representation of the given date (or today, if date not given), useful for
     * day-related calculations that are required to be repeatable, as a seed (Random methods)
     *
     * @return an Int number generated for the given day
     */
    fun intOfDay(date: LocalDate? = null): Int {
        date?.let {
            return DateTimeFormatter.ofPattern("yyyyMMdd", Locale.getDefault()).format(date).toInt()
        } ?: run {
            return Chrono.todayText(dateFormat = "yyyyMMdd").toInt()
        }
    }

}