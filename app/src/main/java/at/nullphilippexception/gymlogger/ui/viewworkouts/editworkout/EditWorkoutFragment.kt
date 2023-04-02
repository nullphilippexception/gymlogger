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
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.databinding.FragmentEditWorkoutBinding
import kotlinx.coroutines.launch

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

        binding.btnSave.setOnClickListener {
            // onclick code here
        }

        binding.btnDelete.setOnClickListener {
            // onclick code here
        }
    }
}