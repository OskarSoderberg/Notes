package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class NoteRecyclerAdapter(
    var noteClickInterface: NoteClickInterface,
    private var noteItems: MutableList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NoteViewHolder -> {
                val item = noteItems[position]
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return noteItems.size
    }

    inner class NoteViewHolder constructor(
        noteView: View
    ): RecyclerView.ViewHolder(noteView) {

        private val noteText = noteView.itemText

        init {
            noteView.setOnClickListener { noteClickInterface.onNoteClick(adapterPosition) }
            noteView.setOnLongClickListener { noteClickInterface.onNoteLongClick(adapterPosition) }
        }

        fun bind(noteItem: String) {
            noteText.text = noteItem
        }
    }

    fun getNoteAt(position: Int): String {
        return noteItems[position]
    }

    fun submitList(noteItems: MutableList<String>) {
        this.noteItems = noteItems
    }
}