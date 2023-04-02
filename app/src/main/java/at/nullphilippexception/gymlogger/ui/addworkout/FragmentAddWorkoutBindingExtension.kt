package at.nullphilippexception.gymlogger.model
import at.nullphilippexception.gymlogger.databinding.FragmentAddWorkoutBinding
import at.nullphilippexception.gymlogger.model.Workout.Companion.EMPTY_STRING
import at.nullphilippexception.gymlogger.util.getTodaysDateFormatted
import java.util.*

fun FragmentAddWorkoutBinding.bind(
    onEnterWorkout: (String, String, String, String, String) -> Unit,
    onViewWorkouts: () -> Unit,
    onAddExercise: () -> Unit,
    onChangeDate: () -> Unit
): FragmentAddWorkoutBinding {
    btnEnter.setOnClickListener { onEnterWorkout(
        spExercise.selectedItem?.toString() ?: EMPTY_STRING,
        etSets.nullSafeString(),
        etReps.nullSafeString(),
        etWeight.nullSafeString(),
        etNote.nullSafeString()
    ) }
    btnView.setOnClickListener { onViewWorkouts() }
    tvAdd.setOnClickListener { onAddExercise() }
    cbDate.setOnClickListener { onChangeDate() }

    return this
}