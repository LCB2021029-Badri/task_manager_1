package com.example.taskmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    var repository: TaskRepository
    val allTasks: LiveData<List<Task>>
    val selectedDate = MutableLiveData<Long?>()

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insert(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insert(task)
        }
    }

    fun update(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.update(task)
        }
    }

    fun delete(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.delete(task)
        }
    }

    // preserve date in addTask and editTask fragments
    fun setSelectedDate(date: Long?) {
        selectedDate.value = date
    }

    fun getSelectedDate(): Long? {
        return selectedDate.value
    }
}
