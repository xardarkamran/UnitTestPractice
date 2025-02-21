package com.example.unittesting.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insertNoteRepo(note: Note)
    suspend fun updateNoteRepo(note: Note)
    suspend fun deleteNoteRepo(note: Note)
    fun getAllNotesRepo():Flow<List<Note>>
}