package com.notepad

import androidx.compose.ui.window.ComposeUIViewController
import com.notepad.di.appModule
import com.notepad.domain.repository.AuthRepository
import com.notepad.domain.repository.NoteRepository
import com.notepad.domain.repository.CategoryRepository
import com.notepad.ui.navigation.NavigationGraph
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.get

fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(appModule)
    }

    val authRepository: AuthRepository = get()
    val noteRepository: NoteRepository = get()
    val categoryRepository: CategoryRepository = get()

    NavigationGraph(
        authRepository = authRepository,
        noteRepository = noteRepository,
        categoryRepository = categoryRepository
    )
}
