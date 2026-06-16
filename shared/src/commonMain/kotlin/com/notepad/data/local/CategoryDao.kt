package com.notepad.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.notepad.db.Categories
import com.notepad.db.NotepadDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryDao(database: NotepadDatabase) {
    private val queries = database.notepadQueries

    fun getCategories(userId: String): Flow<List<Categories>> {
        return queries.selectCategories(userId)
            .asFlow()
            .map { it.executeAsList() }
    }

    suspend fun getCategoryById(id: String): Categories? {
        return withContext(Dispatchers.Default) {
            queries.selectCategoryById(id).executeAsOneOrNull()
        }
    }

    suspend fun insertCategory(category: Categories) {
        withContext(Dispatchers.Default) {
            queries.insertCategory(
                id = category.id,
                name = category.name,
                color = category.color,
                user_id = category.user_id
            )
        }
    }

    suspend fun updateCategory(category: Categories) {
        withContext(Dispatchers.Default) {
            queries.updateCategory(
                name = category.name,
                color = category.color,
                id = category.id
            )
        }
    }

    suspend fun deleteCategory(id: String) {
        withContext(Dispatchers.Default) {
            queries.deleteCategory(id)
        }
    }
}
