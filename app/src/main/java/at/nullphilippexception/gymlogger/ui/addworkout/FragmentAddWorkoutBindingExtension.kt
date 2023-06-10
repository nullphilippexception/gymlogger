package at.nullphilippexception.gymlogger.ui.addworkout
import android.content.Context
import at.nullphilippexception.gymlogger.databinding.FragmentAddWorkoutBinding
import at.nullphilippexception.gymlogger.model.Workout.Companion.EMPTY_STRING
import at.nullphilippexception.gymlogger.model.nullSafeString
import at.nullphilippexception.gymlogger.model.setEndIconFocusRemover
import at.nullphilippexception.gymlogger.model.surrenderFocus

fun FragmentAddWorkoutBinding.bind(
    onEnterWorkout: (String, String, String, String, String) -> Unit,
    onViewWorkouts: () -> Unit,
    onAddExercise: () -> Unit,
    onChangeDate: () -> Unit,
    context: Context
): FragmentAddWorkoutBinding {
    btnEnter.setOnClickListener {
        onEnterWorkout(
            spExercise.selectedItem?.toString() ?: EMPTY_STRING,
            etSets.nullSafeString(),
            etReps.nullSafeString(),
            etWeight.nullSafeString(),
            etNote.nullSafeString()
        )
        etSets.surrenderFocus(context)
        etReps.surrenderFocus(context)
        etWeight.surrenderFocus(context)
        etNote.surrenderFocus(context)
    }
    btnView.setOnClickListener { onViewWorkouts() }
    tvAdd.setOnClickListener { onAddExercise() }
    cbDate.setOnClickListener { onChangeDate() }

    tilSets.setEndIconFocusRemover(context, etSets)
    tilReps.setEndIconFocusRemover(context, etReps)
    tilWeight.setEndIconFocusRemover(context, etWeight)
    tilNote.setEndIconFocusRemover(context, etNote)

    return this
}