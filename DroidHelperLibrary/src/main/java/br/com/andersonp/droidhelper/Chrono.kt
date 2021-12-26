package br.com.andersonp.droidhelper

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.*
import kotlin.math.floor

/**
 * Chrono - The manager of time
 *
 */
@Suppress("unused")
object Chrono {

    // ******************** GETs / RETURNs *************************

    /**
     * Returns today in a Date object
     *
     * @param zeroTimeOfDay (optional) whether it will return the start of the day (zero hour). Defaults to FALSE.
     * @return the today date object
     */
    fun today(zeroTimeOfDay: Boolean = false): LocalDateTime {
        val newDate = LocalDateTime.now()
        return if (zeroTimeOfDay) {
            newDate.withHour(0).withMinute(0).withSecond(0).withNano(0)
        } else {
            newDate
        }
    }

    /**
     * Returns a readable text of today date
     *
     * @param dateFormat the date pattern to be used as template (Ex: dd/MM/yyyy)
     * @param locale where to base this date calculation, defaults to System locale
     * @return locale the date in string format
     */
    fun todayText(dateFormat: String = "dd/MM/yyyy", locale: Locale = Locale.getDefault()): String {
        return DateTimeFormatter.ofPattern(dateFormat, locale).format(today())
    }

    // ******************** CONVERSIONS *************************

    /**
     * Simply converts a Long to a LocalDateTime object
     *
     * @return the converted LocalDateTime from the Long (must be in milliseconds)
     */
    fun Long.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), TimeZone.getDefault().toZoneId())
    }

    /**
     * Returns the equivalent milliseconds of a LocalDateTime
     *
     * @return the Long equivalent (milliseconds) of the LocalDateTime
     */
    fun LocalDateTime.toMilli(): Long {
        return (this.toEpochSecond(ZoneOffset.UTC) * 1000 + this[ChronoField.MILLI_OF_SECOND])
    }

    /**
     * Converts a LocalDateTime to a readable string
     *
     * @param pattern the pattern format of the string returned
     * @param locale the locale considered. Defaults to local
     * @return the Local DateTime in a readable string
     */
    fun LocalDateTime.toReadable(pattern: String = "yyyy-MM-dd HH:mm:ss", locale: Locale = Locale.getDefault()): String {
        return DateTimeFormatter.ofPattern(pattern, locale).format(this)
    }

    /**
     * Converts a milliseconds value to a date in String format
     *
     * @param timeinMillies value of the time in Long format
     * @param pattern pattern which the date info will be filled with
     * @param locale locale where to base this date calculation, defaults to System locale
     * @return string with the date description in a readable format
     */
    fun millisecondsToDatestring(timeinMillies: Long, pattern: String = "yyyy-MM-dd HH:mm:ss", locale: Locale = Locale.getDefault()): String {
        return DateTimeFormatter.ofPattern(pattern, locale).format(timeinMillies.toLocalDateTime())
    }

    /**
     * Find the specific Long that represents the given date in milliseconds
     *
     * @param year the year to be used
     * @param month the month to be used
     * @param day the day to be used
     * @param hour (optional) the hour to be used
     * @param minute (optional) the minute to be used
     * @param seconds (optional) the seconds to be used
     * @return the date as Long format (milliseconds)
     */
    fun dateToMilliseconds(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, seconds: Int = 0, nano: Int = 0): Long {
        val date = LocalDateTime.of(year, month, day, hour, minute, seconds, nano)
        return date.toMilli()
    }

    // ******************** OPERATIONS *************************

    /**
     * Wait [waitedTime] milisseconds then run [runAfter], if given
     *
     * @param waitedTime the number of milisseconds to wait before running code or continuing to run
     * @param runAfter the code to be executed after the given time (optional)
     */
    fun waitTime(waitedTime: Long, runAfter: () -> Unit = {}) {
        Handler(Looper.getMainLooper()).postDelayed(runAfter, waitedTime)
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
     * Tells the calculated Time (counting minutes from Midnight)
     *
     * @param minutesFromMidnight minutes passed from Midnight
     * @param to24hourFormat whether the time will be formatted in 24-hour format
     * @return the converted time as a readable string
     */
    fun minutesToTime(minutesFromMidnight: Int, to24hourFormat: Boolean = true): String {
        lateinit var postTime: String
        lateinit var hourString: String

        var hours: Int = floor((minutesFromMidnight / 60).toDouble()).toInt()

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