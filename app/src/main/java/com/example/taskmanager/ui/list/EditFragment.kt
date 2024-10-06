package com.example.taskmanager.ui.list

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
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class EditFragment : Fragment() {

    private lateinit var task: Task
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDueDate: TextView // Assuming date picker is still used here
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

        // Set onClickListener for Save button
        buttonSave.setOnClickListener {
            saveTask()
        }

        return view
    }

    private fun populateFields(task: Task) {
        editTextTitle.setText(task.title)
        editTextDescription.setText(task.description)
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

    private fun saveTask() {
        // Update the task object with new values from the input fields
        task.title = editTextTitle.text.toString()
        task.description = editTextDescription.text.toString()
        task.dueDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(editTextDueDate.text.toString())?.time ?: task.dueDate

        task.priority = when (spinnerPriority.selectedItem.toString()) {
            "High" -> 1
            "Medium" -> 2
            "Low" -> 3
            else -> task.priority
        }

        // Update the task in the ViewModel (and therefore the database)
        taskViewModel.update(task)

        // Navigate back to the task list
        parentFragmentManager.popBackStack()
    }
}