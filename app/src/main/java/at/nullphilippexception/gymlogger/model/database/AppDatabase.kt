package at.nullphilippexception.gymlogger.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.nullphilippexception.gymlogger.model.Exercise
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.util.RoomConverters

@Database(entities = [Workout::class, Exercise::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        private var database: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            database?.let {
                return database!! // compiler wont allow smart cast; we can use !! here because its only called if non-null
            } ?: run {
                synchronized(this) {
                    database = Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                        .build()
                    return database!! // compiler wont allow smart cast; we can use !! here because we just assigned the var
                }
            }
        }
    }
}