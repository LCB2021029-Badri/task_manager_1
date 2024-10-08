package com.example.taskmanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.repository.TaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class TaskViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var taskViewModel: TaskViewModel

    @Mock
    private lateinit var taskRepository: TaskRepository

    private val dummyTask = Task(
        id = 1,
        title = "Test Task",
        description = "This is a test task",
        dueDate = 1633046400000, // Sample timestamp
        priority = 1,
        isCompleted = false
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        taskViewModel = TaskViewModel(ApplicationProvider.getApplicationContext()).apply {
            repository = taskRepository // Use the mocked repository
        }
    }

    @Test
    fun insert() = runBlocking {
        // Act
        taskViewModel.insert(dummyTask)

        // Assert
        verify(taskRepository).insert(dummyTask)
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }
}