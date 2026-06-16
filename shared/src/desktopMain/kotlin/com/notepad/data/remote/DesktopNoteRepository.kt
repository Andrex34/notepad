package com.notepad.data.remote

import com.notepad.data.local.NoteDao
import com.notepad.domain.model.Note
import com.notepad.domain.repository.NoteRepository
import com.notepad.db.Notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DesktopNoteRepository(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getNotes(userId: String): Flow<List<Note>> {
        return noteDao.getNotes(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun searchNotes(userId: String, query: String): Flow<List<Note>> {
        return noteDao.searchNotes(userId, query).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getNoteById(id: String): Note? {
        return noteDao.getNoteById(id)?.toDomain()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toDb())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toDb())
    }

    override suspend fun deleteNote(id: String) {
        noteDao.deleteNote(id)
    }

    override suspend fun syncNotes(userId: String) {}

    private fun Notes.toDomain() = Note(
        id = id,
        title = title,
        content = content,
        categoryId = category_id,
        isPinned = is_pinned == 1L,
        createdAt = created_at,
        updatedAt = updated_at,
        userId = user_id,
        isSynced = is_synced == 1L
    )

    private fun Note.toDb() = Notes(
        id = id,
        title = title,
        content = content,
        category_id = categoryId,
        is_pinned = if (isPinned) 1L else 0L,
        created_at = createdAt,
        updated_at = updatedAt,
        user_id = userId,
        is_synced = if (isSynced) 1L else 0L
    )
}
