package com.photo.mahsa.model

enum class TaskPriority {
    HIGH,
    MEDIUM,
    LOW
}

fun TaskPriority.toInt() = when (this) {
    TaskPriority.HIGH -> 2
    TaskPriority.MEDIUM -> 1
    TaskPriority.LOW -> 0
}

fun Int.toTaskPriority(): TaskPriority = when (this) {
    2 -> TaskPriority.HIGH
    1 -> TaskPriority.MEDIUM
    0 -> TaskPriority.LOW
    else -> throw Exception("Number is not in priority range, number : $this")
}