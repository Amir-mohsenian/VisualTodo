package com.photo.mahsa.usecase

import com.photo.mahsa.data.Repository
import com.photo.mahsa.data.tasksTestData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetTasksUseCaseTest {
    private val repository = mock<Repository>()
    private lateinit var getTasksUseCase: GetTasksUseCase

    @Before
    fun setUp() {
        getTasksUseCase = GetTasksUseCase(repository)
    }

    @Test
    fun loadTasksFromRepository() {
        whenever(repository.loadTasks()).thenReturn(flowOf(tasksTestData))
        getTasksUseCase.invoke()
        verify(repository, times(1)).loadTasks()
    }
}