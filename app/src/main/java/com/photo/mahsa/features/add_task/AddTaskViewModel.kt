package com.photo.mahsa.features.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photo.mahsa.R
import com.photo.mahsa.model.Task
import com.photo.mahsa.result.Result
import com.photo.mahsa.usecase.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    fun addTask(task: Task) {
        if (!task.isValid()) {
            _uiState.update { it.copy(errorMessage = R.string.message_add_task_validation) }
            return
        }
        viewModelScope.launch {
            val result = addTaskUseCase(task)
            if (result is Result.Success) {
                _uiState.update { it.copy(isAdded = true) }
            } else {
                _uiState.update { it.copy(isAdded = false, errorMessage = R.string.error_add_task) }
            }
        }
    }
}

data class UiState(
    val errorMessage: Int? = null,
    val isAdded: Boolean = false
)