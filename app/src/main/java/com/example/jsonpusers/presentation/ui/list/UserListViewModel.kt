package com.example.jsonpusers.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonpusers.domain.model.User
import com.example.jsonpusers.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserListState>(UserListState.Loading)
    val uiState: StateFlow<UserListState> = _uiState.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UserListState.Loading

            val result = repository.getUsers()

            _uiState.value = when {
                result.isSuccess -> {
                    _users.value = result.getOrNull() ?: emptyList()
                    UserListState.Success
                }
                else -> {
                    UserListState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

    fun retry() {
        loadUsers()
    }
}

sealed class UserListState {
    object Loading : UserListState()
    object Success : UserListState()
    data class Error(val message: String) : UserListState()
}