package com.example.flexora.domain.repository

import com.example.flexora.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User?>
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
}
