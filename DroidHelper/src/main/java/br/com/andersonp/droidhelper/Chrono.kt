package br.com.andersonp.droidhelper

import java.text.SimpleDateFormat
import java.util.*

object Chrono {

    fun millisecondsToDatestring(timeinMillies: Long, pattern: String = "yyyy-MM-dd HH:mm:ss", locale: Locale = Locale("pt","BR")): String {
        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(Date(timeinMillies))
    }

    fun datestringToMilliseconds(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, seconds: Int = 0): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute, seconds)
        return calendar.timeInMillis
    }

    fun minutesAhead(qty: Int): Long {
        val calendar: Calendar = Calendar.getInstance()
        return calendar.timeInMillis + (qty * 1000 * 60)
    }

    fun today(pattern: String = "dd/MM/yyyy", locale:Locale = Locale("pt", "BR")): String {
        return SimpleDateFormat(pattern, locale).format(Calendar.getInstance().time)
    }

}