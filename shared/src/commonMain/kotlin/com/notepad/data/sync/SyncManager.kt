package com.notepad.data.sync

import com.notepad.data.local.NoteDao
import com.notepad.data.local.CategoryDao
import com.notepad.data.remote.FirebaseNoteRepository
import com.notepad.data.remote.FirebaseCategoryRepository
import com.notepad.domain.model.Note
import com.notepad.domain.model.Category
import kotlinx.coroutines.flow.first

class SyncManager(
    private val noteDao: NoteDao,
    private val categoryDao: CategoryDao,
    private val firebaseNoteRepository: FirebaseNoteRepository,
    private val firebaseCategoryRepository: FirebaseCategoryRepository
) {
    suspend fun syncNotes(userId: String) {
        val unsyncedNotes = noteDao.getUnsyncedNotes(userId).first()
        unsyncedNotes.forEach { localNote ->
            val note = Note(
                id = localNote.id,
                title = localNote.title,
                content = localNote.content,
                categoryId = localNote.category_id,
                isPinned = localNote.is_pinned == 1L,
                createdAt = localNote.created_at,
                updatedAt = localNote.updated_at,
                userId = localNote.user_id,
                isSynced = true
            )
            firebaseNoteRepository.insertNote(note)
            noteDao.markNoteSynced(localNote.id)
        }
    }

    suspend fun syncCategories(userId: String) {
        val categories = categoryDao.getCategories(userId).first()
        categories.forEach { localCategory ->
            val category = Category(
                id = localCategory.id,
                name = localCategory.name,
                color = localCategory.color,
                userId = localCategory.user_id
            )
            firebaseCategoryRepository.insertCategory(category)
        }
    }
}
