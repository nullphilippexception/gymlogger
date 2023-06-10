package at.nullphilippexception.gymlogger.model

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import at.nullphilippexception.gymlogger.R

import at.nullphilippexception.gymlogger.model.Workout.Companion.EMPTY_STRING
import at.nullphilippexception.gymlogger.ui.addworkout.bind
import com.google.android.material.textfield.TextInputLayout

fun TextView.nullSafeString(): String {
    this.text?.let {
        return text.toString()
    }
    return EMPTY_STRING
}

fun TextView.resetText() {
    this.text = EMPTY_STRING
}

// TODO refactor this to work better with encapsulated EditText
fun TextInputLayout.setEndIconFocusRemover(context: Context, editText: EditText) {
    editText.setOnFocusChangeListener { view, hasFocus ->
        if (hasFocus) {
            this.endIconDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_checkmark)
            this.setEndIconOnClickListener {
                if (ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime()) == true) {
                    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                        ?.hideSoftInputFromWindow(view.windowToken, 0)
                }
                this.clearFocus()
            }
        } else {
            this.endIconDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_editable)
            this.setEndIconOnClickListener {
                this.requestFocus()
            }
        }
    }
}

fun EditText.surrenderFocus(context: Context) {
    if(this.hasFocus()) {
        if (ViewCompat.getRootWindowInsets(this)?.isVisible(WindowInsetsCompat.Type.ime()) == true) {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                ?.hideSoftInputFromWindow(this.windowToken, 0)
        }
        this.clearFocus()
    }

}
