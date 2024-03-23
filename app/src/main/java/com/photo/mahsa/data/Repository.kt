package com.photo.mahsa.data

import com.photo.mahsa.database.dao.TaskDao
import com.photo.mahsa.database.model.TaskEntity
import com.photo.mahsa.database.model.asExternalModel
import com.photo.mahsa.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface Repository {
    fun loadTasks(): Flow<List<Task>>
}

class RepositoryImp @Inject constructor(
    private val taskDao: TaskDao
): Repository {
    override fun loadTasks(): Flow<List<Task>> {
        return taskDao.loadAll().map { it.map(TaskEntity::asExternalModel)}
    }
}