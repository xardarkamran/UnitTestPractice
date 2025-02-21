package com.example.unittesting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unittesting.domain.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDataBase:RoomDatabase() {
    abstract fun notesDao():NotesDao
}