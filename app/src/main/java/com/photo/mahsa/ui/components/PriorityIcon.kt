package com.photo.mahsa.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.photo.mahsa.model.TaskPriority
import com.photo.mahsa.ui.theme.MahsaTheme
import com.photo.mahsa.ui.theme.PriorityHigh
import com.photo.mahsa.ui.theme.PriorityLow
import com.photo.mahsa.ui.theme.PriorityMedium

@Composable
fun PriorityIcon(modifier: Modifier = Modifier, priority: TaskPriority) {
    val color = when (priority) {
        TaskPriority.HIGH -> PriorityHigh
        TaskPriority.MEDIUM -> PriorityMedium
        TaskPriority.LOW -> PriorityLow
    }
    Icon(
        modifier = modifier,
        imageVector = Icons.Default.KeyboardArrowUp,
        contentDescription = null,
        tint = color
    )
}

@Preview(showBackground = true)
@Composable
private fun PriorityIconPreview() {
    MahsaTheme {
        Column {
            PriorityIcon(priority = TaskPriority.LOW, modifier = Modifier.size(48.dp))
        }
    }
}