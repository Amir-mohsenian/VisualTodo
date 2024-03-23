package com.photo.mahsa.data

import com.photo.mahsa.model.Task
import com.photo.mahsa.model.TaskPriority

val tasksTestData = listOf(
    Task(
        id = 1,
        title = "fake_title",
        desc = "fake_desc",
        image = null,
        priority = TaskPriority.HIGH,
        timestamp = System.currentTimeMillis() + 3000
    ),
    Task(
        id = 2,
        title = "fake_title2",
        desc = "fake_desc2",
        image = null,
        priority = TaskPriority.LOW,
        timestamp = System.currentTimeMillis() + 8000
    ),
    Task(
        id = 3,
        title = "fake_title2",
        desc = "fake_desc2",
        image = null,
        priority = TaskPriority.MEDIUM,
        timestamp = System.currentTimeMillis() + 25000
    )
    ,
    Task(
        id = 4,
        title = "fake_title2",
        desc = "fake_desc2",
        image = null,
        priority = TaskPriority.LOW,
        timestamp = System.currentTimeMillis() + 10000
    )
)