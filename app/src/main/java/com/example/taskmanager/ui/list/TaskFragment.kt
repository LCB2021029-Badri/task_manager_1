package com.example.taskmanager.ui.list

import AddTaskFragment
import TaskAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.interfaces.OnTaskClickListener
import com.example.taskmanager.viewmodel.TaskViewModel

class TaskFragment : Fragment(), OnTaskClickListener {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter
    private var isSelectionMode = false // Track if selection mode is active

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTaskBinding.inflate(inflater, container, false)

        adapter = TaskAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks?.let { adapter.submitList(it) }
        }

        binding.fabAddTask.setOnClickListener {
            val addTaskFragment = AddTaskFragment()
            navigateToFragment(addTaskFragment) // Call the function to navigate to the new fragment
        }

        binding.fabDarkMode.setOnClickListener {
            val settingsFragment = SettingsFragment()
            navigateToFragment(settingsFragment)
        }

        binding.fabSelectMode.setOnClickListener {
            toggleSelectionMode()
        }

        // Handle long press for selection
        binding.fabDeleteMode.setOnClickListener {
            if (isSelectionMode) {
                deleteSelectedTasks()
                exitSelectionMode()
            }
        }

        return binding.root
    }

    // Function to handle navigation to AddTaskFragment
    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Use the correct container ID
            .addToBackStack(null) // Optional: Add to back stack to allow back navigation
            .commit()
    }

    override fun onTaskClick(task: Task) {
        if (isSelectionMode) {
            // If selection mode is active, don't navigate, just toggle the selection
            adapter.toggleSelection(task)
//            adapter.getSelectedTasks()
        } else {
            // Navigate to the edit fragment and pass the task as a Bundle
            val editTaskFragment = EditFragment()
            val bundle = Bundle().apply {
                putParcelable("task", task)
            }
            editTaskFragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, editTaskFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    // Function to delete selected tasks
    private fun deleteSelectedTasks() {
        val selectedTasks = adapter.getSelectedTasks()
        selectedTasks.forEach { taskViewModel.delete(it) } // Delete selected tasks from the ViewModel
        adapter.clearSelection()
    }

    // Function to exit selection mode
    private fun exitSelectionMode() {
        isSelectionMode = false
        adapter.clearSelection()
        // Optionally hide the delete button or action bar
    }

    // Function to enter selection mode
    private fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode
        // Optionally show the delete button or action bar
    }
}
