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

    fun getItem(position: Int): String {
        return noteItems[position]
    }

    fun submitList(noteItems: MutableList<String>) {
        this.noteItems = noteItems
    }

    inner class NoteViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {

        private val itemText = itemView.itemText

        init {
            itemView.setOnClickListener { noteClickInterface.onNoteClick(adapterPosition) }

            itemView.setOnLongClickListener { noteClickInterface.onNoteLongClick(adapterPosition) }
        }

        fun bind(noteItem: String) {
            itemText.text = noteItem
        }
    }
}