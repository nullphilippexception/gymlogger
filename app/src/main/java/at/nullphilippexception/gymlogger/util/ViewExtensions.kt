package at.nullphilippexception.gymlogger.model

import android.widget.TextView

import at.nullphilippexception.gymlogger.model.Workout.Companion.EMPTY_STRING

fun TextView.nullSafeString(): String {
    this.text?.let {
        return text.toString()
    }
    return EMPTY_STRING
}

fun TextView.resetText() {
    this.text = EMPTY_STRING
}