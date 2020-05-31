package com.example.notes

import android.view.View

interface NoteClickInterface {
    fun onNoteClick(position: Int)
    fun onNoteLongClick(position: Int): Boolean
}