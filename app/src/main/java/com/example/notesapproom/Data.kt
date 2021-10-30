package com.example.notesapproom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Data(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PK") val pk: Int= 0,
    @ColumnInfo(name = "Note") val note: String
)
