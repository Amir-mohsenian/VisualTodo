package com.photo.mahsa.add_task

import com.photo.mahsa.features.add_task.AddTaskViewModel
import com.photo.mahsa.model.Task
import com.photo.mahsa.model.TaskPriority
import com.photo.mahsa.usecase.AddTaskUseCase
import com.photo.mahsa.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@kotlinx.coroutines.ExperimentalCoroutinesApi
class AddTaskViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var addTaskUseCase: AddTaskUseCase

    private lateinit var viewModel: AddTaskViewModel

    @Before
    fun setup() {
        viewModel = AddTaskViewModel(addTaskUseCase)
    }

    @Test
    fun verifyAddTaskUseCaseCall() = runTest {
        val task = Task(
            1,
            "title",
            "",
            null,
            imagePath = null,
            TaskPriority.LOW,
            System.currentTimeMillis()
        )
        viewModel.addTask(
            task
        )

        verify(addTaskUseCase).invoke(task)
    }

    @Test
    fun produceErrorWhenTaskIsNotValid() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        val task = Task(
            1,
            "",
            "",
            null,
            imagePath = null,
            TaskPriority.LOW,
            System.currentTimeMillis()
        )
        viewModel.addTask(
            task
        )

        verify(addTaskUseCase, never()).invoke(task)
        assertTrue(viewModel.uiState.value.errorMessage != null)
        collectJob.cancel()
    }
}