package com.photo.mahsa.usecase

import com.photo.mahsa.data.Repository
import com.photo.mahsa.model.Task
import com.photo.mahsa.result.Result
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun  invoke(
        task: Task
    ): Result<Long> = repository.addTask(task)
}