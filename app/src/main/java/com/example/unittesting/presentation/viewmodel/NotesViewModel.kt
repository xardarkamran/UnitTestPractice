package com.example.unittesting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittesting.domain.Note
import com.example.unittesting.domain.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NotesViewModel constructor(private val notesRepository: NotesRepository) :
    ViewModel() {

    private var _notesList = MutableStateFlow<Response>(Response.Loading)
    val notesList = _notesList.asStateFlow()

    fun insertNote(note: Note) = viewModelScope.launch {
        notesRepository.insertNoteRepo(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        notesRepository.updateNoteRepo(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        notesRepository.deleteNoteRepo(note)
    }

    fun getAllNotes() = viewModelScope.launch {
        _notesList.value = Response.Loading
        try {
            val list = notesRepository.getAllNotesRepo().first()
            _notesList.emit(Response.Success(list))
        }catch (e:Exception){
            _notesList.emit(Response.Error(e.message.toString()))
        }

    }

}