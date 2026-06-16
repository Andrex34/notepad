package com.notepad.ui.screens.noteeditor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.notepad.domain.model.Note
import com.notepad.domain.repository.NoteRepository
import com.notepad.util.generateId
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    noteRepository: NoteRepository,
    noteId: String?,
    userId: String,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isPinned by remember { mutableStateOf(false) }
    var isLoaded by remember { mutableStateOf(noteId == null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(noteId) {
        if (noteId != null) {
            val note = noteRepository.getNoteById(noteId)
            if (note != null) {
                title = note.title
                content = note.content
                isPinned = note.isPinned
            }
            isLoaded = true
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                    Text(
                        text = if (noteId == null) "Новая заметка" else "Редактировать",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (noteId != null) {
                        IconButton(onClick = {
                            scope.launch {
                                noteRepository.deleteNote(noteId)
                                onBack()
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Удалить")
                        }
                    }
                    IconButton(onClick = {
                        scope.launch {
                            val now = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
                            val note = Note(
                                id = noteId ?: generateId(),
                                title = title,
                                content = content,
                                categoryId = null,
                                isPinned = isPinned,
                                createdAt = if (noteId != null) {
                                    noteRepository.getNoteById(noteId)?.createdAt ?: now
                                } else now,
                                updatedAt = now,
                                userId = userId,
                                isSynced = false
                            )
                            if (noteId != null) {
                                noteRepository.updateNote(note)
                            } else {
                                noteRepository.insertNote(note)
                            }
                            onBack()
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Сохранить")
                    }
                }
            }
        }
    ) { padding ->
        if (!isLoaded) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Заголовок") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    FilterChip(
                        selected = isPinned,
                        onClick = { isPinned = !isPinned },
                        label = { Text("Закрепить") }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Текст заметки") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    minLines = 10
                )
            }
        }
    }
}
