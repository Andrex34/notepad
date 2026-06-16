package com.notepad.di

import com.notepad.data.local.CategoryDao
import com.notepad.data.local.NoteDao
import com.notepad.data.sync.SyncManager
import com.notepad.db.NotepadDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SqlDriver> {
        NativeSqliteDriver(NotepadDatabase.Schema, "notepad.db")
    }

    single { NotepadDatabase(get()) }
    single { NoteDao(get()) }
    single { CategoryDao(get()) }
    single { SyncManager(get(), get(), get(), get()) }
}
