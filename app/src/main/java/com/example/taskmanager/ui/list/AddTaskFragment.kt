import android.app.DatePickerDialog
import android.widget.TextView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDueDate: TextView // Now it's TextView instead of EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var buttonSave: Button

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        // Initialize UI elements
        editTextTitle = view.findViewById(R.id.editTextTitle)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        editTextDueDate = view.findViewById(R.id.editTextDueDate) // Cast TextView
        spinnerPriority = view.findViewById(R.id.spinnerPriority)
        buttonSave = view.findViewById(R.id.buttonSave)

        // Setup the Spinner for Priority Levels
        val priorities = arrayOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        // Set onClickListener for Save button
        buttonSave.setOnClickListener {
            saveTask()
        }

        // Set onClickListener for Due Date TextView
        editTextDueDate.setOnClickListener {
            showDatePickerDialog(editTextDueDate) // Show date picker dialog
        }

        return view
    }

    // Show date picker dialog to select a due date
    private fun showDatePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Format the selected date as dd/MM/yyyy and set it in the TextView
            val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            textView.text = formattedDate
        }, year, month, day)

        // Set a minimum date to avoid selecting past dates
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun saveTask() {
        // Get user input
        val title = editTextTitle.text.toString().trim()
        val description = editTextDescription.text.toString().trim()
        val dueDateString = editTextDueDate.text.toString().trim() // Now it's from TextView
        val priorityString = spinnerPriority.selectedItem.toString()

        // Validate input
        if (title.isEmpty() || description.isEmpty() || dueDateString.isEmpty()) {
            // Handle validation error (e.g., show a Toast or Snackbar)
            return
        }

        // Parse due date
        val dueDate = parseDate(dueDateString)

        // Convert priority from String to Int
        val priority = when (priorityString) {
            "High" -> 1
            "Medium" -> 2
            "Low" -> 3
            else -> 2 // Default to Medium if somehow invalid
        }

        // Create a new Task object
        val task = Task(title = title, description = description, dueDate = dueDate ?: 0, priority = priority, isCompleted = false)

        // Insert task into ViewModel
        taskViewModel.insert(task)

        // Optionally, navigate back or clear the input fields
        clearFields()
    }

    private fun parseDate(dateString: String): Long? {
        return try {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            format.isLenient = false // Strict parsing
            val date = format.parse(dateString)
            date?.time // Convert Date to timestamp (Long)
        } catch (e: Exception) {
            e.printStackTrace()
            null // Return null in case of error
        }
    }

    private fun clearFields() {
        editTextTitle.text.clear()
        editTextDescription.text.clear()
        editTextDueDate.text = "" // Clear the TextView for due date
        spinnerPriority.setSelection(0)
    }
}
