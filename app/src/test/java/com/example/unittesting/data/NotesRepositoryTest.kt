package com.example.unittesting.data

import com.example.unittesting.domain.Note
import com.example.unittesting.data.local.NotesDao
import com.example.unittesting.domain.NotesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class NotesRepositoryTest {

    private lateinit var notesRepository: NotesRepository

    @Mock
    lateinit var notesDao: NotesDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        notesRepository = NoteRepositoryImpl(notesDao)
    }

    @Test
    fun `insert note`() = runTest {
        val note: Note = mock()
        notesRepository.insertNoteRepo(note)
        verify(notesDao).insertNote(note)
    }

    @Test
    fun `update note`() = runTest {
        val note = Note(
            id = 1,
            title = "this is my test title",
            desc = "this is test description",
            date = "2020-05-12",
            priority = 1
        )
        notesRepository.updateNoteRepo(note)
        verify(notesDao).updateNote(note)
    }

    @Test
    fun `delete note`() = runTest {
        val note = Note(
            id = 1,
            title = "this is my test title",
            desc = "this is test description",
            date = "2020-05-12",
            priority = 1
        )
        notesRepository.deleteNoteRepo(note)
        verify(notesDao).deleteNote(note)
    }

    @Test
    fun `get All Notes Test`() = runTest {
        val notesList = listOf(
            Note(id = 1, title = "this is my test title", desc = "this is test description", date = "2020-05-12", priority = 1),
            Note(id = 2, title = "this is my test2 title", desc = "this is test2 description", date = "2020-05-12", priority = 2)
        )

        val flow = flowOf(notesList)

        `when`(notesDao.getAllNotes()).thenReturn(flow)

        val result = notesRepository.getAllNotesRepo().first()
        assertThat(result).isEqualTo(notesList)

    }

}