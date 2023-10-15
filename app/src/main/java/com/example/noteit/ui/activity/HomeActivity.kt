package com.example.noteit.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noteit.R
import com.example.noteit.database.NoteDatabase
import com.example.noteit.databinding.ActivityHomeBinding
import com.example.noteit.models.Note
import com.example.noteit.ui.adapter.NotesAdapter
import com.example.noteit.ui.viewmodels.HomeViewModel
import com.example.noteit.utils.OnNoteClickListeners

class HomeActivity : AppCompatActivity(), OnNoteClickListeners, PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var selectedNote: Note

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val noteData = result.data?.getSerializableExtra("note") as? Note
            if (noteData != null){
                homeViewModel.updateNote(noteData)
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDatabase()
        setupUi()
        setupObservers()
        setupRecyclerView()
    }

    private fun setupDatabase() {
        noteDatabase = NoteDatabase.getDatabase(this@HomeActivity)
    }

    private fun setupRecyclerView() {
        binding.noteRecyclerView.setHasFixedSize(true)
        notesAdapter = NotesAdapter(this, this)
        binding.noteRecyclerView.adapter = notesAdapter
    }

    private fun setupObservers() {
        homeViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[HomeViewModel::class.java]
        homeViewModel.allNotesList.observe(this, Observer {noteList ->
            noteList?.let {
                notesAdapter.updateList(noteList)
            }
        })

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if (result.resultCode == Activity.RESULT_OK){
                val noteData = result.data?.getSerializableExtra("note") as? Note
                if (noteData != null){
                    homeViewModel.addNote(noteData)
                }
            }
        }

        passDataToAddNoteActivity(getContent)
    }

    private fun passDataToAddNoteActivity(getContent: ActivityResultLauncher<Intent>) {
        binding.addNote.setOnClickListener {
            val intent: Intent = Intent(this,AddNoteActivity::class.java)
            getContent.launch(intent)
        }
    }

    private fun setupUi() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return  false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank() || !newText.isNullOrEmpty()){
                    notesAdapter.filterList(newText)
                }else{
                    Toast.makeText(this@HomeActivity,"Searched Element Cannot Be Empty.",Toast.LENGTH_SHORT).show()
                }
                return true
            }

        })

    }

    override fun onNoteClicked(note: com.example.noteit.models.Note) {
        val intent: Intent = Intent(this@HomeActivity, AddNoteActivity::class.java).apply {
            putExtra("current_note",note)
        }
        updateNote.launch(intent)
    }

    override fun onNoteLongClicked(note: com.example.noteit.models.Note, cardView: CardView) {
        selectedNote = note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        PopupMenu(this,cardView).apply {
            setOnMenuItemClickListener(this@HomeActivity)
            inflate(R.menu.menu_home)
        }.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.deleteNoteMenu){
            homeViewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }

}