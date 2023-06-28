package at.nullphilippexception.gymlogger.ui.viewworkouts.editworkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import at.nullphilippexception.gymlogger.model.Exercise
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.database.ExerciseDao
import at.nullphilippexception.gymlogger.model.database.WorkoutDao
import at.nullphilippexception.gymlogger.ui.addexercise.AddExerciseViewModel
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent.INSERT_SUCCESS
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent
import at.nullphilippexception.gymlogger.util.CoroutineDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val MOCKED_UID = "mockeduid"

@RunWith(JUnit4::class)
class EditWorkoutViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: EditWorkoutViewModel
    private val mockDao: WorkoutDao = mockk()
    private val mockDispatchers: CoroutineDispatchers = mockk()
    private val mockWorkout: Workout = mockk()

    @Before
    fun setup() {

        coEvery { mockDao.updateWorkout(any()) } returns Unit
        coEvery { mockDao.deleteWorkout(any()) } returns Unit
        coEvery { mockWorkout.uid } returns MOCKED_UID
        coEvery { mockDao.getWorkoutById(any()) } returns mockWorkout
        coEvery { mockDispatchers.io } returns Dispatchers.Unconfined
        viewModel = EditWorkoutViewModel(mockDao, mockDispatchers)
    }

    //TODO fix this test
    @Test
    fun `if update workout throws no error post success event`() = runBlockingTest {
        viewModel.saveEditedWorkout(
            reps = "10",
            sets = "10",
            weight = "10",
            date = "30.10.23",
            note = "none"
        )
        assertEquals(INSERT_SUCCESS, viewModel.viewModelEvent.value)
    }

    @Test
    fun `load workout retrieves the correct workout`() = runBlockingTest {
        viewModel.loadWorkout("test")
        assertEquals(MOCKED_UID, viewModel.workout.value?.uid)
    }
}