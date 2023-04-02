package at.nullphilippexception.gymlogger.util

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getTodaysDateFormatted(): String {
    val formatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    return formatter.format(this.time)
}