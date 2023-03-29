package at.nullphilippexception.gymlogger.ui.addworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.databinding.FragmentAddWorkoutBinding
import at.nullphilippexception.gymlogger.model.bind
import at.nullphilippexception.gymlogger.model.resetText
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent.INSERT_FAILURE
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent.INSERT_SUCCESS

class AddWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentAddWorkoutBinding
    private val viewModel: AddWorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWorkoutBinding.inflate(inflater, container, false).bind(
            viewModel::addWorkout,
            this@AddWorkoutFragment::viewWorkouts,
            this@AddWorkoutFragment::addExercise,
            this@AddWorkoutFragment::handleDateCheckbox
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.exercises.observe(viewLifecycleOwner) { lst ->
            binding.spExercise.adapter = ArrayAdapter(requireContext(),
                R.layout.view_spinner_item, lst)
        }

        viewModel.viewModelEvent.observe(viewLifecycleOwner) {  event ->
            when(event) {
                INSERT_SUCCESS -> {
                    Toast.makeText(requireContext(), getString(R.string.frag_add_workout_event_success), Toast.LENGTH_SHORT).show()
                    resetInputs()
                }
                INSERT_FAILURE ->
                    Toast.makeText(requireContext(), getString(R.string.frag_add_workout_event_failure), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun viewWorkouts() {
        findNavController().navigate(R.id.action_goToWorkoutList)
    }

    private fun addExercise() {
        findNavController().navigate(R.id.action_goToExercises)
    }

    private fun handleDateCheckbox() {
        // pop up date picker and edit checkbox text accordingly
    }

    private fun resetInputs() {
        binding.etWeight.resetText()
        binding.etReps.resetText()
        binding.etSets.resetText()
        binding.etNote.resetText()
    }
}