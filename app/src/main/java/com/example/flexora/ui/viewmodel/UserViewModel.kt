package com.example.flexora.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexora.domain.model.User
import com.example.flexora.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val user: StateFlow<User?> = repository.getUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateName(newName: String) {
        viewModelScope.launch {
            val currentUser = user.value ?: User(id = 1, name = newName, profileImageUri = "")
            repository.insertUser(currentUser.copy(name = newName))
        }
    }

    fun updateProfileImage(newUri: String) {
        viewModelScope.launch {
            val currentUser = user.value ?: User(id = 1, name = "User", profileImageUri = newUri)
            repository.insertUser(currentUser.copy(profileImageUri = newUri))
        }
    }
}
