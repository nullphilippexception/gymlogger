package at.nullphilippexception.gymlogger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.nullphilippexception.gymlogger.model.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.IllegalArgumentException


class MainViewModel() : ViewModel(){
    private val _exerciseListState = MutableStateFlow(ExerciseListState())
    val exerciseListState: StateFlow<ExerciseListState> = _exerciseListState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _exerciseListState.update { currentState ->
                currentState.copy(
                    exercises = getExercises()
                )
            }
        }
    }

    fun addWorkout(exercise: String, sets: String, reps: String, weight: String,
                   date: String, note: String) {
        // input parsing - REPLACE WITH STRING RESOURCES
        if(exercise.isEmpty()) throw IllegalArgumentException("Exercise field can't be empty")
        if(sets.toIntOrNull() == null) throw IllegalArgumentException("Sets must be an Integer")
        if(reps.toIntOrNull() == null) throw IllegalArgumentException("Reps must be an Integer")
        if(weight.toDoubleOrNull() == null) throw IllegalArgumentException("Weight must be a Double")

        // enter into db
    }

    fun getExercises(): MutableList<Exercise> {
        // read exercises from db and return list of them
        return mutableListOf()
    }

    data class ExerciseListState(
        var exercises: MutableList<Exercise> = mutableListOf<Exercise>()
    )

}