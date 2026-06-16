package com.notepad.data.remote

import com.notepad.data.local.CategoryDao
import com.notepad.domain.model.Category
import com.notepad.domain.repository.CategoryRepository
import com.notepad.db.Categories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DesktopCategoryRepository(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getCategories(userId: String): Flow<List<Category>> {
        return categoryDao.getCategories(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category.toDb())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toDb())
    }

    override suspend fun deleteCategory(id: String) {
        categoryDao.deleteCategory(id)
    }

    override suspend fun syncCategories(userId: String) {}

    private fun Categories.toDomain() = Category(
        id = id,
        name = name,
        color = color,
        userId = user_id
    )

    private fun Category.toDb() = Categories(
        id = id,
        name = name,
        color = color,
        user_id = userId
    )
}
