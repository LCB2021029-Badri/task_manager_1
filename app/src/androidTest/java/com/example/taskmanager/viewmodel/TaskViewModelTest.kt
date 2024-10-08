package com.example.taskmanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.repository.TaskRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import org.mockito.Mockito.anyList

@RunWith(AndroidJUnit4::class)
class TaskViewModelTest {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskDatabase: TaskDatabase
    private lateinit var taskRepository: TaskRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        taskDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskDatabase::class.java
        ).build()
        taskRepository = TaskRepository(taskDatabase.taskDao())
        viewModel = TaskViewModel(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        taskDatabase.close()
    }

    @Test
    fun insert() = runBlocking {
        val task = Task(title = "Test Task", description = "Description", dueDate = System.currentTimeMillis(), priority = 1, isCompleted = false)

        // Create a typed observer for LiveData<List<Task>>
        val observer = mock(Observer::class.java) as Observer<List<Task>>
        viewModel.allTasks.observeForever(observer)

        // Act
        viewModel.insert(task)

        // Assert
        verify(observer, times(1)).onChanged(anyList()) // Verify that the observer gets called
    }

    @Test
    fun update() = runBlocking {
        // Insert a task first
        val task = Task(title = "Test Task", description = "Description", dueDate = System.currentTimeMillis(), priority = 1, isCompleted = false)
        viewModel.insert(task)

        // Update the task
        val updatedTask = task.copy(title = "Updated Task")

        // Create a typed observer for LiveData<List<Task>>
        val observer = mock(Observer::class.java) as Observer<List<Task>>
        viewModel.allTasks.observeForever(observer)

        // Act
        viewModel.update(updatedTask)

        // Assert
        verify(observer, times(1)).onChanged(anyList()) // Verify that the observer gets called
    }

    @Test
    fun delete() = runBlocking {
        // Insert a task first
        val task = Task(title = "Test Task", description = "Description", dueDate = System.currentTimeMillis(), priority = 1, isCompleted = false)
        viewModel.insert(task)

        // Create a typed observer for LiveData<List<Task>>
        val observer = mock(Observer::class.java) as Observer<List<Task>>
        viewModel.allTasks.observeForever(observer)

        // Act
        viewModel.delete(task)

        // Assert
        verify(observer, times(1)).onChanged(anyList()) // Verify that the observer gets called
    }

    @Test
    fun setSelectedDate() {
        val date: Long = System.currentTimeMillis()
        viewModel.setSelectedDate(date)

        // Assert
        assertEquals(date, viewModel.getSelectedDate())
    }
}
