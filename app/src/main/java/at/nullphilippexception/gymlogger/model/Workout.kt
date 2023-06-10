package at.nullphilippexception.gymlogger.model

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.room.Entity
import androidx.room.PrimaryKey
import at.nullphilippexception.gymlogger.R

@Entity(tableName = "workout")
data class Workout(
    @PrimaryKey val uid: String,
    val exercise: Exercise = Exercise.getEmptyExercise(),
    val sets: Int = 0,
    val reps: Int = 0,
    val weight: Double = 0.0,
    val date: String = EMPTY_STRING,
    val note: String = EMPTY_STRING
) {

    fun getSetsString(context: Context): String =
        "${context.getString(R.string.class_workout_sets)} $sets"

    fun getRepsString(context: Context): String =
        "${context.getString(R.string.class_workout_reps)} $reps"

    fun getWeightString(context: Context): String =
        "${context.getString(R.string.class_workout_weight)} $weight"

    companion object {
        const val EMPTY_STRING = ""
    }
}


