package com.example.unittesting.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    val title: String,
    var desc: String,
    var date:String,
    var priority:Int
    )