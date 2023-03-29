package at.nullphilippexception.gymlogger

import at.nullphilippexception.gymlogger.ui.MainViewModel
import org.junit.Test

class MainViewModelTest {
    @Test
    fun GIVEN_addWorkout_WHEN_setsIsNotAnInteger_THEN_throwException() {
        val viewModel = MainViewModel()
        var receivedError = "no error"

        try {
            viewModel.addWorkout(
                exercise = "exercise",
                sets = "NOTANINT",
                reps="5",
                weight="5.0",
                date="15.11.22",
                note="n.a."
            )
        } catch(e : IllegalArgumentException) {
            receivedError = e.message.toString()
        }

        // REPLACE WITH STRING RESOURCE
        assert(receivedError == "Sets must be an Integer")
    }
}