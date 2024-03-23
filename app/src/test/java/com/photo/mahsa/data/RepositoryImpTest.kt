package com.photo.mahsa.data

import com.photo.mahsa.database.dao.TaskDao
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RepositoryImpTest {
    private val taskDao = mock<TaskDao>()
    private lateinit var repositoryImp: RepositoryImp

    @Before
    fun setup() {
        repositoryImp = RepositoryImp(taskDao)
    }

    @Test
    fun verifyLoadAllTasksFromDb() {
        repositoryImp.loadTasks()
        verify(taskDao).loadAll()
    }
}