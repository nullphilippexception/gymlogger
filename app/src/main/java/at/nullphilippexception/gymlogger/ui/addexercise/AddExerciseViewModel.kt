package at.nullphilippexception.gymlogger.ui.addexercise

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.model.Exercise
import at.nullphilippexception.gymlogger.model.database.ExerciseDao
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent.*
import at.nullphilippexception.gymlogger.util.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(
    private val dao: ExerciseDao,
    private val dispatcher: CoroutineDispatchers
) : ViewModel() {

    val viewData: MutableLiveData<ViewData> by lazy {
        MutableLiveData<ViewData>()
    }
    val viewModelEvent: MutableLiveData<ViewModelEvent> by lazy {
        MutableLiveData<ViewModelEvent>()
    }

    init {
        viewModelScope.launch(dispatcher.io) {
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
        viewModelScope.launch(dispatcher.io) {
            try {
                if(name.isEmpty()) throw IllegalArgumentException("Name can't be empty")

                dao
                    .insertExercise(Exercise(
                        name = name,
                        category = category
                    ))
                viewModelEvent.postValue(
                    INSERT_SUCCESS
                )
            } catch(e: Exception) {
                //Log.e("ADD EXERCISE", e.message?:"no error message")
                viewModelEvent.postValue(
                    INSERT_FAILURE
                )
            }
        }
    }

    private fun getExercises(): List<Exercise> {
        return dao
            .getAllExercises()
    }
}

data class ViewData (
    var exercises: List<Exercise> = mutableListOf(),
    var showNoExercisesHint: Boolean = false
)

// TODO move this to dedicated file!
enum class ViewModelEvent {
    INSERT_SUCCESS,
    INSERT_FAILURE,
    DELETE_SUCCESS
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