package com.photo.mahsa.data

import com.photo.mahsa.ui.model.Task

object FakeRepository: Repository {
    override suspend fun loadTasks(): List<Task> {
        return tasksTestData
    }
}

