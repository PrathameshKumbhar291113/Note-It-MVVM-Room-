package com.example.noteit.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteit.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note:Note)

    @Query("UPDATE notes_table Set title = :title, noteDetail = :noteDetail WHERE id= :id")
    suspend fun updateNote(id: Int?, title: String?, noteDetail: String?)

    @Query("Select * From notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}