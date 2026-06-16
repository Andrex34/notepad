package com.notepad.data.remote

import com.notepad.domain.model.User
import com.notepad.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.FirebaseAuth

class FirebaseAuthRepository(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password)
            val user = result.user
            if (user != null) {
                Result.success(User(user.uid, user.email ?: email))
            } else {
                Result.failure(Exception("Пользователь не найден"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            val user = result.user
            if (user != null) {
                Result.success(User(user.uid, user.email ?: email))
            } else {
                Result.failure(Exception("Ошибка регистрации"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let { User(it.uid, it.email ?: "") }
    }
}
