package com.tegarpenemuan.latihanroom.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tegarpenemuan.latihanroom.databinding.ListItemNotesBinding
import com.tegarpenemuan.latihanroom.model.NoteModel

class NoteAdapter(private val listener: EventListener,private var notes: List<NoteModel>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<NoteModel>) {
        this.notes = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.tvJudul.text = note.judul
        holder.binding.tvCatatan.text = note.catatan

        holder.itemView.setOnClickListener {
            listener.onClick(note)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    interface EventListener {
        fun onClick(item: NoteModel)
    }
}