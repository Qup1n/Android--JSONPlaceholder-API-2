package com.example.jsonpusers.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
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
class UserDetailViewModel @Inject constructor(
    private val repository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: Int = savedStateHandle["userId"] ?: 1

    private val _uiState = MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    val uiState: StateFlow<UserDetailState> = _uiState.asStateFlow()

    private var _allUsers = emptyList<User>()
    private var currentIndex = 0

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            _uiState.value = UserDetailState.Loading

            // Сначала загружаем список всех пользователей для навигации
            if (_allUsers.isEmpty()) {
                val allUsersResult = repository.getUsers()
                if (allUsersResult.isSuccess) {
                    _allUsers = allUsersResult.getOrNull() ?: emptyList()
                    currentIndex = _allUsers.indexOfFirst { it.id == userId }.coerceAtLeast(0)
                }
            }

            val result = repository.getUser(userId)

            _uiState.value = when {
                result.isSuccess -> {
                    val user = result.getOrNull()
                    if (user != null) {
                        UserDetailState.Success(
                            user = user,
                            hasPrevious = currentIndex > 0,
                            hasNext = currentIndex < _allUsers.size - 1
                        )
                    } else {
                        UserDetailState.Error("User not found")
                    }
                }
                else -> {
                    UserDetailState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

    fun getPreviousUser(): Int? {
        return if (currentIndex > 0) {
            _allUsers[currentIndex - 1].id
        } else null
    }

    fun getNextUser(): Int? {
        return if (currentIndex < _allUsers.size - 1) {
            _allUsers[currentIndex + 1].id
        } else null
    }

    fun retry() {
        loadUser()
    }
}

sealed class UserDetailState {
    object Loading : UserDetailState()
    data class Success(
        val user: User,
        val hasPrevious: Boolean,
        val hasNext: Boolean
    ) : UserDetailState()
    data class Error(val message: String) : UserDetailState()
}