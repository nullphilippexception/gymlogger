package at.nullphilippexception.gymlogger.ui.addworkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import at.nullphilippexception.gymlogger.model.Exercise
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.Workout.Companion.EMPTY_STRING
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class AddWorkoutViewModel(application: Application) : AndroidViewModel(application) {
    val exercises: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val viewModelEvent: MutableLiveData<ViewModelEvent> by lazy {
        MutableLiveData<ViewModelEvent>()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            exercises.postValue(
                AppDatabase
                    .getDatabase(getApplication())
                    .exerciseDao()
                    .getAllExercises()
                    .map {
                        it.name
                    }
            )
        }
    }

    fun addWorkout(exercise: String, sets: String, reps: String, weight: String,
                   date: String, note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(exercise.isEmpty()) throw IllegalArgumentException("Exercise field can't be empty")
                if(sets.toIntOrNull() == null) throw IllegalArgumentException("Sets must be an Integer")
                if(reps.toIntOrNull() == null) throw IllegalArgumentException("Reps must be an Integer")
                if(weight.toDoubleOrNull() == null) throw IllegalArgumentException("Weight must be a Double")
                AppDatabase.getDatabase(getApplication()).workoutDao().insertWorkout(
                    Workout(
                        uid = UUID.randomUUID().toString(),
                        exercise = exercise,
                        sets = sets.toInt(),
                        reps = reps.toInt(),
                        weight = weight.toDouble(),
                        date = date,
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