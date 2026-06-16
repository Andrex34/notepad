package com.notepad.domain.repository

import com.notepad.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
    suspend fun logout()
    fun getCurrentUser(): User?
}
