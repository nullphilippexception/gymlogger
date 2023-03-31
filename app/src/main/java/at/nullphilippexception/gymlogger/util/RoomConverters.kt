package at.nullphilippexception.gymlogger.util

import androidx.room.TypeConverter
import at.nullphilippexception.gymlogger.model.Exercise
import com.google.gson.Gson

class RoomConverters {
    // Exercise
    @TypeConverter
    fun fromString(value: String?): Exercise {
        return Gson().fromJson(value, Exercise::class.java)
    }

    @TypeConverter
    fun fromExercise(exercise: Exercise?): String? {
        return Gson().toJson(exercise)
    }
}