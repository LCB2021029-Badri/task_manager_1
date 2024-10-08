package com.example.taskmanager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.data.TaskDao
import com.example.taskmanager.data.models.Task
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class TaskRepositoryTest {

    private lateinit var taskRepository: TaskRepository

    @Mock
    private lateinit var taskDao: TaskDao

    private val dummyTask = Task(
        id = 1,
        title = "Test Task",
        description = "This is a test task",
        dueDate = 1633046400000, // Sample timestamp
        priority = 1,
        isCompleted = false
    )

    private val liveDataTasks: LiveData<List<Task>> = MutableLiveData(listOf(dummyTask))

    @Before
    fun setUp() {
        taskDao = mock(TaskDao::class.java)
        taskRepository = TaskRepository(taskDao)
        whenever(taskDao.getAllTasks()).thenReturn(liveDataTasks)
    }

    @Test
    fun insert() = runBlocking {
        // Arrange
        val taskToInsert = dummyTask

        // Act
        taskRepository.insert(taskToInsert)

        // Assert
        verify(taskDao).insertTask(taskToInsert)
    }

    @Test
    fun update() = runBlocking {
        // Arrange
        val taskToUpdate = dummyTask.copy(title = "Updated Test Task")

        // Act
        taskRepository.update(taskToUpdate)

        // Assert
        verify(taskDao).updateTask(taskToUpdate)
    }

    @Test
    fun delete() = runBlocking {
        // Arrange
        val taskToDelete = dummyTask

        // Act
        taskRepository.delete(taskToDelete)

        // Assert
        verify(taskDao).deleteTask(taskToDelete)
    }
}