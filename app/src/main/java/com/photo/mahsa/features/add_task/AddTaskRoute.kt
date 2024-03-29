package com.photo.mahsa.features.add_task

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.photo.mahsa.model.Task
import com.photo.mahsa.model.TaskPriority
import com.photo.mahsa.ui.theme.MahsaTheme
import java.io.File

@Composable
fun AddTaskRoute(
    viewModel: AddTaskViewModel,
    onBackAction: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (uiState.isAdded) {
        LaunchedEffect(uiState) {
            onBackAction()
        }
    }

    var galleryPath: String? by remember {
        mutableStateOf(null)
    }

    var taskPriority by remember {
        mutableStateOf(TaskPriority.HIGH)
    }

    val pickedGallery = remember {
        derivedStateOf {
            galleryPath != null
        }
    }

    var pickedCamera by remember {
        mutableStateOf(false)
    }

    var cameraUri: Uri? = remember {
        null
    }

    val imagePicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                galleryPath = it.path
                pickedCamera = false
            }
            Log.i("****LOOOOG", "Uri selected is ${it?.path}")
        }

    val cameraPicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
            if (it && cameraUri != null) {
                pickedCamera = true
                galleryPath = null
                Log.i("****LOOOG", "camera is selected ${cameraUri?.path}")
            } else {
                Log.i("***LOOOOG", "camera is not selected and ${cameraUri?.path}")
            }
        }

    AddTaskScreen(uiState = uiState, onImageClick = {
        imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }, onCameraClick = {
        cameraUri = null
        val directory = File(context.filesDir, "")

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "${System.currentTimeMillis()}.png")

        cameraUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        cameraPicker.launch(cameraUri)

    },
        pickedGallery = pickedGallery.value,
        pickedCamera = pickedCamera,
        priority = taskPriority,
        onPriorityClick = {
            taskPriority = when (taskPriority) {
                TaskPriority.HIGH -> TaskPriority.LOW
                TaskPriority.MEDIUM -> TaskPriority.HIGH
                TaskPriority.LOW -> TaskPriority.MEDIUM
            }
        },
        onSubmit = { title, desc ->
            viewModel.addTask(
                Task(
                    title = title,
                    desc = desc,
                    imagePath = galleryPath ?: cameraUri?.path,
                    priority = taskPriority,
                    timestamp = System.currentTimeMillis()
                )
            )
        })
}

@Composable
fun AddTaskScreen(
    uiState: UiState,
    pickedGallery: Boolean,
    pickedCamera: Boolean,
    priority: TaskPriority,
    onImageClick: () -> Unit,
    onCameraClick: () -> Unit,
    onPriorityClick: () -> Unit,
    onSubmit: (title: String, desc: String) -> Unit
) {

    val (title, setTitle) = remember {
        mutableStateOf("")
    }

    val (desc, setDesc) = remember {
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
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .height(220.dp),
            value = desc,
            onValueChange = setDesc,
            placeholder = { Text(text = "Description") },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onSubmit(title, desc) }
            )
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            ElevatedButton(
                onClick = { onSubmit(title, desc) },
                modifier = Modifier.size(180.dp),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    contentDescription = null,
                    modifier = Modifier.size(92.dp)
                )
            }
        }

        AddMediaBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 12.dp,
                    end = 12.dp
                ),
            paddingValues = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                bottom = 24.dp
            ),
            hasGalleryImage = pickedGallery,
            hasCameraImage = pickedCamera,
            hasVoice = false,
            priority = priority,
            onGalleryClick = onImageClick,
            onCameraClick = onCameraClick,
            onVoiceClick = { },
            onPriorityClick = onPriorityClick
        )
    }

}

@Composable
private fun AddMediaBar(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    hasGalleryImage: Boolean,
    hasCameraImage: Boolean,
    hasVoice: Boolean,
    priority: TaskPriority,
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onVoiceClick: () -> Unit,
    onPriorityClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(paddingValues)
            .border(
                width = 1.dp,
                shape = MaterialTheme.shapes.small,
                color = Color.LightGray
            )
            .then(modifier)
    ) {
        Icon(
            modifier = Modifier.clickable { onGalleryClick() },
            imageVector = Icons.Default.Image,
            contentDescription = null,
            tint = if (!hasGalleryImage) Color.LightGray else Color.Green
        )

        Icon(
            modifier = Modifier
                .padding(start = 32.dp)
                .clickable { onCameraClick() },
            imageVector = Icons.Default.CameraEnhance,
            contentDescription = null,
            tint = if (!hasCameraImage) Color.LightGray else Color.Green
        )

        Icon(
            modifier = Modifier
                .padding(start = 32.dp)
                .clickable { onVoiceClick() },
            imageVector = Icons.Default.RecordVoiceOver,
            contentDescription = null,
            tint = if (!hasVoice) Color.LightGray else Color.Green
        )

        Spacer(modifier = Modifier.weight(1f))

        val (priorityIcon, priorityColor) = when (priority) {
            TaskPriority.LOW -> Icons.Default.ArrowDownward to Color.Green
            TaskPriority.MEDIUM -> Icons.Default.LinearScale to Color.Yellow
            TaskPriority.HIGH -> Icons.Default.ArrowUpward to Color.Red
        }

        Icon(
            modifier = Modifier
                .padding(start = 32.dp)
                .clickable { onPriorityClick() },
            imageVector = priorityIcon,
            contentDescription = null,
            tint = priorityColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddMediaBarPreview() {
    MahsaTheme {
        AddMediaBar(
            modifier = Modifier.padding(8.dp),
            paddingValues = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 8.dp),
            hasGalleryImage = true,
            hasCameraImage = false,
            hasVoice = true,
            priority = TaskPriority.HIGH,
            onGalleryClick = { /*TODO*/ },
            onCameraClick = { /*TODO*/ },
            onVoiceClick = { /*TODO*/ }) {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddTaskScreenPreview() {
    MahsaTheme {
        AddTaskScreen(
            uiState = UiState(),
            onImageClick = {},
            onCameraClick = {},
            pickedGallery = true,
            pickedCamera = true,
            onPriorityClick = {},
            priority = TaskPriority.HIGH
        ) { _, _ ->

        }
    }
}