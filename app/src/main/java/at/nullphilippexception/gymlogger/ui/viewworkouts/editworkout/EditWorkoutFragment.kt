package at.nullphilippexception.gymlogger.ui.viewworkouts.editworkout

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.databinding.FragmentEditWorkoutBinding
import at.nullphilippexception.gymlogger.model.nullSafeString
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent
import at.nullphilippexception.gymlogger.util.WorkoutDatePickerDialog
import at.nullphilippexception.gymlogger.util.convertToCorrectFormat
import kotlinx.coroutines.launch
import java.util.*

const val WORKOUT_ID = "workout_id"

class EditWorkoutFragment : Fragment() {

    private var workoutId: String? = null
    private lateinit var binding: FragmentEditWorkoutBinding
    private val viewModel: EditWorkoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workoutId = it.getString(WORKOUT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutId?.let {
                    viewModel.loadWorkout(it)
                } ?: Toast.makeText(requireContext(),
                    resources.getString(R.string.frag_edit_workout_event_error_loading),
                    Toast.LENGTH_LONG).show()
            }
        }

        viewModel.workout.observe(viewLifecycleOwner) { workout ->
            binding.tvExercise.text = workout.exercise.name
            binding.tilSets.hint = "${resources.getString(R.string.frag_add_workout_et_sets_hint)}: ${workout.sets}"
            binding.tilReps.hint = "${resources.getString(R.string.frag_add_workout_et_reps_hint)}: ${workout.reps}"
            binding.tilWeight.hint = "${resources.getString(R.string.frag_add_workout_et_weight_hint)}: ${workout.weight}"
            binding.tvDate.text = workout.date
            binding.etNote.text = Editable.Factory.getInstance().newEditable(workout.note)
        }

        viewModel.viewModelEvent.observe(viewLifecycleOwner) {  event ->
            // TODO refactor to proper stuff here
            when(event) {
                ViewModelEvent.INSERT_SUCCESS -> {
                    Toast.makeText(requireContext(), "insert successful", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }

                ViewModelEvent.INSERT_FAILURE ->
                    Toast.makeText(requireContext(), "insert failure", Toast.LENGTH_SHORT).show()

                ViewModelEvent.DELETE_SUCCESS -> {
                    Toast.makeText(requireContext(), "deletion successful", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }

        binding.tvDate.setOnClickListener {
            val datePicker = WorkoutDatePickerDialog()
            datePicker.setListener(object : WorkoutDatePickerDialog.DateDialogListener {
                override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
                    binding.tvDate.text = Calendar.getInstance().convertToCorrectFormat(dayOfMonth, month, year)
                }
            })
            datePicker.show(parentFragmentManager, "STRING") // fix tag
        }

        binding.btnSave.setOnClickListener {
            viewModel.saveEditedWorkout(
                sets = binding.etSets.nullSafeString(),
                reps = binding.etReps.nullSafeString(),
                weight = binding.etWeight.nullSafeString(),
                date = binding.tvDate.nullSafeString(),
                note = binding.etNote.nullSafeString()
            )
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteCurrentWorkout()
        }
    }
}