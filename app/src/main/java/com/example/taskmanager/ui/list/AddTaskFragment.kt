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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.ui.list.TaskFragment
import com.example.taskmanager.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDueDate: TextView
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
        editTextDueDate = view.findViewById(R.id.editTextDueDate)
        spinnerPriority = view.findViewById(R.id.spinnerPriority)
        buttonSave = view.findViewById(R.id.buttonSave)

        // Setup the Spinner for Priority Levels
        val priorities = arrayOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        // Observe selectedDate LiveData from the ViewModel
        taskViewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            if (date != null) {
                // Update the due date TextView with the formatted date
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(date))
                editTextDueDate.text = formattedDate
            }
        }

        // Set onClickListener for Save button
        buttonSave.setOnClickListener {
            saveTask()
            val taskFragment = TaskFragment()
            navigateToFragment(taskFragment)
        }

        // Set onClickListener for Due Date TextView
        editTextDueDate.setOnClickListener {
            showDatePickerDialog()
        }


        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Call the function to clear the fields
                clearFields()

                // Disable the callback and allow the system to handle the back press
                isEnabled = false
                requireActivity().onBackPressed()
            }
        })

        return view
    }

    // Show date picker dialog to select a due date
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val calendar = Calendar.getInstance()
            calendar.set(selectedYear, selectedMonth, selectedDay)

            // Save selected date to ViewModel
            taskViewModel.setSelectedDate(calendar.timeInMillis)
        }, year, month, day)

        // Set a minimum date to avoid selecting past dates
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun saveTask() {
        // Get user input
        val title = editTextTitle.text.toString().trim()
        val description = editTextDescription.text.toString().trim()
        val dueDateString = editTextDueDate.text.toString().trim()
        val priorityString = spinnerPriority.selectedItem.toString()

        // Validate input
        if (title.isEmpty() || description.isEmpty() || dueDateString.isEmpty()) {
            // Handle validation error (e.g., show a Toast or Snackbar)
            return
        }

        // Parse due date from ViewModel
        val dueDate = taskViewModel.getSelectedDate()

        // Convert priority from String to Int
        val priority = when (priorityString) {
            "High" -> 1
            "Medium" -> 2
            "Low" -> 3
            else -> 2 // Default to Medium if somehow invalid
        }

        // Create a new Task object
        val task = Task(
            title = title,
            description = description,
            dueDate = dueDate ?: 0L, // Use dueDate from ViewModel
            priority = priority,
            isCompleted = false
        )

        // Insert task into ViewModel
        taskViewModel.insert(task)

        // Optionally, navigate back or clear the input fields
        clearFields()
    }

    private fun clearFields() {
        editTextTitle.text.clear()
        editTextDescription.text.clear()
        editTextDueDate.text = "" // Clear the TextView for due date
        spinnerPriority.setSelection(0)
        taskViewModel.setSelectedDate(null) // Clear the selected date in ViewModel
    }

    // Function to handle navigation to AddTaskFragment
    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


}