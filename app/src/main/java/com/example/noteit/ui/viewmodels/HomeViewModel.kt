package com.example.noteit.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteit.database.NoteDatabase
import com.example.noteit.models.Note
import com.example.noteit.repository.NoteRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private var noteRepository: NoteRepository
    var allNotesList : LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        noteRepository = NoteRepository(dao)
        allNotesList = noteRepository.allNotes
    }

    fun addNote(note: Note) {
        viewModelScope.launch{
            noteRepository.addNote(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch{
            noteRepository.deleteNote(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch{
            noteRepository.updateNote(note)
        }
    }



}