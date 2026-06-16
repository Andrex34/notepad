package com.notepad.util

import com.benasher44.uuid.uuid4

fun generateId(): String = uuid4().toString()
