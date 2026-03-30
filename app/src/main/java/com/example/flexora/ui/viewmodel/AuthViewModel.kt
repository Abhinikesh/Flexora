package com.example.flexora.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexora.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _authResult = MutableSharedFlow<Result<Unit>>()
    val authResult = _authResult.asSharedFlow()

    fun onEmailChange(newValue: String) {
        _email.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.login(_email.value, _password.value).collect { result ->
                _isLoading.value = false
                _authResult.emit(result)
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.register(_email.value, _password.value).collect { result ->
                _isLoading.value = false
                _authResult.emit(result)
            }
        }
    }
}
