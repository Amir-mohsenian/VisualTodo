package com.photo.mahsa.model

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Task(
    val id: Long? = null,
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

    var titleState by mutableStateOf(title)
    var descState by mutableStateOf(desc)
}