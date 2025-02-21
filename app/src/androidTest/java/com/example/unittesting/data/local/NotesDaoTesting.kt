package com.example.unittesting.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.unittesting.ClassMainDispatcherRule
import com.example.unittesting.domain.Note
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NotesDaoTesting {

    @get:Rule
    val classMainDispatcherRule = ClassMainDispatcherRule()


    //injection of hilt
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var notesDataBase: NotesDataBase

    @Inject
    lateinit var notesDao: NotesDao


    @Before
    fun setUp() {

        hiltRule.inject() // Inject dependencies

        //manual injection before hilt
        /*
               val context = ApplicationProvider.getApplicationContext<Context>()
                notesDataBase = Room.inMemoryDatabaseBuilder(
                    context,
                    NotesDataBase::class.java
                ).allowMainThreadQueries().build()
                notesDao = notesDataBase.notesDao()*/
    }

    @After
    fun tearDown() {
        notesDataBase.close() // Close DB after tests
    }

    @Test
    fun insertNoteTest() = runTest {
        val note = Note(
            id = 1,
            title = "this is my test title",
            desc = "this is test description",
            date = "2020-05-12",
            priority = 1
        )
        notesDao.insertNote(note)
        val list = notesDao.getAllNotes().first()
        assertThat(list).contains(note)
    }

    @Test
    fun deleteNoteTest() = runTest {
        val note = Note(
            id = 1,
            title = "this is my test title",
            desc = "this is test description",
            date = "2020-05-12",
            priority = 1
        )

        notesDao.insertNote(note)
        notesDao.deleteNote(note)

        val list = notesDao.getAllNotes().first()
        assertThat(list).doesNotContain(note)
    }

    @Test
    fun updateNoteTest() = runTest {
        val note = Note(
            id = 1,
            title = "this is my test title",
            desc = "this is test description",
            date = "2020-05-12",
            priority = 1
        )
        notesDao.insertNote(note)
        val note1 = Note(
            id = 1,
            title = "this is my test1 title",
            desc = "this is test1 description",
            date = "2020-05-12",
            priority = 1
        )
        notesDao.updateNote(note1)

        val list = notesDao.getAllNotes().first()

        assertThat(list).contains(note1)

    }


    @Test
    fun getAllNotesTest() = runTest {
        val note = Note(
            id = 1,
            title = "this is my test title",
            desc = "this is test description",
            date = "2020-05-12",
            priority = 1
        )
        val note1 = Note(
            id = 2,
            title = "this is my test1 title",
            desc = "this is test1 description",
            date = "2020-05-12",
            priority = 1
        )
        notesDao.insertNote(note)
        notesDao.insertNote(note1)
        val list = notesDao.getAllNotes().first()

        assertThat(list).containsExactly(note, note1)


    }


}