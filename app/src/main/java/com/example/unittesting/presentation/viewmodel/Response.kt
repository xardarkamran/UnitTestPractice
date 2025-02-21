package com.example.unittesting.presentation.viewmodel

import com.example.unittesting.domain.Note

sealed class Response {
    object Loading : Response()
    data class Success(val list: List<Note>) : Response()
    data class Error(val message: String) : Response()
}