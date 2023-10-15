package com.example.noteit.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.databinding.ItemNoteBinding
import com.example.noteit.models.Note
import com.example.noteit.utils.OnNoteClickListeners
import com.example.noteit.utils.randomColors

class NotesAdapter(
    private val context: Context,
    val listeners: OnNoteClickListeners
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var noteList =  ArrayList<Note>()
    private var fullNoteList =  ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) =
        holder.bindNote(noteList[position], holder)

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(searchQuery: String){
        noteList.clear()
        fullNoteList.forEach{noteItem ->
            if (noteItem.title?.lowercase()?.contains(searchQuery.lowercase()) == true ||
            noteItem.noteDetail?.lowercase()?.contains(searchQuery.lowercase()) == true){
                noteList.add(noteItem)
            }
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newNoteList: List<Note>){
        noteList.clear()
        noteList.addAll(newNoteList)

        fullNoteList.clear()
        fullNoteList.addAll(newNoteList)
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(
        val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindNote(note: Note, holder: NotesViewHolder) {
            binding.noteTitle.text = note.title
            binding.noteDetail.text = note.noteDetail
            binding.noteSavedDate.text = note.date
            binding.noteTitle.isSelected = true
            binding.noteSavedDate.isSelected = true
            binding.noteCard.setCardBackgroundColor(context.resources.getColor(randomColors(),null))

            binding.noteCard.setOnClickListener {
                listeners.onNoteClicked(noteList[holder.adapterPosition])
            }

            binding.noteCard.setOnLongClickListener {
                listeners.onNoteLongClicked(noteList[holder.adapterPosition], binding.noteCard)
                true
            }
        }
    }
}