package com.photo.mahsa.features.tasks

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.photo.mahsa.R
import com.photo.mahsa.model.Task
import com.photo.mahsa.model.TaskPriority
import com.photo.mahsa.ui.components.PriorityIcon
import com.photo.mahsa.ui.theme.MahsaTheme

@Composable
fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    onEditTask: () -> Unit
) {
    val uiState by viewModel.tasksUiState.collectAsStateWithLifecycle()


}

@Composable
fun TasksScreen(
    uiState: GetTasksUiState,
    onSortByPriority: () -> Unit,
    onSortByDateTime: () -> Unit,
    onEditTask: (Task) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            SortButton(
                modifier = Modifier.weight(0.5f),
                title = R.string.title_sort_priority,
                icon = Icons.Default.ArrowDropDown
            )
            SortButton(
                modifier = Modifier.weight(0.5f),
                title = R.string.title_sort_date_time,
                icon = Icons.Default.ArrowDropDown
            )
        }

        if (uiState is GetTasksUiState.Success) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(uiState.tasks) {
                    TaskItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(92.dp), item = it
                    ) { task ->
                        onEditTask(task)
                    }
                }
            }
        }

        if (uiState is GetTasksUiState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(48.dp)
            )
        }

        if (uiState is GetTasksUiState.LoadFailed) {
            Text(text = stringResource(id = R.string.title_empty_list))
        }
    }
}

@Composable
private fun TaskItem(modifier: Modifier = Modifier, item: Task, onTaskClick: (Task) -> Unit) {
    Column(modifier = modifier.padding(horizontal = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.weight(1f))
            PriorityIcon(priority = item.priority, modifier = Modifier.size(24.dp))
        }

        Text(
            text = item.desc,
            maxLines = 5,
            modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskItemPreview() {
    MahsaTheme {
        TaskItem(
            modifier = Modifier.fillMaxWidth(),
            item = Task(
                id = 1,
                title = "Short Title",
                desc = "Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description Long Description ",
                image = null,
                priority = TaskPriority.MEDIUM,
                timestamp = System.currentTimeMillis()
            )
        ) {

        }
    }
}

@Composable
private fun SortButton(modifier: Modifier = Modifier, @StringRes title: Int, icon: ImageVector) {

}

