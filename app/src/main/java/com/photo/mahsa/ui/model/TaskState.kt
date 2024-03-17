package com.photo.mahsa.ui.model

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable

@Immutable
data class TaskState(
    val id: Long,
    val title: String,
    val desc: String,
    val image: Bitmap? = null
)