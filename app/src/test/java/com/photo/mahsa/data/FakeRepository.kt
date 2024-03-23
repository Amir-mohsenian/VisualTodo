package com.photo.mahsa.data

import com.photo.mahsa.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object FakeRepository: Repository {
    override fun loadTasks(): Flow<List<Task>> {
        return flowOf(tasksTestData)
    }
}

