package at.nullphilippexception.gymlogger.di

import android.app.Application
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.model.database.ExerciseDao
import at.nullphilippexception.gymlogger.util.CoroutineDispatchers
import at.nullphilippexception.gymlogger.util.StandardCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): AppDatabase {
        return AppDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideCoroutineDispatchers(): CoroutineDispatchers {
        return StandardCoroutineDispatchers()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(app: Application): ExerciseDao {
        return AppDatabase.getDatabase(app).exerciseDao()
    }
}