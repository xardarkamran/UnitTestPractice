package com.example.unittesting.di

import android.content.Context
import androidx.room.Room
import com.example.unittesting.data.local.NotesDao
import com.example.unittesting.data.local.NotesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces =[DatabaseModule::class] // Replaces the production module
)
@Module
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideInMemoryDatabase(@ApplicationContext context: Context): NotesDataBase =
        Room.inMemoryDatabaseBuilder(
            context,
            NotesDataBase::class.java
        )
            .allowMainThreadQueries() //allowMainThreadQueries() makes it work on the main thread during testing.
            .build()


    @Provides
    fun provideNotesDao(notesDataBase: NotesDataBase): NotesDao =
        notesDataBase.notesDao()


}