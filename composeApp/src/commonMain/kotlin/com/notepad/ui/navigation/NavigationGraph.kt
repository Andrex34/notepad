package com.notepad.ui.navigation

import androidx.compose.runtime.*
import com.notepad.domain.repository.AuthRepository
import com.notepad.domain.repository.NoteRepository
import com.notepad.domain.repository.CategoryRepository
import com.notepad.ui.screens.login.LoginScreen
import com.notepad.ui.screens.register.RegisterScreen
import com.notepad.ui.screens.noteslist.NotesListScreen
import com.notepad.ui.screens.noteeditor.NoteEditorScreen
import com.notepad.ui.screens.categories.CategoriesScreen

sealed class Screen {
    data object Login : Screen()
    data object Register : Screen()
    data object NotesList : Screen()
    data class NoteEditor(val noteId: String? = null) : Screen()
    data object Categories : Screen()
}

@Composable
fun NavigationGraph(
    authRepository: AuthRepository,
    noteRepository: NoteRepository,
    categoryRepository: CategoryRepository
) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }
    var currentUserId by remember { mutableStateOf<String?>(null) }

    when (val screen = currentScreen) {
        is Screen.Login -> {
            LoginScreen(
                authRepository = authRepository,
                onLoginSuccess = {
                    currentUserId = authRepository.getCurrentUser()?.uid
                    currentScreen = Screen.NotesList
                },
                onRegisterClick = { currentScreen = Screen.Register }
            )
        }

        is Screen.Register -> {
            RegisterScreen(
                authRepository = authRepository,
                onRegisterSuccess = {
                    currentUserId = authRepository.getCurrentUser()?.uid
                    currentScreen = Screen.NotesList
                },
                onBackClick = { currentScreen = Screen.Login }
            )
        }

        is Screen.NotesList -> {
            val userId = currentUserId ?: return
            NotesListScreen(
                noteRepository = noteRepository,
                userId = userId,
                onNoteClick = { noteId ->
                    currentScreen = Screen.NoteEditor(noteId)
                },
                onAddNoteClick = {
                    currentScreen = Screen.NoteEditor()
                },
                onCategoriesClick = {
                    currentScreen = Screen.Categories
                },
                onLogout = {
                    currentScreen = Screen.Login
                    currentUserId = null
                }
            )
        }

        is Screen.NoteEditor -> {
            val userId = currentUserId ?: return
            NoteEditorScreen(
                noteRepository = noteRepository,
                noteId = screen.noteId,
                userId = userId,
                onBack = { currentScreen = Screen.NotesList }
            )
        }

        is Screen.Categories -> {
            val userId = currentUserId ?: return
            CategoriesScreen(
                categoryRepository = categoryRepository,
                userId = userId,
                onBack = { currentScreen = Screen.NotesList }
            )
        }
    }
}
