package com.photo.mahsa.features.tasks

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    onEditTask: () -> Unit
) {
    Log.i("****LOOOG", "Route Task")
}

@Composable
fun TasksScreen(

) {

}

