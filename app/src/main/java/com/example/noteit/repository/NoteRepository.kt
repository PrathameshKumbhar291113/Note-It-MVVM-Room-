package com.example.noteit.repository

import androidx.lifecycle.LiveData
import com.example.noteit.database.NoteDao
import com.example.noteit.models.Note

class NoteRepository(private val noteDao: NoteDao){

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()
    suspend fun addNote(note: Note) = noteDao.addNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    suspend fun updateNote(note: Note) = noteDao.updateNote(id = note.id , title = note.title, noteDetail = note.noteDetail)

}