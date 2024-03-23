package com.photo.mahsa.model

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable

data class Task(
    val id: Long,
    val title: String,
    val desc: String,
    val image: Bitmap? = null,
    val priority: TaskPriority = TaskPriority.LOW,
    val timestamp: Long
)