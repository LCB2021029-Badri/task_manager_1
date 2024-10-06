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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTaskBinding.inflate(inflater, container, false)

        val adapter = TaskAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks?.let { adapter.submitList(it) }
        }

        binding.fabAddTask.setOnClickListener {
            navigateToAddTaskFragment() // Call the function to navigate to the new fragment
        }

        return binding.root
    }

    // Function to handle navigation to AddTaskFragment
    private fun navigateToAddTaskFragment() {
        val addTaskFragment = AddTaskFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, addTaskFragment) // Use the correct container ID
            .addToBackStack(null) // Optional: Add to back stack to allow back navigation
            .commit()
    }

    override fun onTaskClick(task: Task) {
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
