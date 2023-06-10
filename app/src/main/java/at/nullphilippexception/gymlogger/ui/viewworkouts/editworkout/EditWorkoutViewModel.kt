package at.nullphilippexception.gymlogger.ui.viewworkouts.editworkout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent.*
import at.nullphilippexception.gymlogger.util.ifEmptyToDouble
import at.nullphilippexception.gymlogger.util.ifEmptyToInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditWorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var currentWorkout: Workout
    val viewModelEvent: MutableLiveData<ViewModelEvent> by lazy {
        MutableLiveData<ViewModelEvent>()
    }
    val workout: MutableLiveData<Workout> by lazy {
        MutableLiveData<Workout>()
    }

    fun saveEditedWorkout(sets: String, reps: String, weight: String, date: String, note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase
                .getDatabase(getApplication())
                .workoutDao()
                .updateWorkout(
                    Workout(
                        uid = currentWorkout.uid,
                        exercise = currentWorkout.exercise,
                        sets = sets.ifEmptyToInt(currentWorkout.sets),
                        reps = reps.ifEmptyToInt(currentWorkout.reps),
                        weight = weight.ifEmptyToDouble(currentWorkout.weight),
                        date = date.ifEmpty { currentWorkout.date },
                        note = note.ifEmpty { currentWorkout.note },
                    )
                )
            viewModelEvent.postValue(INSERT_SUCCESS)
        }
    }

    fun deleteCurrentWorkout() {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase
                .getDatabase(getApplication())
                .workoutDao()
                .deleteWorkout(currentWorkout)
            viewModelEvent.postValue(DELETE_SUCCESS)
        }
    }

    fun loadWorkout(workoutId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currentWorkout = AppDatabase
                .getDatabase(getApplication())
                .workoutDao()
                .getWorkoutById(workoutId)
            workout.postValue(
                currentWorkout
            )
        }
    }
}