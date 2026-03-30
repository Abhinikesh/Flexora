package com.example.flexora.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserEmail: String?
    val isUserLoggedIn: Boolean
    fun login(email: String, password: String): Flow<Result<Unit>>
    fun register(email: String, password: String): Flow<Result<Unit>>
    fun logout()
}
