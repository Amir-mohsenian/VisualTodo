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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TasksViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getTasksUseCase: GetTasksUseCase

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
    fun sortTasksByPriority() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.tasksUiState.collect() }
        viewModel.sortTasksByPriority()
        assertTrue((viewModel.tasksUiState.value as GetTasksUiState.Success).tasks[0].priority == TaskPriority.HIGH)
        assertTrue((viewModel.tasksUiState.value as GetTasksUiState.Success).tasks[1].priority == TaskPriority.MEDIUM)
        assertTrue((viewModel.tasksUiState.value as GetTasksUiState.Success).tasks[2].priority == TaskPriority.LOW)
        assertTrue((viewModel.tasksUiState.value as GetTasksUiState.Success).tasks[3].priority == TaskPriority.LOW)
        collectJob.cancel()
    }

    @Test
    fun sortTasksByTimestamp() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.tasksUiState.collect() }
        viewModel.sortTasksByDateTime()
        val tasks = (viewModel.tasksUiState.value as GetTasksUiState.Success).tasks
        assertTrue(tasks[0].timestamp > tasks[1].timestamp)
        assertTrue(tasks[0].timestamp > tasks[2].timestamp)
        collectJob.cancel()
    }
}