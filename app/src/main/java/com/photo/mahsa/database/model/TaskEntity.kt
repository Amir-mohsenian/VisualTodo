package com.photo.mahsa.database.model

import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.photo.mahsa.ui.model.Task

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Long? = null,
    val title: String,
    val desc: String,
    val imageAddress: String? = null
) {
}

fun TaskEntity.asExternalModel() = Task(
    id = this.id!!,
    title = this.title,
    desc = this.desc,
    image = BitmapFactory.decodeFile(this.imageAddress)
)