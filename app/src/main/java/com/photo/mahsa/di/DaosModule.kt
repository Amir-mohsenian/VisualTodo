package com.photo.mahsa.di

import com.photo.mahsa.database.AppDatabase
import com.photo.mahsa.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTaskDao(
        database: AppDatabase
    ): TaskDao = database.taskDao()
}