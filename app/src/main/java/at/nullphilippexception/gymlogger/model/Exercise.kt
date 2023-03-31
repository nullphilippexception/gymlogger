package at.nullphilippexception.gymlogger.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import at.nullphilippexception.gymlogger.model.Workout.Companion.EMPTY_STRING

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "exercise_name") val name: String,
    val category: String
) {
    companion object {
        fun getEmptyExercise(): Exercise {
            return Exercise(name = EMPTY_STRING, category = EMPTY_STRING)
        }
    }
}
