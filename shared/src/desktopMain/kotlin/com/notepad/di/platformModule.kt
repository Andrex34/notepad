package com.notepad.di

import com.notepad.data.local.CategoryDao
import com.notepad.data.local.NoteDao
import com.notepad.data.remote.DesktopAuthRepository
import com.notepad.data.remote.DesktopNoteRepository
import com.notepad.data.remote.DesktopCategoryRepository
import com.notepad.data.sync.SyncManager
import com.notepad.domain.repository.AuthRepository
import com.notepad.domain.repository.NoteRepository
import com.notepad.domain.repository.CategoryRepository
import com.notepad.db.NotepadDatabase
import app.cash.sqldelight.Query
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.JdbcDriver
import org.koin.dsl.module
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

actual fun platformModule() = module {
    single<SqlDriver> {
        val dbFile = File(System.getProperty("user.home"), ".notepad/notepad.db")
        dbFile.parentFile?.mkdirs()

        object : JdbcDriver() {
            private var conn: Connection? = null

            override fun getConnection(): Connection {
                if (conn == null || conn!!.isClosed) {
                    Class.forName("org.sqlite.JDBC")
                    conn = DriverManager.getConnection("jdbc:sqlite:${dbFile.absolutePath}")
                }
                return conn!!
            }

            override fun closeConnection(connection: Connection) {}

            override fun close() {
                conn?.close()
                conn = null
            }

            override fun addListener(vararg queryKeys: String, listener: Query.Listener) {}
            override fun removeListener(vararg queryKeys: String, listener: Query.Listener) {}
            override fun notifyListeners(vararg queryKeys: String) {}
        }
    }

    single {
        val driver = get<SqlDriver>()
        try {
            NotepadDatabase.Schema.create(driver)
        } catch (_: Exception) {}
        NotepadDatabase(driver)
    }
    single { NoteDao(get()) }
    single { CategoryDao(get()) }
    single<AuthRepository> { DesktopAuthRepository() }
    single<NoteRepository> { DesktopNoteRepository(get()) }
    single<CategoryRepository> { DesktopCategoryRepository(get()) }
    single { SyncManager(get(), get(), get(), get()) }
}
