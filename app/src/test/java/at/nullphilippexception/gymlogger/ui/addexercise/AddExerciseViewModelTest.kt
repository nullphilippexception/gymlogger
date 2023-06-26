package at.nullphilippexception.gymlogger.ui.addexercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import at.nullphilippexception.gymlogger.model.Exercise
import at.nullphilippexception.gymlogger.model.database.AppDatabase
import at.nullphilippexception.gymlogger.model.database.ExerciseDao
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent.*
import at.nullphilippexception.gymlogger.util.CoroutineDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class AddExerciseViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddExerciseViewModel
    private val mockDao: ExerciseDao = mockk()
    private val mockDispatchers: CoroutineDispatchers = mockk()

    @Before
    fun setup() {
        coEvery { mockDao.getAllExercises() } returns emptyList()
        coEvery { mockDao.insertExercise(any()) } returns Unit
        coEvery { mockDispatchers.io } returns Dispatchers.Unconfined
        viewModel = AddExerciseViewModel(mockDao, mockDispatchers)
    }

    @Test
    fun `insertExercise with valid input should post INSERT_SUCCESS event`() = runBlockingTest {
        val exerciseName = "Test Exercise"
        val exerciseCategory = "Test Category"

        viewModel.insertExercise(exerciseName, exerciseCategory)

        verify { mockDao.insertExercise(Exercise(name = exerciseName, category = exerciseCategory)) }
        assertEquals(INSERT_SUCCESS, viewModel.viewModelEvent.value)
    }

    @Test
    fun `insertExercise with empty name should post INSERT_FAILURE event`() = runBlockingTest {
        val exerciseName = ""
        val exerciseCategory = "Test Category"

        viewModel.insertExercise(exerciseName, exerciseCategory)

        verify(exactly = 0) { mockDao.insertExercise(any()) }
        assertEquals(INSERT_FAILURE, viewModel.viewModelEvent.value)
    }

    @Test
    fun `when db returns no exercises showNoExercisesHint is true`() = runBlockingTest {
        assertTrue(viewModel.viewData.value?.showNoExercisesHint ?: false)
    }
}
