package com.example.unittesting.ui

import app.cash.turbine.test
import com.example.unittesting.CoroutineMainDispatcherRule
import com.example.unittesting.domain.Note
import com.example.unittesting.domain.NotesRepository
import com.example.unittesting.presentation.viewmodel.NotesViewModel
import com.example.unittesting.presentation.viewmodel.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class NotesViewModelTest {

    private lateinit var notesViewModel: NotesViewModel

    private val notesRepository:NotesRepository = mock()

    @get:Rule
    val coroutineMainDispatcherRule = CoroutineMainDispatcherRule()

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        notesViewModel = NotesViewModel(notesRepository)
    }

    @Test
    fun `insert note test`() = runTest {

        // Arrange: Create a test note
        val note = Note(id = 1, title = "Test Note", desc = "Test Desc",
            date = "2025-02-20", priority = 1)

        // Act: Call ViewModel's insertNote
        notesViewModel.insertNote(note)

        advanceUntilIdle() // Ensures coroutine completes before verification

        // Assert: Verify repository method was called
        verify(notesRepository).insertNoteRepo(note)

    }

    @Test
    fun `update note test`() = runTest {
        val note = Note(id = 1, title = "Test Note", desc = "Test Desc",
            date = "2025-02-20", priority = 1)
        notesViewModel.updateNote(note)

        advanceUntilIdle()

        verify(notesRepository).updateNoteRepo(note)

    }

    @Test
    fun `delete note test`() = runTest {
        val note = Note(id = 1, title = "Test Note", desc = "Test Desc",
            date = "2025-02-20", priority = 1)
        notesViewModel.deleteNote(note)

        advanceUntilIdle()
        verify(notesRepository).deleteNoteRepo(note)
    }

    @Test
    fun `get All Test Notes`() = runTest {
        val notesList = listOf(
            Note(id = 1, title = "test title", desc = "test desc", date = "2020-02-1", priority = 1)
        )

        val flow = flowOf(notesList)
        whenever(notesRepository.getAllNotesRepo()).thenReturn(flow)

        notesViewModel.getAllNotes()

        notesViewModel.notesList.test{
            assertEquals(awaitItem(), Response.Loading) // First emission should be Loading
            assertEquals(awaitItem(), Response.Success(notesList))
            cancelAndIgnoreRemainingEvents()

        }

    }

}