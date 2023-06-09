package at.nullphilippexception.gymlogger.ui.addworkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import at.nullphilippexception.gymlogger.model.Exercise
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.Workout.Companion.EMPTY_STRING
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.model.database.ExerciseDao
import at.nullphilippexception.gymlogger.model.database.WorkoutDao
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent.*
import at.nullphilippexception.gymlogger.util.CoroutineDispatchers
import at.nullphilippexception.gymlogger.util.getDateFormatted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val workoutDao: WorkoutDao,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {
    val exercises: MutableLiveData<List<Exercise>> by lazy {
        MutableLiveData<List<Exercise>>()
    }
    val date: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val viewModelEvent: MutableLiveData<ViewModelEvent> by lazy {
        MutableLiveData<ViewModelEvent>()
    }

    init {
        viewModelScope.launch(dispatchers.io) {
            exercises.postValue(
                exerciseDao
                    .getAllExercises()
            )
            date.postValue(
                Calendar.getInstance().getDateFormatted()
            )
        }
    }

    fun addWorkout(exercise: String, sets: String, reps: String, weight: String, note: String) {
        viewModelScope.launch(dispatchers.io) {
            try {
                if(exercise.isEmpty()) throw IllegalArgumentException("Exercise field can't be empty")
                if(sets.toIntOrNull() == null) throw IllegalArgumentException("Sets must be an Integer")
                if(reps.toIntOrNull() == null) throw IllegalArgumentException("Reps must be an Integer")
                if(weight.toDoubleOrNull() == null) throw IllegalArgumentException("Weight must be a Double")
                workoutDao.insertWorkout(
                    Workout(
                        uid = UUID.randomUUID().toString(),
                        exercise = exercises.value?.find { it.name == exercise } ?: Exercise.getEmptyExercise(),
                        sets = sets.toInt(),
                        reps = reps.toInt(),
                        weight = weight.toDouble(),
                        date = date.value ?: EMPTY_STRING,
                        note = note
                    )
                )
                viewModelEvent.postValue(
                    INSERT_SUCCESS
                )
            } catch(e: Exception) {
                Log.e("ADD WORKOUT", e.message?:"no error message")
                viewModelEvent.postValue(
                    INSERT_FAILURE
                )
            }
        }
    }
}

enum class ViewModelEvent {
    INSERT_SUCCESS,
    INSERT_FAILURE
}