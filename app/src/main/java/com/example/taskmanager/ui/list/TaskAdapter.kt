import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.databinding.TaskItemBinding
import com.example.taskmanager.interfaces.OnTaskClickListener
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(private val listener: OnTaskClickListener) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.textViewTitle.text = task.title
            binding.textViewDescription.text = task.description
            binding.textViewDueDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(task.dueDate))
            binding.priorityIndicator.setImageResource(
                when (task.priority) {
                    1 -> R.color.priority_high
                    2 -> R.color.priority_medium
                    else -> R.color.priority_low
                }
            )

            // handling on item click events
            binding.root.setOnClickListener {
                listener.onTaskClick(task)
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
