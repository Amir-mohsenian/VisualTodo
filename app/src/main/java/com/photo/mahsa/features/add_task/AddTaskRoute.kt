package com.photo.mahsa.features.add_task

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.photo.mahsa.ui.theme.MahsaTheme

@Composable
fun AddTaskRoute(
    viewModel: AddTaskViewModel,
    onBackAction: () -> Unit,
    onSubmit: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


}

@Composable
fun AddTaskScreen(
    uiState: UiState,
    onImageClick: () -> Unit,
    onSubmit: () -> Unit
) {

    val (title, setTitle) = remember {
        mutableStateOf("")
    }

    val (desc, setDesc) = remember {
        mutableStateOf("")
    }

    var imagePath: String by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            value = title,
            onValueChange = setTitle,
            placeholder = { Text(text = "title") },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )

        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                .border(width = 1.dp, shape = MaterialTheme.shapes.small, color = Color.LightGray)
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)
        ) {
            Icon(

                imageVector = Icons.Default.Image,
                contentDescription = null,
                tint = Color.LightGray
            )

            Icon(
                modifier = Modifier.padding(start = 32.dp),
                imageVector = Icons.Default.CameraEnhance,
                contentDescription = null,
                tint = Color.LightGray
            )

            Icon(
                modifier = Modifier.padding(start = 32.dp),
                imageVector = Icons.Default.RecordVoiceOver,
                contentDescription = null,
                tint = Color.LightGray
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .height(220.dp),
            value = desc,
            onValueChange = setDesc,
            placeholder = { Text(text = "Description") },
            shape = MaterialTheme.shapes.medium
        )

        ElevatedCard(
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
                .size(240.dp),
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    modifier = Modifier
                        .size(92.dp)
                        .clickable { onSubmit() }
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun AddTaskScreenPreview() {
    MahsaTheme {
        AddTaskScreen(uiState = UiState(), onImageClick = {}) {

        }
    }
}