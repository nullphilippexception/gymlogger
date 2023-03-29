package at.nullphilippexception.gymlogger.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import at.nullphilippexception.gymlogger.model.Workout

@Dao
interface WorkoutDao {

    @Query("SELECT * from workout")
    fun getAllWorkouts(): List<Workout>

    @Query("SELECT * from workout WHERE uid LIKE :uid")
    fun getWorkoutById(uid: String): Workout

    @Query("SELECT * from workout WHERE date LIKE :date")
    fun getWorkoutsByDate(date: String): List<Workout>

    @Insert
    fun insertWorkout(workout: Workout)

    @Update
    fun updateWorkout(workout: Workout)

    @Delete
    fun deleteWorkout(workout: Workout)

}