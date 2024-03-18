package com.photo.mahsa.tasks

import com.photo.mahsa.data.FakeRepository
import com.photo.mahsa.features.tasks.TasksViewModel
import com.photo.mahsa.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Assert.*
import org.junit.Test

class TasksViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TasksViewModel

    @Before
    fun setup() {
        viewModel = TasksViewModel(FakeRepository)
    }

    @Test
    fun showLoadingWhenFetchingTasksIsStarted() = runTest {
        viewModel.loadTasks()
        assertEquals(true, viewModel.uiState.value.loading)
    }

    @Test
    fun tasksMustNotBeEmptyWhenDbHasTasks() = runTest {
        viewModel.loadTasks()
        assertEquals(2, viewModel.uiState.value.tasks.size)
    }

    @Test
    fun hideLoadingWhenTaskWasFetched() = runTest {
        viewModel.loadTasks()
        assertEquals(false, viewModel.uiState.value.loading)
    }
}