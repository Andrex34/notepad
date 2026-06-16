package com.notepad.data.remote

import com.notepad.domain.model.Category
import com.notepad.domain.repository.CategoryRepository
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirebaseCategoryRepository(
    private val firestore: FirebaseFirestore
) : CategoryRepository {

    private fun categoriesCollection(userId: String) =
        firestore.collection("users").document(userId).collection("categories")

    override fun getCategories(userId: String): Flow<List<Category>> = flow {
        val snapshot = categoriesCollection(userId).get()
        val categories = snapshot.documents.map { doc ->
            Category(
                id = doc.id,
                name = doc.get("name") ?: "",
                color = doc.get("color") ?: 0L,
                userId = userId
            )
        }
        emit(categories)
    }

    override suspend fun insertCategory(category: Category) {
        categoriesCollection(category.userId).document(category.id).set(
            mapOf(
                "name" to category.name,
                "color" to category.color
            )
        )
    }

    override suspend fun updateCategory(category: Category) = insertCategory(category)

    override suspend fun deleteCategory(id: String) {
        // Delete requires userId, handled by caller
    }

    override suspend fun syncCategories(userId: String) {
        // Sync is handled by SyncManager
    }
}
