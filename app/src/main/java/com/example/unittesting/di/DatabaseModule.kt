package com.example.unittesting.di

import android.content.Context
import androidx.room.Room
import com.example.unittesting.data.NoteRepositoryImpl
import com.example.unittesting.data.local.NotesDao
import com.example.unittesting.data.local.NotesDataBase
import com.example.unittesting.domain.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNotesRoomDb(@ApplicationContext context: Context): NotesDataBase =
        Room.databaseBuilder(
            context = context, klass = NotesDataBase::class.java, name = "Notes_db"
        ).build()

    @Provides
    fun provideNotesDao(notesDataBase: NotesDataBase): NotesDao = notesDataBase.notesDao()

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository = NoteRepositoryImpl(notesDao)

}