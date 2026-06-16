package com.notepad.di

import org.koin.dsl.module

val appModule = module {
    includes(platformModule())
}
