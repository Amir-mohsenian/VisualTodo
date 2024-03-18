package com.photo.mahsa.tasks

import com.photo.mahsa.data.FakeRepository
import com.photo.mahsa.features.tasks.GetTasksUiState
import com.photo.mahsa.features.tasks.TasksViewModel
import com.photo.mahsa.usecase.GetTasksUseCase
import com.photo.mahsa.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Assert.*
import org.junit.Test


class TasksViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getTasksUseCase = GetTasksUseCase(FakeRepository)
    private lateinit var viewModel: TasksViewModel

    @Before
    fun setup() {
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
        assertEquals(2, tasks.size)
        collectJob.cancel()
    }
}