package at.nullphilippexception.gymlogger.ui.viewworkouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.databinding.FragmentAddWorkoutBinding
import at.nullphilippexception.gymlogger.databinding.FragmentViewWorkoutsBinding
import at.nullphilippexception.gymlogger.ui.addexercise.WorkoutsListRecyclerAdapter
import at.nullphilippexception.gymlogger.ui.addworkout.AddWorkoutViewModel


class ViewWorkoutsFragment : Fragment() {
    private lateinit var binding: FragmentViewWorkoutsBinding
    private val viewModel: ViewWorkoutsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewWorkoutsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.workouts.observe(viewLifecycleOwner) {
            binding.rvWorkouts.adapter = WorkoutsListRecyclerAdapter(requireContext(),it)
        }
    }
}