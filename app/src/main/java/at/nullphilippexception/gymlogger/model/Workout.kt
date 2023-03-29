package at.nullphilippexception.gymlogger.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class Workout(
    @PrimaryKey val uid: String,
    val exercise: String = EMPTY_STRING,
    val sets: Int = 0,
    val reps: Int = 0,
    val weight: Double = 0.0,
    val date: String = EMPTY_STRING,
    val note: String = EMPTY_STRING
) {
    companion object {
        const val EMPTY_STRING = ""
    }
}


