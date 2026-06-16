package com.notepad

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.notepad.di.appModule
import com.notepad.ui.navigation.NavigationGraph
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

@Composable
fun App() {
    val koin = KoinPlatformTools.defaultContext().get()
    val authRepository = koin.get<com.notepad.domain.repository.AuthRepository>()
    val noteRepository = koin.get<com.notepad.domain.repository.NoteRepository>()
    val categoryRepository = koin.get<com.notepad.domain.repository.CategoryRepository>()

    NavigationGraph(
        authRepository = authRepository,
        noteRepository = noteRepository,
        categoryRepository = categoryRepository,
        modifier = Modifier.padding(top = 38.dp, bottom = 38.dp)
    )
}
