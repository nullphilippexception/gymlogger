package at.nullphilippexception.gymlogger.util

import at.nullphilippexception.gymlogger.util.Months.*
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getDateFormatted(date: Date = this.time): String {
    val formatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    return formatter.format(date)
}

fun Calendar.getFormattedPreviousDay(currentDate: String): String {
    // TODO: create own easy date class
    val regex = "^(\\d+)\\.(\\d+)\\.(\\d+)$".toRegex()
    // TODO: replace !! operator with better solution
    val (day: String, month: String, year: String) = regex.find(currentDate)!!.destructured

    if(day.toInt() > 1) return "${(day.toInt() - 1).toStringWithLeadingZero()}.$month.$year"
    if(month.toInt() > 1) {
        val previousMonthThirtyOneDays = listOf(FEB, APR, JUN, SEP, NOV)
        // current month is mar and no leap year - previous day is feb 28
        if(MAR == Months.fromInt(month.toInt())
            && year.toInt() % 4 != 0) return "28.${(month.toInt() - 1).toStringWithLeadingZero()}.$year"
        // current month is mar and leap year - previous day is feb 29
        if(MAR == Months.fromInt(month.toInt())
            && year.toInt() % 4 == 0) return "29.${(month.toInt() - 1).toStringWithLeadingZero()}.$year"
        // the previous month of the current month has 31 days
        if(previousMonthThirtyOneDays.contains(Months.fromInt(month.toInt()))) {
            return "31.${(month.toInt() - 1).toStringWithLeadingZero()}.$year"
        }
        else return "30.${(month.toInt() - 1).toStringWithLeadingZero()}.$year"
    }
    else { // we are already on first day of first month, need to switch year
        return "31.12.${year.toInt() - 1}"
    }
}

fun Calendar.getFormattedNextDay(currentDate: String): String {
    // TODO: create own easy date class
    val regex = "^(\\d+)\\.(\\d+)\\.(\\d+)$".toRegex()
    // TODO: replace !! operator with better solution
    val (day: String, month: String, year: String) = regex.find(currentDate)!!.destructured
    val thirtyDayMonths = listOf(APR, JUN, SEP, NOV)

    // TODO: refactor this whole mess
    if(day.toInt() < 28) return "${(day.toInt() + 1).toStringWithLeadingZero()}.$month.$year"
    // leap year
    if(day.toInt() == 28 && Months.fromInt(month.toInt()) == FEB && year.toInt() % 4 == 0) return "${(day.toInt() + 1).toStringWithLeadingZero()}.$month.$year"
    // no leap year
    if(day.toInt() == 28 && Months.fromInt(month.toInt()) == FEB && year.toInt() % 4 != 0) return "01.${(month.toInt() + 1).toStringWithLeadingZero()}.$year"
    if(day.toInt() < 30) return "${(day.toInt() + 1).toStringWithLeadingZero()}.$month.$year"
    if(day.toInt() == 30 && thirtyDayMonths.contains(Months.fromInt(month.toInt()))) return "01.${(month.toInt() + 1).toStringWithLeadingZero()}.$year"
    if(day.toInt() == 30) return "${(day.toInt() + 1).toStringWithLeadingZero()}.$month.$year"
    else { // it is day 31
        if(Months.fromInt(month.toInt()) == DEC) return "01.01.${(year.toInt() + 1)}"
        else { // no year flip
            return "01.${(month.toInt() + 1).toStringWithLeadingZero()}.$year"
        }
    }
}

fun Calendar.convertToCorrectFormat(day: Int, month: Int, year: Int): String {
    val resultDay = day.toStringWithLeadingZero()
    val resultMonth = month.toStringWithLeadingZero()
    val resultYear = year.toString().substring(2) // TODO make this safe for year with less than 4 digits
    return "$resultDay.$resultMonth.$resultYear"
}

fun String.ifEmptyToInt(ifEmptyValue: Int): Int = if(this.isEmpty()) ifEmptyValue else this.toInt()

fun String.ifEmptyToDouble(ifEmptyValue: Double): Double
                = if(this.isEmpty()) ifEmptyValue else this.toDouble()

fun Int.toStringWithLeadingZero(): String {
    return if(this.toString().length == 1) "0$this"
    else this.toString()
}

private enum class Months(numericValue: Int) {
    JAN(1),
    FEB(2),
    MAR(3),
    APR(4),
    MAY(5),
    JUN(6),
    JUL(7),
    AUG(8),
    SEP(9),
    OCT(10),
    NOV(11),
    DEC(12);

    companion object {
        fun fromInt(value: Int): Months? {
            return values().find { it.ordinal + 1 == value }
        }
    }
}
