package com.notepad.data.remote

import com.notepad.domain.model.Note
import com.notepad.domain.repository.NoteRepository
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirebaseNoteRepository(
    private val firestore: FirebaseFirestore
) : NoteRepository {

    private fun notesCollection(userId: String) =
        firestore.collection("users").document(userId).collection("notes")

    override fun getNotes(userId: String): Flow<List<Note>> = flow {
        val snapshot = notesCollection(userId).get()
        val notes = snapshot.documents.map { doc ->
            Note(
                id = doc.id,
                title = doc.get("title") ?: "",
                content = doc.get("content") ?: "",
                categoryId = doc.get("categoryId"),
                isPinned = doc.get("isPinned") ?: false,
                createdAt = doc.get("createdAt") ?: 0L,
                updatedAt = doc.get("updatedAt") ?: 0L,
                userId = userId,
                isSynced = true
            )
        }
        emit(notes)
    }

    override fun searchNotes(userId: String, query: String): Flow<List<Note>> = flow {
        val snapshot = notesCollection(userId).get()
        val notes = snapshot.documents.map { doc ->
            Note(
                id = doc.id,
                title = doc.get("title") ?: "",
                content = doc.get("content") ?: "",
                categoryId = doc.get("categoryId"),
                isPinned = doc.get("isPinned") ?: false,
                createdAt = doc.get("createdAt") ?: 0L,
                updatedAt = doc.get("updatedAt") ?: 0L,
                userId = userId,
                isSynced = true
            )
        }.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.content.contains(query, ignoreCase = true)
        }
        emit(notes)
    }

    override suspend fun getNoteById(id: String): Note? = null

    override suspend fun insertNote(note: Note) {
        notesCollection(note.userId).document(note.id).set(
            mapOf(
                "title" to note.title,
                "content" to note.content,
                "categoryId" to note.categoryId,
                "isPinned" to note.isPinned,
                "createdAt" to note.createdAt,
                "updatedAt" to note.updatedAt
            )
        )
    }

    override suspend fun updateNote(note: Note) = insertNote(note)

    override suspend fun deleteNote(id: String) {
        // Delete requires userId, handled by caller
    }

    override suspend fun syncNotes(userId: String) {
        // Sync is handled by SyncManager
    }
}
