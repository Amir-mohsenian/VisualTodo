package com.photo.mahsa.data

import com.photo.mahsa.ui.model.Task
import javax.inject.Inject

interface Repository {
    suspend fun loadTasks(): List<Task>
}

class RepositoryImp @Inject constructor(): Repository {
    override suspend fun loadTasks(): List<Task> {
        return emptyList()
    }
}