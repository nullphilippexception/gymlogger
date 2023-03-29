package at.nullphilippexception.gymlogger.ui.addexercise

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.model.Exercise
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddExerciseViewModel(application: Application) : AndroidViewModel(application) {
    val viewData: MutableLiveData<ViewData> by lazy {
        MutableLiveData<ViewData>()
    }
    val viewModelEvent: MutableLiveData<ViewModelEvent> by lazy {
        MutableLiveData<ViewModelEvent>()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentExercises = getExercises()
            viewData.postValue(
                ViewData(
                    exercises = currentExercises,
                    showNoExercisesHint = currentExercises.isEmpty()
                )
            )
        }
    }

    fun insertExercise(name: String, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(name.isEmpty()) throw IllegalArgumentException("Name can't be empty")

                AppDatabase
                    .getDatabase(getApplication())
                    .exerciseDao()
                    .insertExercise(Exercise(
                        name = name,
                        category = category
                    ))
                viewModelEvent.postValue(
                    INSERT_SUCCESS
                )
            } catch(e: Exception) {
                Log.e("ADD EXERCISE", e.message?:"no error message")
                viewModelEvent.postValue(
                    INSERT_FAILURE
                )
            }
        }
    }

    private fun getExercises(): List<Exercise> {
        return AppDatabase
            .getDatabase(getApplication())
            .exerciseDao()
            .getAllExercises()
    }
}

data class ViewData (
    var exercises: List<Exercise> = mutableListOf(),
    var showNoExercisesHint: Boolean = false
)

enum class ViewModelEvent {
    INSERT_SUCCESS,
    INSERT_FAILURE
}

enum class ExerciseType(val drawable: Int = R.drawable.ic_error) {
    CHEST (R.drawable.ic_ex_chest),
    SHOULDERS (R.drawable.ic_ex_shoulder),
    ARMS (R.drawable.ic_ex_arms),
    BACK (R.drawable.ic_ex_back),
    LEGS (R.drawable.ic_ex_legs),
    NECK (R.drawable.ic_ex_neck),
    ABS (R.drawable.ic_ex_abs),
    ENDURANCE (R.drawable.ic_ex_treadmill),
    MOBILITY (R.drawable.ic_ex_mobility);
}