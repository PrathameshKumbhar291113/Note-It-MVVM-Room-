package com.example.noteit.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.noteit.databinding.ActivityAddNoteBinding
import com.example.noteit.models.Note
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: Note
    private lateinit var oldNote: Note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        binding.backArrow.setOnClickListener {
            finish()
        }

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.editTitle.setText(oldNote.title)
            binding.editNoteMultiLine.setText(oldNote.noteDetail)
            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.submitNote.setOnClickListener {
            if (!binding.editTitle.text.isNullOrBlank() || !binding.editTitle.text.isNullOrEmpty() ||
                !binding.editNoteMultiLine.text.isNullOrBlank() || !binding.editNoteMultiLine.text.isNullOrEmpty()
            ) {
                if (isUpdate) {
                    note = Note(
                        oldNote.id,
                        binding.editTitle.text.toString(),
                        binding.editNoteMultiLine.text.toString(),
                        SimpleDateFormat("EEE, d MM yyyy HH:mm a").format(
                            Date()
                        )
                    )
                }else{
                    note = Note(
                        null,
                        binding.editTitle.text.toString(),
                        binding.editNoteMultiLine.text.toString(),
                        SimpleDateFormat("EEE, d MM yyyy HH:mm a").format(
                            Date()
                        )
                    )
                }
                val intent: Intent = Intent().apply {
                    putExtra("note",note)
                }
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this@AddNoteActivity,"The Fields Cannot Be Empty" , Toast.LENGTH_SHORT).show()
            }
        }

    }
}