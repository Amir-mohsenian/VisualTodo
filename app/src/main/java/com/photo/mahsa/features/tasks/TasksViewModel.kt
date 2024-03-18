package com.photo.mahsa.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photo.mahsa.data.Repository
import com.photo.mahsa.ui.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun loadTasks() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val tasks = repository.loadTasks()
            _uiState.update { it.copy(tasks = tasks.toImmutableList(), loading = false) }
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val tasks: ImmutableList<Task> = persistentListOf()
)