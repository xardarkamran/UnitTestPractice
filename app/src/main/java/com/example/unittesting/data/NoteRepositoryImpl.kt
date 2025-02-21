package com.example.unittesting.data

import com.example.unittesting.domain.Note
import com.example.unittesting.data.local.NotesDao
import com.example.unittesting.domain.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val notesDao: NotesDao): NotesRepository {
    override suspend fun insertNoteRepo(note: Note) {
        notesDao.insertNote(note)
    }

    override suspend fun updateNoteRepo(note: Note) {
        notesDao.updateNote(note)
    }

    override suspend fun deleteNoteRepo(note: Note) {
        notesDao.deleteNote(note)
    }

    override fun getAllNotesRepo(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }
}