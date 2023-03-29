package at.nullphilippexception.gymlogger.ui.addexercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.databinding.FragmentAddExerciseBinding
import at.nullphilippexception.gymlogger.model.nullSafeString
import at.nullphilippexception.gymlogger.ui.addexercise.ViewModelEvent.*
import at.nullphilippexception.gymlogger.ui.addworkout.ViewModelEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddExerciseFragment : Fragment() {
    private lateinit var binding: FragmentAddExerciseBinding
    private val viewModel: AddExerciseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spCategory.adapter = ArrayAdapter(requireContext(),
            R.layout.view_spinner_item, ExerciseType.values())

        viewModel.viewData.observe(viewLifecycleOwner) {
            binding.rvExercises.isVisible = !it.showNoExercisesHint
            binding.tvNoEntries.isVisible = it.showNoExercisesHint
            binding.rvExercises.adapter = ExerciseListRecyclerAdapter(requireContext(), it.exercises)
        }

        viewModel.viewModelEvent.observe(viewLifecycleOwner) {  event ->
            when(event) {
                INSERT_SUCCESS -> {
                    Toast.makeText(requireContext(), getString(R.string.frag_add_exercise_event_success), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_goToAddWorkout)
                }
                INSERT_FAILURE ->
                    Toast.makeText(requireContext(), getString(R.string.frag_add_exercise_event_failure), Toast.LENGTH_SHORT).show()

            }
        }

        binding.btnAdd.setOnClickListener {
            viewModel.insertExercise(
                name = binding.etName.nullSafeString(),
                category = binding.spCategory.selectedItem.toString()
            )
        }
    }
}