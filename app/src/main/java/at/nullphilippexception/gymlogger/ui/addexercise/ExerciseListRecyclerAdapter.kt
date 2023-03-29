package at.nullphilippexception.gymlogger.ui.addexercise
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import at.nullphilippexception.gymlogger.R
import at.nullphilippexception.gymlogger.model.Exercise


class ExerciseListRecyclerAdapter internal constructor(private val context: Context,
                                                       private val exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseListRecyclerAdapter.ViewHolder?>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = layoutInflater.inflate(R.layout.view_exercise_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvText.text = exercises[position].name
        holder.ivIcon.setImageDrawable(AppCompatResources
            .getDrawable(context, ExerciseType.valueOf(exercises[position].category).drawable)
        )
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        lateinit var tvText: TextView
        lateinit var ivIcon: ImageView

        init {
            tvText = itemView.findViewById(R.id.tv_text)
            ivIcon = itemView.findViewById(R.id.iv_icon)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            clickListener?.onItemClick(view, adapterPosition)
        }
    }

    fun getItem(id: Int): Exercise {
        return exercises[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}