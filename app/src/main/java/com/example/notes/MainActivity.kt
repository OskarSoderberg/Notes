package com.example.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_NOTE = "com.example.notes.NOTE"
const val EXTRA_ID = "com.example.notes.ID"

class MainActivity : AppCompatActivity(), NoteClickInterface {
    companion object {
        var noteItems = mutableListOf<String>()
        lateinit var noteAdapter: NoteRecyclerAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddNoteButton()

        initRecyclerView()

        loadData()
    }

    private fun addNote() {
        val intent = Intent(this, EditNoteActivity::class.java)
        startActivity(intent)
    }

    private fun initAddNoteButton() {
        val addNoteButton: FloatingActionButton = findViewById(R.id.newNoteButton)
        addNoteButton.setOnClickListener {
            addNote()
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            noteAdapter = NoteRecyclerAdapter(this@MainActivity, noteItems)
            adapter = noteAdapter
        }
    }

    private fun loadData() {
        val sharedPref = applicationContext.getSharedPreferences(R.string.save_key.toString(),Context.MODE_PRIVATE)
        val set = sharedPref.getStringSet("notes", null)

        if (noteItems.size == 0) {
            if (set == null) {
                noteItems.add("Tap to edit, hold to delete!")
            } else {
                noteItems = set.toMutableList()
                for (position in 0 until noteItems.size) {
                    // Fixes a whitespace bug
                    noteItems[position] = noteItems[position].trim()
                }
            }
        }
        noteAdapter.submitList(noteItems)
    }

    private fun saveData() {
        val sharedPref = applicationContext.getSharedPreferences(R.string.save_key.toString(),Context.MODE_PRIVATE)
        val set = HashSet<String>(noteItems)
        sharedPref.edit().putStringSet("notes", set).apply()

    }

    private fun editNote(position: Int) {
        val note = noteAdapter.getNoteAt(position)
        val intent = Intent(this, EditNoteActivity::class.java).apply {
            putExtra(EXTRA_NOTE, note)
            putExtra(EXTRA_ID, position)
        }
        startActivity(intent)
    }

    private fun deleteNote(position: Int) {
        noteItems.removeAt(position)
        noteAdapter.notifyDataSetChanged()

        saveData()
}

    override fun onNoteClick(position: Int) {
        editNote(position)
    }

    override fun onNoteLongClick(position: Int): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.delete_title)
            .setMessage(R.string.delete_message)
            .setPositiveButton(R.string.delete_yes) { _, _ -> deleteNote(position) }
            .setNegativeButton(R.string.delete_no) { _, _ -> }
        builder.show()
        return true
    }

}