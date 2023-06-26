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
import at.nullphilippexception.gymlogger.model.resetText
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent.INSERT_FAILURE
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent.INSERT_SUCCESS
import at.nullphilippexception.gymlogger.util.WorkoutDatePickerDialog
import at.nullphilippexception.gymlogger.util.convertToCorrectFormat
import at.nullphilippexception.gymlogger.util.getDateFormatted
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

// TODO enable to access this screen via TopMenu
// TODO datepicker topbar color
@AndroidEntryPoint
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
            this@AddWorkoutFragment::handleDateCheckbox,
            this@AddWorkoutFragment.requireContext()
        )
        return binding.root
    }

    // TODO add proper string
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.exercises.observe(viewLifecycleOwner) { lst ->
            binding.spExercise.adapter = ArrayAdapter(requireContext(),
                R.layout.view_spinner_item, lst.map { it.name }.ifEmpty { listOf("Please add exercises") })
        }

        viewModel.date.observe(viewLifecycleOwner) {
            binding.cbDate.text = it
        }

        viewModel.viewModelEvent.observe(viewLifecycleOwner) {  event ->
            // TODO dont do this onResume!
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
        // this is after click! so if not checked anymore, it was previously checked
        if(!binding.cbDate.isChecked) {
            val datePicker = WorkoutDatePickerDialog()
            datePicker.setListener(object : WorkoutDatePickerDialog.DateDialogListener {
                override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
                    viewModel.date.postValue(
                        Calendar.getInstance().convertToCorrectFormat(dayOfMonth, month, year)
                    )
                }
            })
            datePicker.show(parentFragmentManager, "STRING") // fix this & yyyy date
        } else {
            viewModel.date.postValue(
                Calendar.getInstance().getDateFormatted()
            )
        }
    }

    private fun resetInputs() {
        binding.etWeight.resetText()
        binding.etReps.resetText()
        binding.etSets.resetText()
        binding.etNote.resetText()
        binding.cbDate.text = Calendar.getInstance().getDateFormatted()
        binding.cbDate.isChecked = true
    }
}