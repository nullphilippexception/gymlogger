package at.nullphilippexception.gymlogger.ui.viewworkouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewWorkoutsViewModel(application: Application): AndroidViewModel(application) {
    val workouts: MutableLiveData<List<Workout>> by lazy {
        MutableLiveData<List<Workout>>()
    }
    val viewModelEvent: MutableLiveData<ViewModelEvent> by lazy {
        MutableLiveData<ViewModelEvent>()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            workouts.postValue(
                AppDatabase
                    .getDatabase(getApplication())
                    .workoutDao()
                    .getAllWorkouts()
            )
        }
    }

}