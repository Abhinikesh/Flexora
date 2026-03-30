package com.example.flexora.data.repository

import com.example.flexora.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override val currentUserEmail: String?
        get() = "demo@flexora.com"

    override val isUserLoggedIn: Boolean
        get() = false // Start at login screen for demo

    override fun login(email: String, password: String): Flow<Result<Unit>> = flow {
        // Dummy login: any email/password works if not empty
        if (email.isNotBlank() && password.isNotBlank()) {
            delay(1000) // Simulate network delay
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(Exception("Please enter email and password")))
        }
    }

    override fun register(email: String, password: String): Flow<Result<Unit>> = flow {
        // Dummy register
        if (email.isNotBlank() && password.isNotBlank()) {
            delay(1000)
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(Exception("Please enter email and password")))
        }
    }

    override fun logout() {
        // Dummy logout
    }
}
