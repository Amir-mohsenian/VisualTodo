package com.photo.mahsa.usecase

import com.photo.mahsa.data.Repository
import com.photo.mahsa.ui.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun  invoke(): Flow<List<Task>> = repository.loadTasks()
}