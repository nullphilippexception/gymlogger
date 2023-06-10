package at.nullphilippexception.gymlogger.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar.*

class WorkoutDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface DateDialogListener {
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int)
    }

    private lateinit var listener: DateDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = getInstance()
        val year = calendar.get(YEAR)
        val month = calendar.get(MONTH)
        val day = calendar.get(DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // JAN should be 1, not 0
        listener.onDateSet(year, month+1, dayOfMonth)
    }

    fun setListener(listener: DateDialogListener) {
        this.listener = listener
    }
}