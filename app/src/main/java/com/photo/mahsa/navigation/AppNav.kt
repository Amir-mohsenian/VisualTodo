package com.photo.mahsa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.photo.mahsa.features.add_task.AddTaskRoute
import com.photo.mahsa.features.edit_task.EditTaskRoute
import com.photo.mahsa.features.tasks.TasksRoute

sealed class Screens(val route: String) {
    data object Tasks : Screens("tasks")
    data object AddTask : Screens("add_task")

    data object EditTask : Screens("edit_task/{id}") {
        fun createRoute(taskId: String) = "edit_task/$taskId"
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Tasks.route,
        modifier = modifier
    ) {
        composable(Screens.Tasks.route) {
            TasksRoute {

            }
        }

        composable(Screens.AddTask.route) {
            AddTaskRoute()
        }

        composable(Screens.EditTask.route) {
            EditTaskRoute()
        }
    }
}