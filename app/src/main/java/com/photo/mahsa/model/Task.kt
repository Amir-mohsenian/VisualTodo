package com.photo.mahsa.model

import android.graphics.Bitmap

data class Task(
    val id: Long,
    val title: String,
    val desc: String,
    val image: Bitmap? = null,
    val imagePath: String? = null,
    val priority: TaskPriority = TaskPriority.LOW,
    val timestamp: Long
) {
    fun isValid(): Boolean {
        return !(title.isEmpty() && desc.isEmpty() && image == null && imagePath.isNullOrEmpty())
    }
}