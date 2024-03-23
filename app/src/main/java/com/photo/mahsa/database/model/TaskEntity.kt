package com.photo.mahsa.database.model

import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.photo.mahsa.model.Task

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Long? = null,
    val title: String,
    val desc: String,
    val imageAddress: String? = null,
    val timestamp: Long
) {
}

fun TaskEntity.asExternalModel() = Task(
    id = this.id!!,
    title = this.title,
    desc = this.desc,
    image = BitmapFactory.decodeFile(this.imageAddress),
    timestamp = this.timestamp
)