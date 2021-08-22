package br.com.andersonp.droidhelper

import java.text.SimpleDateFormat
import java.util.*

/**
 * Chrono - The manager of time
 *
 */
object Chrono {

    /**
     * Converts a milliseconds value to a Date in String format
     *
     * @param timeinMillies value of the time in Long format
     * @param pattern pattern which the date info will be filled with
     * @param locale locale where to base this date calculation, defaults to System locale
     * @return string with the date description in a readable format
     */
    fun millisecondsToDatestring(timeinMillies: Long, pattern: String = "yyyy-MM-dd HH:mm:ss", locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(Date(timeinMillies))
    }

    /**
     * Find the specific Long that represents the given date
     *
     * @param year the year to be used
     * @param month the month to be used
     * @param day the day to be used
     * @param hour (optional) the hour to be used
     * @param minute (optional) the minute to be used
     * @param seconds (optional) the seconds to be used
     * @return the date as Long format
     */
    fun dateToMilliseconds(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, seconds: Int = 0): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute, seconds)
        return calendar.timeInMillis
    }

    /**
     * Return get date/time of [qty] minutes ahead of the current date/time
     *
     * @param qty the number of minutes ahead
     * @return the calculated date/time
     */
    fun minutesAhead(qty: Int): Long {
        val calendar: Calendar = Calendar.getInstance()
        return calendar.timeInMillis + (qty * 1000 * 60)
    }

    /**
     * Returns the date format of now
     *
     * @param pattern the date pattern to be used as template
     * @param locale
     * @return locale where to base this date calculation, defaults to System locale
     */
    fun today(pattern: String = "dd/MM/yyyy", locale: Locale = Locale.getDefault()): String {
        return SimpleDateFormat(pattern, locale).format(Calendar.getInstance().time)
    }

    /**
     * Returns an integer representation of the current date, useful for day-related calculations
     * that are are required to be repetitive, as a seed (Random methods)
     *
     * @return an Int number generated for this day
     */
    fun intOfDay(): Int {
        return today(pattern="yyyyMMdd").toInt()
    }

    /**
     * Returns an integer representation of the given date, useful for day-related calculations
     * that are are required to be repetitive, as a seed (Random methods)
     *
     * @return an Int number generated for the given day
     */
    fun intOfDay(date: Date): Int {
        return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date).toInt()
    }

}