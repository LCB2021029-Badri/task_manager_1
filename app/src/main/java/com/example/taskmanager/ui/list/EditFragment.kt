package com.example.taskmanager.ui.list

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class EditFragment : Fragment() {

    private lateinit var task: Task
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
        val view = inflater.inflate(R.layout.fragment_edit, container, false)

        // Initialize UI elements
        editTextTitle = view.findViewById(R.id.editTextTitle)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        editTextDueDate = view.findViewById(R.id.editTextDueDate)
        spinnerPriority = view.findViewById(R.id.spinnerPriority)
        buttonSave = view.findViewById(R.id.buttonSave)

        // Retrieve the passed task object
        arguments?.let {
            task = it.getParcelable("task") ?: return@let
            populateFields(task)
        }

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        // Observe selectedDate LiveData from the ViewModel
        taskViewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            if (date != null) {
                // Update the due date TextView with the formatted date
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(date))
                editTextDueDate.text = formattedDate
            } else {
                // If no new date is selected, show the task's stored due date
                editTextDueDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(task.dueDate))
            }
        }

        // Set onClickListener for Save button
        buttonSave.setOnClickListener {
            updateTask()
            val taskFragment = TaskFragment()
            navigateToFragment(taskFragment)
        }

        // Set onClickListener for Due Date TextView
        editTextDueDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(
            enabled = true
        ) {
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

    private fun populateFields(task: Task) {
        editTextTitle.setText(task.title)
        editTextDescription.setText(task.description)

        // Display stored due date initially
        editTextDueDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(task.dueDate))

        // Set priority in the spinner
        val priority = when (task.priority) {
            1 -> "High"
            2 -> "Medium"
            3 -> "Low"
            else -> "Medium"
        }
        val priorities = arrayOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        spinnerPriority.adapter = adapter
        spinnerPriority.setSelection(priorities.indexOf(priority))
    }

    private fun updateTask() {
        // Update the task object with new values from the input fields
        task.title = editTextTitle.text.toString()
        task.description = editTextDescription.text.toString()

        // Use the selectedDate from ViewModel if set, otherwise use the task's original due date
        task.dueDate = taskViewModel.getSelectedDate() ?: task.dueDate

        task.priority = when (spinnerPriority.selectedItem.toString()) {
            "High" -> 1
            "Medium" -> 2
            "Low" -> 3
            else -> task.priority
        }

        // Update the task in the ViewModel (and therefore the database)
        taskViewModel.update(task)

        // Optionally, navigate back or clear the input fields
        clearFields()
    }

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

    private fun clearFields() {
        editTextTitle.text.clear()
        editTextDescription.text.clear()
        editTextDueDate.text = ""
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