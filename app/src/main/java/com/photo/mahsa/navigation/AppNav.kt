package com.photo.mahsa.navigation

sealed class Screens(val route: String) {
    data object Tasks: Screens("tasks")
    data object AddTask: Screens("add_task")

    data object EditTask: Screens("edit_task")
}