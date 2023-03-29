package at.nullphilippexception.gymlogger.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import at.nullphilippexception.gymlogger.model.Exercise

@Dao
interface ExerciseDao {

    @Query("SELECT * from exercise")
    fun getAllExercises(): List<Exercise>

    @Insert
    fun insertExercise(exercise: Exercise)

}