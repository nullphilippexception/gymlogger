package at.nullphilippexception.gymlogger.ui.viewworkouts.editworkout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditWorkoutViewModel(application: Application) : AndroidViewModel(application) {
    val workout: MutableLiveData<Workout> by lazy {
        MutableLiveData<Workout>()
    }

    fun saveEditedWorkout(sets: String, reps: String, weight: String, date: String, note: String) {
        // Save to db here
    }

    fun loadWorkout(workoutId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            workout.postValue(
                AppDatabase
                    .getDatabase(getApplication())
                    .workoutDao()
                    .getWorkoutById(workoutId)
            )
        }
    }
}