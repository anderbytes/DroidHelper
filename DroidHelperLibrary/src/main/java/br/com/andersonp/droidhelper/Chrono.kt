package br.com.andersonp.droidhelper

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Chrono - The manager of time
 *
 */
@Suppress("unused")
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
        calendar.set(year, month - 1, day, hour, minute, seconds)
        return calendar.timeInMillis
    }

    /**
     * Return get date/time of [qty] minutes ahead of the current date/time
     *
     * @param qty the number of minutes ahead
     * @return the calculated date/time
     */
    fun minutesAhead(qty: Int): Long {
        return Calendar.getInstance().timeInMillis + (qty * 1000 * 60)
    }

    /**
     * Returns the date format of now
     *
     * @param pattern the date pattern to be used as template (Ex: dd/MM/yyyy)
     * @param locale where to base this date calculation, defaults to System locale
     * @return locale the date in string format
     */
    fun today(pattern: String = "dd/MM/yyyy", locale: Locale = Locale.getDefault()): String {
        return SimpleDateFormat(pattern, locale).format(Calendar.getInstance().time)
    }

    /**
     * Returns an integer representation of the given date (or today, if date not given), useful for
     * day-related calculations that are required to be repeatable, as a seed (Random methods)
     *
     * @return an Int number generated for the given day
     */
    fun intOfDay(date: Date? = null): Int {
        date?.let {
            return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date).toInt()
        } ?: run {
            return today(pattern="yyyyMMdd").toInt()
        }
    }

    /**
     * Wait [waitedTime] milisseconds then run [code], if given
     *
     * @param waitedTime the number of milisseconds to wait before running code or continuing to run
     * @param code the code to be executed after the given time (optional)
     */
    fun waitTime(waitedTime: Long, code: () -> Unit = {}) {
        Handler(Looper.getMainLooper()).postDelayed(code, waitedTime)
    }

    /**
     * Runs a block of code and returns the total of milliseconds spent on it
     *
     * @param command the code to be run
     * @param verbose whether stats of time will be given at Logcat
     * @return the total spent milliseconds in that code
     */
    fun lapTime(command: () -> Unit, verbose: Boolean = false): Long {
        val startTime = System.currentTimeMillis()
        command.invoke()
        val endTime = System.currentTimeMillis()
        val total = endTime - startTime

        if (verbose) Log.i("LapTime","The command started at $startTime and ended at $endTime, spending a total of $total milliseconds.")
        return total
    }

    /**
     * Converts from minutes (counting from Midnight) to a readable time format
     *
     * @param minutesFromMidnight minutes passed from Midnight, to be converted
     * @param to24hourFormat whether the time will be formatted in 24-hour format
     * @return the converted time as a readable string
     */
    fun minutesToTime(minutesFromMidnight: Int, to24hourFormat: Boolean = true): String {
        lateinit var postTime: String
        lateinit var hourString: String

        var hours: Int = kotlin.math.floor((minutesFromMidnight / 60).toDouble()).toInt()

        if (to24hourFormat) {
            postTime = "h"
        } else {
            if (hours >= 12) {
                hours -= 12
                postTime = " PM"
            } else {
                postTime = " AM"
            }
        }

        hourString = hours.toString().padStart(2,'0')
        val minutesString: String = minutesFromMidnight.mod(60).toString().padStart(2, '0')

        return ("$hourString:$minutesString$postTime")
    }
}