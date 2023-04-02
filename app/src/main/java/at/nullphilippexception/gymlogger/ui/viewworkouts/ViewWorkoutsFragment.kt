package at.nullphilippexception.gymlogger.ui.viewworkouts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.databinding.FragmentAddWorkoutBinding
import at.nullphilippexception.gymlogger.databinding.FragmentViewWorkoutsBinding
import at.nullphilippexception.gymlogger.ui.addexercise.WorkoutsListRecyclerAdapter
import at.nullphilippexception.gymlogger.ui.addworkout.AddWorkoutViewModel
import at.nullphilippexception.gymlogger.ui.viewworkouts.editworkout.WORKOUT_ID


class ViewWorkoutsFragment : Fragment(), WorkoutsListRecyclerAdapter.ItemClickListener {
    private lateinit var binding: FragmentViewWorkoutsBinding
    private val viewModel: ViewWorkoutsViewModel by viewModels()
    private lateinit var adapter: WorkoutsListRecyclerAdapter

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
            adapter = WorkoutsListRecyclerAdapter(requireContext(),it)
            adapter.setClickListener(this@ViewWorkoutsFragment)
            binding.rvWorkouts.adapter = adapter
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.e("ITEM CLICK", "onItemClick called")
        val workout = adapter.getItem(position)
        val bundle = Bundle()
        bundle.putString(WORKOUT_ID, workout.uid)
        findNavController().navigate(R.id.action_goToEditWorkout,bundle)
    }
}