package com.photo.mahsa.data

import android.database.sqlite.SQLiteException
import com.photo.mahsa.database.dao.TaskDao
import com.photo.mahsa.database.model.TaskEntity
import com.photo.mahsa.database.model.asExternalModel
import com.photo.mahsa.model.Task
import com.photo.mahsa.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface Repository {
    fun loadTasks(): Flow<List<Task>>

    suspend fun addTask(task: Task): Result<Long>
}

class RepositoryImp @Inject constructor(
    private val taskDao: TaskDao
): Repository {
    override fun loadTasks(): Flow<List<Task>> {
        return taskDao.loadAll().map { it.map(TaskEntity::asExternalModel)}
    }

    override suspend fun addTask(task: Task): Result<Long> {
        val result = taskDao.insert(task.toTaskEntity())
        if (result == -1L) {
            return Result.Error(SQLiteException())
        }
        return Result.Success(result)
    }
}