package com.example.notes

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        createNoteEditor()
    }

    private fun createNoteEditor() {
        val noteEditor = findViewById<EditText>(R.id.noteEditor)

        var position = intent.getIntExtra(EXTRA_ID, -1)
        val note = intent.getStringExtra(EXTRA_NOTE)

        if (position == -1) { // If a new note is being created
            position = MainActivity.noteItems.size
            MainActivity.noteItems.add("")
        } else {
            noteEditor.setText(note)
        }

        if (note != null) {
            noteEditor.setSelection(note.length)
        }

        noteEditor.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                MainActivity.noteItems[position] = p0.toString()
                MainActivity.noteAdapter.notifyDataSetChanged()
                saveData()
            }

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun saveData() {
        val sharedPref = applicationContext.getSharedPreferences(R.string.save_key.toString(),Context.MODE_PRIVATE)
        val set = HashSet<String>(MainActivity.noteItems)
        sharedPref.edit().putStringSet("notes", set).apply()
    }
}