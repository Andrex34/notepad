package com.notepad.data.remote

import com.notepad.domain.model.User
import com.notepad.domain.repository.AuthRepository

class DesktopAuthRepository : AuthRepository {
    private var currentUser: User? = null

    override suspend fun login(email: String, password: String): Result<User> {
        val user = User(uid = "desktop-user", email = email)
        currentUser = user
        return Result.success(user)
    }

    override suspend fun register(email: String, password: String): Result<User> {
        val user = User(uid = "desktop-user", email = email)
        currentUser = user
        return Result.success(user)
    }

    override suspend fun logout() {
        currentUser = null
    }

    override fun getCurrentUser(): User? = currentUser
}
