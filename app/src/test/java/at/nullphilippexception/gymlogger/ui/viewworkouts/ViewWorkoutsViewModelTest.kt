package at.nullphilippexception.gymlogger.ui.viewworkouts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import at.nullphilippexception.gymlogger.model.Exercise
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.database.ExerciseDao
import at.nullphilippexception.gymlogger.model.database.WorkoutDao
import at.nullphilippexception.gymlogger.ui.addexercise.AddExerciseViewModel
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent
import at.nullphilippexception.gymlogger.util.CoroutineDispatchers
import at.nullphilippexception.gymlogger.util.getDateFormatted
import at.nullphilippexception.gymlogger.util.getFormattedNextDay
import at.nullphilippexception.gymlogger.util.getFormattedPreviousDay
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class ViewWorkoutsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ViewWorkoutsViewModel
    private val mockDao: WorkoutDao = mockk()
    private val mockDispatchers: CoroutineDispatchers = mockk()
    private val mockWorkout: Workout = mockk()

    @Before
    fun setup() {
        coEvery { mockDispatchers.io } returns Dispatchers.Unconfined
    }

    @Test
    fun `if db returns no workouts for today viewmodel has empty workouts list`() = runBlockingTest {
        coEvery { mockDao.getWorkoutsByDate(any())} returns listOf()
        viewModel = ViewWorkoutsViewModel(mockDao, mockDispatchers)

        assertTrue(viewModel.workouts.value?.isEmpty() ?: false)
    }

    @Test
    fun `if db returns workouts for today viewmodel has all of them`() = runBlockingTest {
        coEvery { mockDao.getWorkoutsByDate(any())} returns listOf(mockWorkout, mockWorkout)
        viewModel = ViewWorkoutsViewModel(mockDao, mockDispatchers)

        assertTrue(viewModel.workouts.value?.size == 2)
    }

    @Test
    fun `viewmodel is able to seperate workouts by date and only shows current date`() = runBlockingTest {
        val today = Calendar.getInstance().getDateFormatted()
        val yesterday = Calendar
            .getInstance()
            .getFormattedPreviousDay(today)
        val tomorrow = Calendar
            .getInstance()
            .getFormattedNextDay(today)

        coEvery { mockDao.getWorkoutsByDate(today)} returns listOf()
        coEvery { mockDao.getWorkoutsByDate(yesterday)} returns listOf(mockWorkout, mockWorkout)
        coEvery { mockDao.getWorkoutsByDate(tomorrow)} returns listOf(mockWorkout)

        viewModel = ViewWorkoutsViewModel(mockDao, mockDispatchers)

        assertTrue(viewModel.workouts.value?.size == 0)
        viewModel.previousDay()
        assertTrue(viewModel.workouts.value?.size == 2)
        viewModel.nextDay()
        viewModel.nextDay()
        assertTrue(viewModel.workouts.value?.size == 1)

    }
}