package com.example.flexora.data.mapper

import com.example.flexora.data.local.entity.UserEntity
import com.example.flexora.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        profileImageUri = profileImageUri
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        profileImageUri = profileImageUri
    )
}
