package com.photo.mahsa.features.tasks

import androidx.lifecycle.ViewModel
import com.photo.mahsa.ui.model.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(

): ViewModel() {

}

data class UiState(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val tasks: ImmutableList<TaskState> = persistentListOf()
)