package at.nullphilippexception.gymlogger.ui.viewworkouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.model.database.WorkoutDao
import at.nullphilippexception.gymlogger.util.CoroutineDispatchers
import at.nullphilippexception.gymlogger.util.getDateFormatted
import at.nullphilippexception.gymlogger.util.getFormattedNextDay
import at.nullphilippexception.gymlogger.util.getFormattedPreviousDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@HiltViewModel
class ViewWorkoutsViewModel(
    private val workoutDao: WorkoutDao,
    private val dispatchers: CoroutineDispatchers
): ViewModel() {
    val selectedDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val workouts: MutableLiveData<List<Workout>> by lazy {
        MutableLiveData<List<Workout>>()
    }

    init {
        viewModelScope.launch(dispatchers.io) {
            loadWorkoutsFromDb()
            selectedDate.postValue(
                Calendar.getInstance().getDateFormatted()
            )
        }
    }

    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            selectedDate.postValue(
                Calendar.getInstance().getDateFormatted()
            )
            loadWorkoutsFromDb()
        }
    }

    fun previousDay() {
        viewModelScope.launch(dispatchers.io) {
            val tmpDate = Calendar
                .getInstance()
                .getFormattedPreviousDay(selectedDate.value ?: Calendar.getInstance().getDateFormatted())
            selectedDate.postValue(
                tmpDate
            )
            loadWorkoutsFromDb(tmpDate)
        }
    }

    fun nextDay() {
        viewModelScope.launch(dispatchers.io) {
            val tmpDate = Calendar
                .getInstance()
                .getFormattedNextDay(selectedDate.value ?: Calendar.getInstance().getDateFormatted())
            selectedDate.postValue(
                tmpDate
            )
            loadWorkoutsFromDb(tmpDate)
        }
    }

    fun selectedDay(date: String) {
        viewModelScope.launch(dispatchers.io) {
            selectedDate.postValue(
                date
            )
            loadWorkoutsFromDb(date)
        }
    }

    private fun loadWorkoutsFromDb(date: String = Calendar.getInstance().getDateFormatted()) {
        workouts.postValue(
            workoutDao.getWorkoutsByDate(date)
        )
    }

}