package com.photo.mahsa.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photo.mahsa.result.Result
import com.photo.mahsa.result.asResult
import com.photo.mahsa.ui.model.Task
import com.photo.mahsa.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
): ViewModel() {
    val tasksUiState: StateFlow<GetTasksUiState> = getTasksUseCase().asResult().map { result ->
        when (result) {
            is Result.Loading -> GetTasksUiState.Loading
            is Result.Error -> GetTasksUiState.LoadFailed
            is Result.Success -> GetTasksUiState.Success(result.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GetTasksUiState.Loading
    )
}

sealed interface GetTasksUiState {
    data object Loading: GetTasksUiState

    data object LoadFailed: GetTasksUiState

    data class Success(
        val tasks: List<Task> = emptyList()) : GetTasksUiState
}
