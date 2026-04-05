package com.example.flexora.data.repository

import com.example.flexora.data.local.dao.UserDao
import com.example.flexora.data.mapper.toEntity
import com.example.flexora.data.mapper.toUser
import com.example.flexora.domain.model.User
import com.example.flexora.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {

    override fun getUser(): Flow<User?> {
        return dao.getUser().map { entity ->
            entity?.toUser()
        }
    }

    override suspend fun insertUser(user: User) {
        dao.insertUser(user.toEntity())
    }

    override suspend fun updateUser(user: User) {
        dao.updateUser(user.toEntity())
    }
}
