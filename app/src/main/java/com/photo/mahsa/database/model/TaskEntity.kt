package com.photo.mahsa.database.model

import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.photo.mahsa.model.Task
import com.photo.mahsa.model.TaskPriority
import com.photo.mahsa.model.toInt
import com.photo.mahsa.model.toTaskPriority

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Long? = null,
    val title: String,
    val desc: String,
    val imageAddress: String? = null,
    val timestamp: Long,
    val taskPriority: Int = TaskPriority.HIGH.toInt()
) {
}

fun TaskEntity.asExternalModel() = Task(
    id = this.id!!,
    title = this.title,
    desc = this.desc,
    image = BitmapFactory.decodeFile(this.imageAddress),
    timestamp = this.timestamp,
    priority = this.taskPriority.toTaskPriority()
)