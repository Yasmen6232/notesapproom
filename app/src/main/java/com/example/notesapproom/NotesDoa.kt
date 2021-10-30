package com.example.notesapproom

import androidx.room.*

@Dao
interface NotesDoa {

    @Query("SELECT * FROM Notes")
    fun gettingData(): List<Data>

    @Insert
    fun addNewNote(note: Data)

    @Delete
    fun deleteNote(note: Data)

    @Update
    fun updateNote(note: Data)
}