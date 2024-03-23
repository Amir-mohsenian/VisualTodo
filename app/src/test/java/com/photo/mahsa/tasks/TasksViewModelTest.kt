package com.photo.mahsa.tasks

import com.photo.mahsa.data.tasksTestData
import com.photo.mahsa.features.tasks.GetTasksUiState
import com.photo.mahsa.features.tasks.TasksViewModel
import com.photo.mahsa.model.TaskPriority
import com.photo.mahsa.usecase.GetTasksUseCase
import com.photo.mahsa.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class TasksViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getTasksUseCase = mock<GetTasksUseCase>()
    private lateinit var viewModel: TasksViewModel

    @Before
    fun setup() {
        whenever(getTasksUseCase.invoke()).thenReturn(flowOf(tasksTestData.shuffled()))
        viewModel = TasksViewModel(getTasksUseCase)
    }

    @Test
    fun showLoadingWhenFetchingTasksIsStarted() = runTest {
        assertEquals(GetTasksUiState.Loading, viewModel.tasksUiState.value)
    }

    @Test
    fun tasksMustNotBeEmptyWhenDbHasTasks() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.tasksUiState.collect() }
        assertTrue(viewModel.tasksUiState.value is GetTasksUiState.Success)
        val tasks = (viewModel.tasksUiState.value as GetTasksUiState.Success).tasks
        verify(getTasksUseCase).invoke()
        assertEquals(tasksTestData.size, tasks.size)
        collectJob.cancel()
    }

    @Test
    fun sortTasksToPriorityWhenUserRequested() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.tasksUiState.collect() }
        viewModel.sortTasksByPriority()
        assertTrue((viewModel.tasksUiState.value as GetTasksUiState.Success).tasks[0].priority == TaskPriority.HIGH)
        assertTrue((viewModel.tasksUiState.value as GetTasksUiState.Success).tasks[1].priority == TaskPriority.MEDIUM)
        assertTrue((viewModel.tasksUiState.value as GetTasksUiState.Success).tasks[2].priority == TaskPriority.LOW)
        assertTrue((viewModel.tasksUiState.value as GetTasksUiState.Success).tasks[3].priority == TaskPriority.LOW)
        collectJob.cancel()
    }
}