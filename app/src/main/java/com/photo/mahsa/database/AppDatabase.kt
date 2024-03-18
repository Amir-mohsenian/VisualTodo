package com.photo.mahsa.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.photo.mahsa.database.dao.TaskDao
import com.photo.mahsa.database.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}