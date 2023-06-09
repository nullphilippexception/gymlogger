package at.nullphilippexception.gymlogger.ui.addexercise
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.model.Workout
import at.nullphilippexception.gymlogger.model.Workout.Companion.EMPTY_STRING


class WorkoutsListRecyclerAdapter internal constructor(private val context: Context,
                                                       private val workouts: List<Workout>) :
    RecyclerView.Adapter<WorkoutsListRecyclerAdapter.ViewHolder?>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = layoutInflater.inflate(R.layout.view_workout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = workouts[position].exercise
        holder.tvExercise.text = exercise.name
        holder.tvDate.text = workouts[position].date
        holder.tvSets.text = workouts[position].getSetsString(context)
        holder.tvReps.text = workouts[position].getRepsString(context)
        holder.tvWeight.text = workouts[position].getWeightString(context)
        val note = workouts[position].note

        if(note != EMPTY_STRING) {
            holder.tvNote.visibility = VISIBLE
            holder.tvNote.text = workouts[position].note
        } else {
            holder.tvNote.visibility = GONE
        }

        try {
            holder.ivIcon.setImageDrawable(
                AppCompatResources
                    .getDrawable(context, ExerciseType.valueOf(exercise.category).drawable)
            )
        } catch(iae: IllegalArgumentException) {
            // this error is thrown when exercise.category is unknown
            // should never happen, as user is not free to enter exercise category on their own
            Log.e("UNKNOWN EXERCISE", iae.message ?: "no error message")
        }

    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var tvExercise: TextView
        var tvDate: TextView
        var tvReps: TextView
        var tvSets: TextView
        var tvWeight: TextView
        var tvNote: TextView
        var ivIcon: ImageView

        init {
            tvExercise = itemView.findViewById(R.id.tv_exercise)
            tvDate = itemView.findViewById(R.id.tv_date)
            tvReps = itemView.findViewById(R.id.tv_reps)
            tvSets = itemView.findViewById(R.id.tv_sets)
            tvWeight = itemView.findViewById(R.id.tv_weight)
            tvNote = itemView.findViewById(R.id.tv_note)
            ivIcon = itemView.findViewById(R.id.iv_icon)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            clickListener?.onItemClick(view, adapterPosition)
        }
    }

    fun getItem(id: Int): Workout {
        return workouts[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}