package com.photo.mahsa.features.tasks

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    onEditTask: () -> Unit
) {
    Text(text = "Add tasks here")
}

@Composable
fun TasksScreen(

) {

}

