package com.notepad.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.notepad.db.NotepadDatabase
import com.notepad.db.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NoteDao(database: NotepadDatabase) {
    private val queries = database.notepadQueries

    fun getNotes(userId: String): Flow<List<Notes>> {
        return queries.selectAllNotes(userId)
            .asFlow()
            .map { it.executeAsList() }
    }

    fun searchNotes(userId: String, query: String): Flow<List<Notes>> {
        return queries.searchNotes(userId, query, query)
            .asFlow()
            .map { it.executeAsList() }
    }

    suspend fun getNoteById(id: String): Notes? {
        return withContext(Dispatchers.Default) {
            queries.selectNoteById(id).executeAsOneOrNull()
        }
    }

    suspend fun insertNote(note: Notes) {
        withContext(Dispatchers.Default) {
            queries.insertNote(
                id = note.id,
                title = note.title,
                content = note.content,
                category_id = note.category_id,
                is_pinned = note.is_pinned,
                created_at = note.created_at,
                updated_at = note.updated_at,
                user_id = note.user_id,
                is_synced = note.is_synced
            )
        }
    }

    suspend fun updateNote(note: Notes) {
        withContext(Dispatchers.Default) {
            queries.updateNote(
                title = note.title,
                content = note.content,
                category_id = note.category_id,
                is_pinned = note.is_pinned,
                updated_at = note.updated_at,
                is_synced = note.is_synced,
                id = note.id
            )
        }
    }

    suspend fun deleteNote(id: String) {
        withContext(Dispatchers.Default) {
            queries.deleteNote(id)
        }
    }

    fun getUnsyncedNotes(userId: String): Flow<List<Notes>> {
        return queries.getUnsyncedNotes(userId)
            .asFlow()
            .map { it.executeAsList() }
    }

    suspend fun markNoteSynced(id: String) {
        withContext(Dispatchers.Default) {
            queries.markNoteSynced(id)
        }
    }
}
