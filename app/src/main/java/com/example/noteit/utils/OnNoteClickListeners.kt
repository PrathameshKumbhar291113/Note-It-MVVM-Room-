package com.example.noteit.utils

import androidx.cardview.widget.CardView
import com.example.noteit.models.Note

interface OnNoteClickListeners {
   fun onNoteClicked(note: Note)
   fun onNoteLongClicked(note: Note, cardView: CardView)
}