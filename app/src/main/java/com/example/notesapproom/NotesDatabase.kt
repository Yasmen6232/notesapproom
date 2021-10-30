package com.example.notesapproom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Data::class],version = 1,exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun notesDoa(): NotesDoa

    companion object{
        var instance: NotesDatabase?= null
        fun getInstance(context: Context): NotesDatabase{
            if (instance!=null)
                return instance!!
            instance= Room.databaseBuilder(context,NotesDatabase::class.java,"Miro")
                .run { allowMainThreadQueries() }.build()
            return instance!!
        }
    }
}