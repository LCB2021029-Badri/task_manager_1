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
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TaskViewModelTest {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskDatabase: TaskDatabase
    private lateinit var taskRepository: TaskRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // Create an in-memory database for testing
        taskDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskDatabase::class.java
        ).build()

        // Initialize the TaskRepository with the DAO
        taskRepository = TaskRepository(taskDatabase.taskDao())

        // Initialize the TaskViewModel with the Application context
        viewModel = TaskViewModel(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        // Close the database after the tests
        taskDatabase.close()
    }

    @Test
    fun setSelectedDate() {
        val date: Long = System.currentTimeMillis()
        viewModel.setSelectedDate(date)

        // Assert that the selected date is set correctly
        assertEquals(date, viewModel.getSelectedDate())
    }

    @Test
    fun insert() = runBlocking {
        val task = Task(title = "Test Task", description = "Description", dueDate = System.currentTimeMillis(), priority = 1, isCompleted = false)

        // Create a typed observer for LiveData<List<Task>>
        val observer = mock(Observer::class.java) as Observer<List<Task>>
        viewModel.allTasks.observeForever(observer)

        // Act
        viewModel.insert(task)

        // Assert that the observer is notified once
        verify(observer, times(1)).onChanged(anyList())
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

        // Assert that the observer gets called
        verify(observer, times(1)).onChanged(anyList())
    }


}
