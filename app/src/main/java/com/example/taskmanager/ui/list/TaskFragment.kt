package com.example.taskmanager.ui.list

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
            navigateToFragment(addTaskFragment)
        }

        binding.fabDarkMode.setOnClickListener {
            val settingsFragment = SettingsFragment()
            navigateToFragment(settingsFragment)
        }

        // Handle delete in selection mode
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
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onTaskClick(task: Task) {
        if (isSelectionMode) {
            // Toggle selection when in selection mode
            adapter.toggleSelection(task)
            checkIfSelectionModeActive() // Exit selection mode if no items are selected
        } else {
            // Navigate to edit fragment when not in selection mode
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

    override fun onTaskLongClick(task: Task) {
        if (!isSelectionMode) {
            enterSelectionMode() // Enter selection mode on long-click
        }
        adapter.toggleSelection(task)
    }

    override fun onTaskCompletionChanged(task: Task) {
        // Update the task in the database via ViewModel
        taskViewModel.update(task)
    }

    private fun deleteSelectedTasks() {
        val selectedTasks = adapter.getSelectedTasks()
        selectedTasks.forEach { taskViewModel.delete(it) }
        adapter.clearSelection()
    }

    private fun enterSelectionMode() {
        isSelectionMode = true
        // Show delete button or perform any UI update for selection mode
    }

    private fun exitSelectionMode() {
        isSelectionMode = false
        adapter.clearSelection()
        // Hide delete button or reset any selection UI
    }

    private fun checkIfSelectionModeActive() {
        if (adapter.getSelectedTasks().isEmpty()) {
            exitSelectionMode() // Exit selection mode if no items are selected
        }
    }
}