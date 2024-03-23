package com.photo.mahsa.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photo.mahsa.model.Task
import com.photo.mahsa.result.Result
import com.photo.mahsa.result.asResult
import com.photo.mahsa.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    private val tasks: StateFlow<Result<List<Task>>> =
        getTasksUseCase().asResult()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)

    private val sortedByPriority = MutableStateFlow(false)
    private val sortedByDateTime = MutableStateFlow(false)

    val tasksUiState: StateFlow<GetTasksUiState> =
        combine(tasks, sortedByPriority, sortedByDateTime) { tasksResult, sortedByPriority, sortedByDateTime ->
            return@combine when (tasksResult) {
                is Result.Loading -> GetTasksUiState.Loading
                is Result.Error -> GetTasksUiState.LoadFailed
                is Result.Success -> {
                    val tasks = if (sortedByPriority) {
                        tasksResult.data.sortedBy { it.priority }
                    } else if (sortedByDateTime) {
                        tasksResult.data.sortedBy { it.timestamp }.reversed()
                    } else {
                        tasksResult.data
                    }
                    GetTasksUiState.Success(tasks)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GetTasksUiState.Loading)

    fun sortTasksByPriority() {
        sortedByDateTime.value = false
        sortedByPriority.value = true
    }

    fun sortTasksByDateTime() {
        sortedByPriority.value = false
        sortedByDateTime.value = true
    }
}

sealed interface GetTasksUiState {
    data object Loading : GetTasksUiState

    data object LoadFailed : GetTasksUiState

    data class Success(
        val tasks: List<Task> = emptyList()
    ) : GetTasksUiState
}
