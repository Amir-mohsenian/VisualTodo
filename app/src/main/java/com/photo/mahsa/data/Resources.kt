package com.photo.mahsa.data

import com.photo.mahsa.database.model.TaskEntity
import com.photo.mahsa.model.Task

fun Task.toTaskEntity() = TaskEntity(
    title = this.title,
    desc = this.desc,
    imageAddress = this.imagePath,
    timestamp = this.timestamp
)