package com.notepad.domain.model

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val categoryId: String?,
    val isPinned: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val userId: String,
    val isSynced: Boolean = false
)
