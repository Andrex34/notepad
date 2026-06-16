package com.notepad.di

import com.notepad.data.local.CategoryDao
import com.notepad.data.local.NoteDao
import com.notepad.data.remote.FirebaseAuthRepository
import com.notepad.data.remote.FirebaseNoteRepository
import com.notepad.data.remote.FirebaseCategoryRepository
import com.notepad.data.sync.SyncManager
import com.notepad.domain.repository.AuthRepository
import com.notepad.domain.repository.NoteRepository
import com.notepad.domain.repository.CategoryRepository
import com.notepad.db.NotepadDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            NotepadDatabase.Schema,
            get(),
            "notepad.db"
        )
    }

    single { NotepadDatabase(get()) }
    single { NoteDao(get()) }
    single { CategoryDao(get()) }

    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }

    single<AuthRepository> { FirebaseAuthRepository(get()) }
    single<NoteRepository> { FirebaseNoteRepository(get()) }
    single<CategoryRepository> { FirebaseCategoryRepository(get()) }
    single { SyncManager(get(), get(), get(), get()) }
}
